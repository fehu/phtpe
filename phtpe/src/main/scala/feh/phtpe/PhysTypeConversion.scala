package feh.phtpe

trait PhysTypeConversion[-From <: PhysType, +To <: PhysType]

object PhysTypeConversion{
  protected[phtpe] def inst[From <: PhysType, To <: PhysType]: PhysTypeConversion[From, To] = new PhysTypeConversion[From, To]{}
}

trait PhysTypedConversion[N, T[_] <: PhysTyped[N, _], -From <: PhysType, +To <: PhysType] {
  def mult: N
}

object PhysTypedConversion{
  protected[phtpe] def inst[N, T[_] <: PhysTyped[N, _], From <: PhysType, To <: PhysType](c: N): PhysTypedConversion[N, T, From, To] = 
    new PhysTypedConversion[N, T, From, To]{ def mult: N = c }
}