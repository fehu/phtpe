package feh

import feh.phtpe.PhysType.PositiveIntegerConstant
import scala.language.experimental.macros
import scala.reflect._

package object phtpe extends PhysType.Composite.Alias with Units.SI.Alias with PhysTypedImplicits{

  final type _1 = PhysType._1
  final type _2 = PhysType._2
  final type _3 = PhysType._3
  final type _4 = PhysType._4
  final type _5 = PhysType._5
  final type _6 = PhysType._6
  final type _7 = PhysType._7
  final type _8 = PhysType._8

  final def positiveIntegerConstant[C <: PositiveIntegerConstant: ClassTag]: C = (classTag[C].runtimeClass match {
    case c if c == classOf[_1] =>_1
    case c if c == classOf[_2] =>_2
    case c if c == classOf[_3] =>_3
    case c if c == classOf[_4] =>_4
    case c if c == classOf[_5] =>_5
    case c if c == classOf[_6] =>_6
    case c if c == classOf[_7] =>_7
    case c if c == classOf[_8] =>_8
  }).asInstanceOf[C]

  final def _1 = PhysType._1
  final def _2 = PhysType._2
  final def _3 = PhysType._3
  final def _4 = PhysType._4
  final def _5 = PhysType._5
  final def _6 = PhysType._6
  final def _7 = PhysType._7
  implicit final def _8 = PhysType._8

  implicit def equalEvidence[L <: PhysType, R <: PhysType]: PhysTypeEqualEvidence[L, R] = macro PhysTypeMacros.Equal.evidence[L, R]
  implicit def weakEqualEvidence[L <: PhysType, R <: PhysType]: WeakPhysTypeEqualEvidence[L, R] = macro PhysTypeMacros.Equal.weakEvidence[L, R]
  implicit def decomposition[T <: PhysType]: PhysTypeStringDecomposition[T] = macro PhysTypeMacros.decomposition[T]

  final type Neutral = PhysType.Neutral
  final def Neutral = PhysType.Neutral

  object Measure{
    type Scalar[Num, Tpe <: PhysType] = PhysTyped[Num, Tpe]

    type Vector[V <: AbstractVector, Tpe <: PhysType] = PhysTyped[V, Tpe]
  }
}
