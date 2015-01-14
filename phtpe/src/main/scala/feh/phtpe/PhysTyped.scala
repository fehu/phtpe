package feh.phtpe

import feh.phtpe.PhysType.IntegerConstant

/** A physically typed value */
abstract class PhysTyped[N: Numeric, Tpe <: PhysType: PhysTypeStringDecomposition]{
  val value: N

  override def toString: String = value + "|" + implicitly[PhysTypeStringDecomposition[Tpe]].toString
}

object PhysTyped extends PhysTypedImplicits

trait PhysTypedImplicits{
  final def apply[N: Numeric, Tpe <: PhysType: PhysTypeStringDecomposition](v: N): PhysTyped[N, Tpe] =
    new PhysTyped[N, Tpe]{ val value = v }
  final def unapply[N: Numeric, Tpe <: PhysType](tped: PhysTyped[N, Tpe]): Option[N] = Some(tped.value)

  final type |[N, Tpe <: PhysType] = PhysTyped[N, Tpe]

  implicit def anyNumericIsNeutral[N: Numeric](n: N)(implicit d: PhysTypeStringDecomposition[Neutral]): N|Neutral = n.of[Neutral]

  implicit final def creation[N: Numeric, V <% N, Tpe <: PhysType: PhysTypeStringDecomposition](v: V) = PhysTyped[N, Tpe](v)

  implicit final class PhysTypeCreation[N: Numeric](v: N){
    def @@[Tpe <: PhysType: PhysTypeStringDecomposition] = PhysTyped[N, Tpe](v)
    def of[Tpe <: PhysType: PhysTypeStringDecomposition] = PhysTyped[N, Tpe](v)
  }

  implicit def phTypedSafeCasting[N, Tpe <: PhysType, Expected <: PhysType: PhysTypeStringDecomposition]
                                 (tped: PhysTyped[N, Tpe])
                                 (implicit num: Numeric[N], ev: PhysTypeEqualEvidence[Tpe, Expected]): N|Expected =
    tped.value.of[Expected]

  implicit def phTypedNumeric[N2: Numeric, N1 <% N2: Numeric, Tpe <: PhysType: PhysTypeStringDecomposition](tped: N1|Tpe): N2|Tpe =
    (tped.value: N2).of[Tpe]

  implicit class PhysTypedOps[N, Tpe <: PhysType: PhysTypeStringDecomposition](tped: PhysTyped[N, Tpe])(implicit num: Numeric[N])
  {

    /** Hard equal evidence, aborts at compile if types are incompatible */
    def =@=[Tpe2 <: PhysType](tped2: PhysTyped[N, Tpe2])(implicit ev: PhysTypeEqualEvidence[Tpe, Tpe2]) = tped.value == tped2.value

    /** Hard sum, aborts at compile if types are incompatible */
    def +[Tpe2 <: PhysType](tped2: PhysTyped[N, Tpe2])(implicit ev: PhysTypeEqualEvidence[Tpe, Tpe2]): PhysTyped[N, Tpe] =
      num.plus(tped.value, tped2.value).@@[Tpe]

    /** Hard subtraction, aborts at compile if types are incompatible */
    def -[Tpe2 <: PhysType](tped2: PhysTyped[N, Tpe2])(implicit ev: PhysTypeEqualEvidence[Tpe, Tpe2]): PhysTyped[N, Tpe] =
      num.minus(tped.value, tped2.value).@@[Tpe]

    def *[Tpe2 <: PhysType](tped2: PhysTyped[N, Tpe2])(implicit ev: PhysTypeStringDecomposition[Tpe ** Tpe2]) =
      PhysTyped[N, Tpe ** Tpe2](num.times(tped.value, tped2.value))
    def *(const: N) = PhysTyped[N, Tpe](num.times(tped.value, const))

    def /[Tpe2 <: PhysType](tped2: PhysTyped[N, Tpe2])(implicit ev: PhysTypeStringDecomposition[Tpe / Tpe2]) =
      PhysTyped[N, Tpe / Tpe2](divide(tped.value, tped2.value))
    def /(const: N) = PhysTyped[N, Tpe](divide(tped.value, const))

    def unary_- = PhysTyped[N, Tpe](num.negate(tped.value))

    def pow[C <: IntegerConstant](const: C)(implicit ev: PhysTypeStringDecomposition[Tpe ^ C]) = PhysTyped[N, Tpe ^ C](
      (num.one /: (1 to const.int))((acc, _) => num.times(acc, tped.value))
    )
    def ^[C <: IntegerConstant](const: C)(implicit ev: PhysTypeStringDecomposition[Tpe ^ C]) = pow(const)

    /** Soft equals */
    def phEquals[N2: Numeric, Tpe2 <: PhysType](tped2: PhysTyped[N2, Tpe2])(implicit ev: WeakPhysTypeEqualEvidence[Tpe, Tpe2]): Boolean =
      ev.equal && tped.value == tped2.value

    /** Soft sum */
    def soft_+[Tpe2 <: PhysType](tped2: PhysTyped[N, Tpe2])(implicit ev: WeakPhysTypeEqualEvidence[Tpe, Tpe2]): Option[PhysTyped[N, Tpe]] =
      ev.asOption(num.plus(tped.value, tped2.value).@@[Tpe])

    /** Soft subtraction */
    def soft_-[Tpe2 <: PhysType](tped2: PhysTyped[N, Tpe2])(implicit ev: WeakPhysTypeEqualEvidence[Tpe, Tpe2]): Option[PhysTyped[N, Tpe]] =
      ev.asOption(num.minus(tped.value, tped2.value).@@[Tpe])

    def typeEqual[Tpe2 <: PhysType](implicit ev: WeakPhysTypeEqualEvidence[Tpe, Tpe2]) = ev.equal

    def ensureType[Expected <: PhysType: PhysTypeStringDecomposition](implicit ev: PhysTypeEqualEvidence[Tpe, Expected]): N|Expected =
      tped.value.of[Expected]
  }

  implicit class NumericPhysTypedOps[N](const: N)(implicit num: Numeric[N]){
    def **[Tpe <: PhysType: PhysTypeStringDecomposition](tped: PhysTyped[N, Tpe]) =
      PhysTyped[N, Tpe](num.times(const, tped.value))

    def div[Tpe <: PhysType](tped: PhysTyped[N, Tpe])(implicit ev: PhysTypeStringDecomposition[Tpe ^- _1]) =
      PhysTyped[N, Tpe^ -[_1]](divide(const, tped.value))
    def \[Tpe <: PhysType](tped: PhysTyped[N, Tpe])(implicit ev: PhysTypeStringDecomposition[Tpe ^- _1]) = div(tped)
  }

  private def divide[N](n1: N, n2: N)(implicit num: Numeric[N]) = num match {
    case integral: Integral[_]      => integral.quot(n1, n2)
    case fractional: Fractional[_]  => fractional.div(n1, n2)
  }
}