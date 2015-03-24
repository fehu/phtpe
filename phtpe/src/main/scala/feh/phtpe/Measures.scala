package feh.phtpe

import feh.phtpe.PhysType.PositiveIntegerConstant

trait AbstractMeasures[Sys <: AbstractPhysTypeSystem]
trait Measures[Sys <: PhysTypeSystem] extends Measures.Scalar[Sys] with Measures.Vector[Sys]

object Measures{
  import short._

  trait Scalar[Sys <: PhysTypeSystem] extends AbstractMeasures[Sys]{
    type Mass <: MkMass[_]
    type Time <: MkTime[_]
    type Temperature <: MkTemperature[_]
    type LuminousIntensity <: MkLuminousIntensity[_]
//    type Luminance <: MkLuminance[_]


    protected type MkMass[N]              = Measure.Scalar[N, Sys#Mass]
    protected type MkTime[N]              = Measure.Scalar[N, Sys#Time]
    protected type MkTemperature[N]       = Measure.Scalar[N, Sys#Temperature]
//    type MkLuminance[N]                 = Measure.Scalar[N, Sys#Luminance]
    protected type MkLuminousIntensity[N] = Measure.Scalar[N, Sys#LuminousIntensity]
  }
  trait Vector[Sys <: PhysTypeSystem] extends AbstractMeasures[Sys] {
    type Distance <: MkDistance[_, _]
    type Speed <: MkSpeed[_, _]
    type Acceleration <: MkAcceleration[_, _]
    type Force <: MkForce[_, _]

    type MkVector[N, D <: PositiveIntegerConstant, T <: PhysType] =
      Measure.Vector[_ <: AbstractVector{type Num = N; type Dim = D}, T]

    type MkDistance[N, D <: PositiveIntegerConstant]      = MkVector[N, D, Sys#Distance]
    type MkSpeed[N, D <: PositiveIntegerConstant]         = MkVector[N, D, Sys#Speed]
    type MkAcceleration[N, D <: PositiveIntegerConstant]  = MkVector[N, D, Sys#Acceleration]
    type MkForce[N, D <: PositiveIntegerConstant]         = MkVector[N, D, Sys#Force]
  }

  trait SI extends Measures[PhysTypeSystem.SI]

  trait TimeBigInt[Sys <: PhysTypeSystem] extends Measures[Sys]{
    type Time = MkTime[BigInt]
  }

  object SI {
    trait Float extends SI {
      type Mass               = MkMass[scala.Float]
      type Temperature        = MkTemperature[scala.Float]
//      type Luminance          = MkLuminance[_]
      type LuminousIntensity  = MkLuminousIntensity[scala.Float]

      type Distance     <: MkDistance[scala.Float, _]
      type Speed        <: MkSpeed[scala.Float, _]
      type Acceleration <: MkAcceleration[scala.Float, _]
      type Force        <: MkForce[scala.Float, _]
    }
  }

  class SICompatible[FromS <: PhysTypeSystem, From <: Measures[FromS],
                     ToS <: PhysTypeSystem, To <: Measures[ToS]] (implicit comp: PhysTypeSystemCompatibility[FromS, ToS]) {
    protected def conversion[N, PhT[_] <: PhysTyped[N, _], F <: PhysType, T <: PhysType] = PhysTypedConversion.inst[N, PhT, F, T] _
  }


}