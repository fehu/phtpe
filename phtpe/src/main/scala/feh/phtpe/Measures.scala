package feh.phtpe

trait AbstractMeasures[Sys <: AbstractPhysTypeSystem]
trait Measures[Sys <: PhysTypeSystem] extends Measures.Scalar[Sys] with Measures.Vector[Sys]

object Measures{
  import short._

  trait Scalar[Sys <: PhysTypeSystem] extends AbstractMeasures[Sys]{
    type Mass               <: Measure.Scalar[_, Sys#Mass]
    type Time               <: Measure.Scalar[_, Sys#Time]
    type Temperature        <: Measure.Scalar[_, Sys#Temperature]
//    type Luminance          <: Measure.Scalar[_, Sys#Luminance]
    type LuminousIntensity  <: Measure.Scalar[_, Sys#LuminousIntensity]
  }
  trait Vector[Sys <: PhysTypeSystem] extends AbstractMeasures[Sys] {
    type Distance     <: Measure.Vector[_ <: AbstractVector, Sys#Distance]
    type Speed        <: Measure.Vector[_ <: AbstractVector, Sys#Distance / Sys#Time]
    type Acceleration <: Measure.Vector[_ <: AbstractVector, Sys#Distance / (Sys#Time ^ _2)]
    type Force        <: Measure.Vector[_ <: AbstractVector, Sys#Mass ** Sys#Distance / (Sys#Time ^ _2)]
//    type Speed           <: Measure.Vector[_ <: AbstractVector, Sys#Speed]
//    type Acceleration    <: Measure.Vector[_ <: AbstractVector, Sys#Acceleration]
//    type Force           <: Measure.Vector[_ <: AbstractVector, Sys#Force]
  }

  trait SI extends Measures[PhysTypeSystem.SI]

  object SI {
//    class M[Ma, Ti, Te, Lu, Di, Sp, Ac, Fo] extends SI{
//      type Mass <: Measure.Scalar[Ma, kg]
//      type Time <: Measure.Scalar[Ti, s]
//      type Temperature <: Measure.Scalar[Te, K]
//      type Luminance <: Measure.Scalar[Lu, cd/(m^_2)]
//
//      type Distance     <: Measure.Vector[_ <: AbstractVector{ type Num = Di }, m]
//      type Speed        <: Measure.Vector[_ <: AbstractVector{ type Num = Sp }, m/s]
//      type Acceleration <: Measure.Vector[_ <: AbstractVector{ type Num = Ac }, m/(s^_2)]
//      type Force        <: Measure.Vector[_ <: AbstractVector{ type Num = Fo }, kg**m/(s^_2)] //N
//    }

    type Float = SI {
      type Mass         = Measure.Scalar[scala.Float, kg]
//      type Time       = Measure.Scalar[Ti, s]
      type Temperature  = Measure.Scalar[scala.Float, K]
      type Luminance    = Measure.Scalar[scala.Float, cd/(m^_2)]

      type Distance     = Measure.Vector[_ <: AbstractVector{ type Num = scala.Float }, m]
      type Speed        = Measure.Vector[_ <: AbstractVector{ type Num = scala.Float }, m/s]
      type Acceleration = Measure.Vector[_ <: AbstractVector{ type Num = scala.Float }, m/(s^_2)]
      type Force        = Measure.Vector[_ <: AbstractVector{ type Num = scala.Float }, kg**m/(s^_2)] //N
    }
//    M[scala.Float, Ti, scala.Float, scala.Float, scala.Float, scala.Float, scala.Float, scala.Float]
//    def Float[Ti] = new M[scala.Float, Ti, scala.Float, scala.Float, scala.Float, scala.Float, scala.Float, scala.Float]
//
//    val FloatBI = Float[BigInt]
//    type FloatBI = FloatBI.type

//    object M{
//      class Scalar[Ma, Ti, Te, Lu]{
//        type Mass <: Measure.Scalar[Ma, kg]
//        type Time <: Measure.Scalar[Ti, s]
//        type Temperature <: Measure.Scalar[Te, K]
//        type Luminance <: Measure.Scalar[Lu, cd/(m^_2)]
//      }
//    }
  }

  class SICompatible[FromS <: PhysTypeSystem, From <: Measures[FromS],
                     ToS <: PhysTypeSystem, To <: Measures[ToS]] (implicit comp: PhysTypeSystemCompatibility[FromS, ToS]) {
    protected def conversion[N, PhT[_] <: PhysTyped[N, _], F <: PhysType, T <: PhysType] = PhysTypedConversion.inst[N, PhT, F, T] _
  }


}