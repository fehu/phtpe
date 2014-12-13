package feh.phtpe

import scala.reflect.ClassTag

object Measures{
  type Mass[Num] = Measure.Scalar[Num, _]
  type Time[Num] = Measure.Scalar[Num, _]

  type Distance[Num, V <: AbstractVector]        = Measure.Vector[V, _]
  type Speed[Num, V <: AbstractVector]           = Measure.Vector[V, _]
  type Acceleration[Num, V <: AbstractVector]    = Measure.Vector[V, _]
  type Force[Num, V <: AbstractVector]           = Measure.Vector[V, _]

  object SI{
    import short._

    type Mass[Num] = Measure.Scalar[Num, kg] with Measures.Mass[Num]
    type Time[Num] = Measure.Scalar[Num, s]  with Measures.Time[Num]

    type Distance[Num, V <: AbstractVector]     = Measure.Vector[V, m] with Measures.Distance[Num, V]
    type Speed[Num, V <: AbstractVector]        = Measure.Vector[V, m/s] with Measures.Speed[Num, V]
    type Acceleration[Num, V <: AbstractVector] = Measure.Vector[V, m/(s^_2)] with Measures.Acceleration[Num, V]
    type Force[Num, V <: AbstractVector]        = Measure.Vector[V, N] with Measures.Force[Num, V]
  }
}

trait MeasuresImplicits{
  implicit class VectorImplicits[V <: AbstractVector: ClassTag, Tpe <: PhysType](val v: Measure.Vector[V, Tpe]){
    def abs = PhysTyped[V#Num, Tpe](v.value.abs)(v.value.num.asInstanceOf[Numeric[V#Num]])
  }


}