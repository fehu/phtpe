package feh.phtpe

/** A physically typed value */
case class PhysTyped[N: Numeric, Tpe <: PhysType](value: N)

object PhysTyped{
  implicit class Creation[N: Numeric](v: N){
    def @@[Tpe <: PhysType] = PhysTyped[N, Tpe](v)
    def of[Tpe <: PhysType] = PhysTyped[N, Tpe](v)
  }

  implicit class PhysTypedOps[N: Numeric, Tpe <: PhysType](tped: PhysTyped[N, Tpe]){

    def phEq[Tpe2 <: PhysType](tped2: PhysTyped[N, Tpe2])(implicit ev: WeakPhysTypeEqualEvidence[Tpe, Tpe2]) =
      ev.equal && tped.value == tped2.value
    def ===[Tpe2 <: PhysType](tped2: PhysTyped[N, Tpe2])(implicit ev: WeakPhysTypeEqualEvidence[Tpe, Tpe2]) = phEq[Tpe2](tped2)

    /** Hard evidence, aborts at compile if types are incompatible */
    def =@=[Tpe2 <: PhysType](tped2: PhysTyped[N, Tpe2])(implicit ev: PhysTypeEqualEvidence[Tpe, Tpe2]) = tped.value == tped2.value
  }
}