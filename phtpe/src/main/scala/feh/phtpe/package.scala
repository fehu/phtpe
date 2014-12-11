package feh

import scala.language.experimental.macros

package object phtpe extends PhysType.Composite.Alias with Units.SI.Alias{

  final type _1 = PhysType._1
  final type _2 = PhysType._2
  final type _3 = PhysType._3
  final type _4 = PhysType._4
  final type _5 = PhysType._5
  final type _6 = PhysType._6
  final type _7 = PhysType._7
  final type _8 = PhysType._8

  implicit def equalEvidence[L <: PhysType, R <: PhysType]: PhysTypeEqualEvidence[L, R] = macro PhysTypeEqualProves.evidence[L, R]
  implicit def weakEqualEvidence[L <: PhysType, R <: PhysType]: WeakPhysTypeEqualEvidence[L, R] = macro PhysTypeEqualProves.weakEvidence[L, R]
}
