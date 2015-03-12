package feh.phtpe

trait Measures{
  type Mass <: Measure.Scalar[_, _]
  type Time <: Measure.Scalar[_, _]
  type Temperature <: Measure.Scalar[_, _]

  type Distance        <: Measure.Vector[_ <: AbstractVector, _]
  type Speed           <: Measure.Vector[_ <: AbstractVector, _]
  type Acceleration    <: Measure.Vector[_ <: AbstractVector, _]
  type Force           <: Measure.Vector[_ <: AbstractVector, _]
}

object Measures{
  trait SI extends Measures{
    import short._

    type Mass = Measure.Scalar[_, kg]
    type Time = Measure.Scalar[_, s]

    type Distance     = Measure.Vector[_ <: AbstractVector, m]
    type Speed        = Measure.Vector[_ <: AbstractVector, m/s]
    type Acceleration = Measure.Vector[_ <: AbstractVector, m/(s^_2)]
    type Force        = Measure.Vector[_ <: AbstractVector, N]
  }
}