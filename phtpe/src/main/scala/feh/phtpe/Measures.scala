package feh.phtpe

import feh.phtpe.PhysType.PositiveIntegerConstant
import feh.phtpe.PhysTyped.|

sealed trait Measure[Num, Tpe <: PhysType]{
  implicit val num: Numeric[Num]
  def get: Num

  implicit def physTyped: PhysTyped[Num, Tpe] = PhysTyped[Num, Tpe](get)
}

//case class Measure[Num: Numeric, M <: Measurable[Num, _]](value: Num)

object Measure{
//  implicit def physTypedIsMeasure[Num,
//                                  Tpe <: PhysType,
//                                  Tpe2 <: PhysType,
//                                  M <: Measure[Num, Tpe2]]
//                                 (phtped: PhysTyped[Num, Tpe])
//                                 (implicit num: Numeric[Num],
//                                           ev: PhysTypeEqualEvidence[Tpe, Tpe2]) = Measure[Num, M](phtped.value)
  implicit def scalarPhysTypedIsMeasure[Num,
                                        Tpe <: PhysType,
                                        Tpe2 <: PhysType,
                                        M <: Measure.Scalar[Num, Tpe2]]
                                       (phtped: PhysTyped[Num, Tpe])
                                       (implicit num: Numeric[Num],
                                                 ev: PhysTypeEqualEvidence[Tpe, Tpe2]) = Measure.Scalar[Num, Tpe2](phtped.value)

  implicit def vectorPhysTypedIsMeasure[Num,
                                        Dim <: PositiveIntegerConstant,
                                        V <: AbstractVector[Num, Dim],
                                        Tpe <: PhysType,
                                        Tpe2 <: PhysType,
                                        M <: Measure.Vector[Num, Dim, V, Tpe2]]
                                       (phtped: PhysTyped[V, Tpe])
                                       (implicit num: Numeric[Num], vector: Numeric[V],
                                                 ev: PhysTypeEqualEvidence[Tpe, Tpe2]) = Measure.Vector[Num, Dim, V, Tpe2](phtped.value)

  trait Scalar[Num, Tpe <: PhysType] extends Measure[Num, Tpe]

  protected def Scalar[Num: Numeric, Tpe <: PhysType](v: Num) = new Scalar[Num, Tpe] {
    final lazy val num: Numeric[Num] = implicitly
    final def get = v
  }

  trait Vector[Num, Dim <: PositiveIntegerConstant, V <: AbstractVector[Num, Dim], Tpe <: PhysType] extends Measure[V, Tpe]{
    def get: V
  }

  protected def Vector[Num: Numeric, Dim <: PositiveIntegerConstant, V <: AbstractVector[Num, Dim]: Numeric, Tpe <: PhysType](v: V) =
    new Vector[Num, Dim, V, Tpe] {
      final lazy val num: Numeric[V] = implicitly
      final def get = v
    }
}



object Measures{
  trait Mass[Num] { self: Measure.Scalar[Num, _] => }
  trait Time[Num] { self: Measure.Scalar[Num, _] => }

  trait Distance[Num, Dim <: PositiveIntegerConstant]       { self: Measure.Vector[Num, Dim, _, _] => }
  trait Speed[Num, Dim <: PositiveIntegerConstant]          { self: Measure.Vector[Num, Dim, _, _] => }
  trait Acceleration[Num, Dim <: PositiveIntegerConstant]   { self: Measure.Vector[Num, Dim, _, _] => }
  trait Force[Num, Dim <: PositiveIntegerConstant]          { self: Measure.Vector[Num, Dim, _, _] => }

  object SI{
    import short._

    case class Mass[Num](get: Num)(implicit val num: Numeric[Num]) extends Measure.Scalar[Num, Kilogram] with Measures.Mass[Num]
    case class Time[Num](get: Num)(implicit val num: Numeric[Num]) extends Measure.Scalar[Num, Second] with Measures.Time[Num]

    case class Distance[Num, Dim <: PositiveIntegerConstant, V <: AbstractVector[Num, Dim]]
      (get: V)(implicit val num: Numeric[V], val vector: Numeric[V])
      extends Measure.Vector[Num, Dim, V, Second] with Measures.Distance[Num, Dim]

    case class Speed[Num, Dim <: PositiveIntegerConstant, V <: AbstractVector[Num, Dim]]
      (get: V)(implicit val num: Numeric[V], val vector: Numeric[V])
      extends Measure.Vector[Num, Dim, V, m/s] with Measures.Speed[Num, Dim]

    case class Acceleration[Num, Dim <: PositiveIntegerConstant, V <: AbstractVector[Num, Dim]]
      (get:V)(implicit val num: Numeric[V], val vector: Numeric[V])
      extends Measure.Vector[Num, Dim, V, m/(s^_2)] with Measures.Acceleration[Num, Dim]

    case class Force[Num, Dim <: PositiveIntegerConstant, V <: AbstractVector[Num, Dim]]
      (get: V)(implicit val num: Numeric[V], val vector: Numeric[V])
      extends Measure.Vector[Num, Dim, V, Newton] with Measures.Force[Num, Dim]
  }
}
