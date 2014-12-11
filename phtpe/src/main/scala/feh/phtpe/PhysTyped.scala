package feh.phtpe

/** A physically typed value */
case class PhysTyped[N: Numeric, Tpe <: PhysType](value: N)

object PhysTyped{
  implicit class Creation[N: Numeric](v: N){
    def @@[Tpe <: PhysType] = PhysTyped[N, Tpe](v)
    def of[Tpe <: PhysType] = PhysTyped[N, Tpe](v)
  }

  implicit class PhysTypedOps[N, Tpe <: PhysType](tped: PhysTyped[N, Tpe])
                                                 (implicit num: Numeric[N]/*, val simplified: PhysTypeSimplified[Tpe]*/)
  {

    /** Hard equal evidence, aborts at compile if types are incompatible */
    def =@=[Tpe2 <: PhysType](tped2: PhysTyped[N, Tpe2])(implicit ev: PhysTypeEqualEvidence[Tpe, Tpe2]) = tped.value == tped2.value

    /** Hard sum, aborts at compile if types are incompatible */
    def +[Tpe2 <: PhysType](tped2: PhysTyped[N, Tpe2])(implicit ev: PhysTypeEqualEvidence[Tpe, Tpe2]): PhysTyped[N, Tpe] =
      num.plus(tped.value, tped2.value).@@[Tpe]

    /** Hard subtraction, aborts at compile if types are incompatible */
    def -[Tpe2 <: PhysType](tped2: PhysTyped[N, Tpe2])(implicit ev: PhysTypeEqualEvidence[Tpe, Tpe2]): PhysTyped[N, Tpe] =
      num.minus(tped.value, tped2.value).@@[Tpe]

    /** Soft equals */
    def phEq[Tpe2 <: PhysType](tped2: PhysTyped[N, Tpe2])(implicit ev: WeakPhysTypeEqualEvidence[Tpe, Tpe2]): Boolean =
      ev.equal && tped.value == tped2.value

    /** Soft equals */
    def ===[Tpe2 <: PhysType](tped2: PhysTyped[N, Tpe2])(implicit ev: WeakPhysTypeEqualEvidence[Tpe, Tpe2]) = phEq[Tpe2](tped2)

    /** Soft sum */
    def soft_+[Tpe2 <: PhysType](tped2: PhysTyped[N, Tpe2])(implicit ev: WeakPhysTypeEqualEvidence[Tpe, Tpe2]): Option[PhysTyped[N, Tpe]] =
      ev.asOption(num.plus(tped.value, tped2.value).@@[Tpe])

    /** Soft subtraction */
    def soft_-[Tpe2 <: PhysType](tped2: PhysTyped[N, Tpe2])(implicit ev: WeakPhysTypeEqualEvidence[Tpe, Tpe2]): Option[PhysTyped[N, Tpe]] =
      ev.asOption(num.minus(tped.value, tped2.value).@@[Tpe])
  }
}