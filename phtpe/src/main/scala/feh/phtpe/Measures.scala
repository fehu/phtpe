package feh.phtpe

trait Measures{
  type Mass[Num] <: Measure.Scalar[Num, _]
  type Time[Num] <: Measure.Scalar[Num, _]

  type Distance[Num, V <: AbstractVector]        <: Measure.Vector[V, _]
  type Speed[Num, V <: AbstractVector]           <: Measure.Vector[V, _]
  type Acceleration[Num, V <: AbstractVector]    <: Measure.Vector[V, _]
  type Force[Num, V <: AbstractVector]           <: Measure.Vector[V, _]
}

object Measures{
  object SI extends Measures{
    import short._

    type Mass[Num] = Measure.Scalar[Num, kg]
    type Time[Num] = Measure.Scalar[Num, s]

    type Distance[Num, V <: AbstractVector]     = Measure.Vector[V, m]
    type Speed[Num, V <: AbstractVector]        = Measure.Vector[V, m/s]
    type Acceleration[Num, V <: AbstractVector] = Measure.Vector[V, m/(s^_2)]
    type Force[Num, V <: AbstractVector]        = Measure.Vector[V, N]
  }
}