package feh.phtpe

/** A physically typed value */
case class PhysTyped[N: Numeric, Tpe <: PhysType](value: N)

object PhysTyped{
  implicit class Creation[N: Numeric](v: N){
    def @@[Tpe <: PhysType] = PhysTyped[N, Tpe](v)
    def of[Tpe <: PhysType] = PhysTyped[N, Tpe](v)
  }

  implicit class PhysTypedOps[N, Tpe <: PhysType](tped: PhysTyped[N, Tpe])(implicit num: Numeric[N])
  {

    /** Hard equal evidence, aborts at compile if types are incompatible */
    def =@=[Tpe2 <: PhysType](tped2: PhysTyped[N, Tpe2])(implicit ev: PhysTypeEqualEvidence[Tpe, Tpe2]) = tped.value == tped2.value

    /** Hard sum, aborts at compile if types are incompatible */
    def +[Tpe2 <: PhysType](tped2: PhysTyped[N, Tpe2])(implicit ev: PhysTypeEqualEvidence[Tpe, Tpe2]): PhysTyped[N, Tpe] =
      num.plus(tped.value, tped2.value).@@[Tpe]

    /** Hard subtraction, aborts at compile if types are incompatible */
    def -[Tpe2 <: PhysType](tped2: PhysTyped[N, Tpe2])(implicit ev: PhysTypeEqualEvidence[Tpe, Tpe2]): PhysTyped[N, Tpe] =
      num.minus(tped.value, tped2.value).@@[Tpe]

    def *[Tpe2 <: PhysType](tped2: PhysTyped[N, Tpe2]) = PhysTyped[N, Tpe ** Tpe2](num.times(tped.value, tped2.value))
    def *(const: N) = PhysTyped[N, Tpe](num.times(tped.value, const))

    def /[Tpe2 <: PhysType](tped2: PhysTyped[N, Tpe2]) = PhysTyped[N, Tpe / Tpe2](divide(tped.value, tped2.value))
    def /(const: N) = PhysTyped[N, Tpe](divide(tped.value, const))

    /** Soft equals */
    def phEquals[Tpe2 <: PhysType](tped2: PhysTyped[N, Tpe2])(implicit ev: WeakPhysTypeEqualEvidence[Tpe, Tpe2]): Boolean =
      ev.equal && tped.value == tped2.value

    /** Soft equals */
    def ===[Tpe2 <: PhysType](tped2: PhysTyped[N, Tpe2])(implicit ev: WeakPhysTypeEqualEvidence[Tpe, Tpe2]) = phEquals[Tpe2](tped2)

    /** Soft sum */
    def soft_+[Tpe2 <: PhysType](tped2: PhysTyped[N, Tpe2])(implicit ev: WeakPhysTypeEqualEvidence[Tpe, Tpe2]): Option[PhysTyped[N, Tpe]] =
      ev.asOption(num.plus(tped.value, tped2.value).@@[Tpe])

    /** Soft subtraction */
    def soft_-[Tpe2 <: PhysType](tped2: PhysTyped[N, Tpe2])(implicit ev: WeakPhysTypeEqualEvidence[Tpe, Tpe2]): Option[PhysTyped[N, Tpe]] =
      ev.asOption(num.minus(tped.value, tped2.value).@@[Tpe])
  }

  implicit class NumericPhysTypedOps[N](const: N)(implicit num: Numeric[N]){
    def **[Tpe <: PhysType](tped: PhysTyped[N, Tpe]) = PhysTyped[N, Tpe](num.times(const, tped.value))
    def x[Tpe <: PhysType](tped: PhysTyped[N, Tpe]) = **[Tpe](tped)

    def div[Tpe <: PhysType](tped: PhysTyped[N, Tpe]) = PhysTyped[N, Tpe^ -[_1]](divide(const, tped.value))
    def \[Tpe <: PhysType](tped: PhysTyped[N, Tpe]) = div(tped)
  }

  private def divide[N](n1: N, n2: N)(implicit num: Numeric[N]) = num match {
    case integral: Integral[_]      => integral.quot(n1, n2)
    case fractional: Fractional[_]  => fractional.div(n1, n2)
  }
}