package feh.phtpe

trait PhysTypeConversion[-From <: PhysType, +To <: PhysType]

object PhysTypeConversion{

}

trait PhysTypedConversion[N, T[_] <: PhysTyped[N, _], -From <: PhysType, +To <: PhysType] {
  def c: N
}