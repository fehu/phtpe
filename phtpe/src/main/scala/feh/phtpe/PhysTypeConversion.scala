package feh.phtpe

trait PhysTypeConversion[-From <: PhysType, +To <: PhysType]

object PhysTypeConversion{
  protected[phtpe] def inst[From <: PhysType, To <: PhysType]: PhysTypeConversion[From, To] = new PhysTypeConversion[From, To]{}
}

trait PhysTypedConversion[N, -From <: PhysType, +To <: PhysType] {
  def mult: N
}

object PhysTypedConversion{
  protected[phtpe] def inst[N, From <: PhysType, To <: PhysType](c: N): PhysTypedConversion[N, From, To] =
    new PhysTypedConversion[N, From, To]{ def mult: N = c }
}