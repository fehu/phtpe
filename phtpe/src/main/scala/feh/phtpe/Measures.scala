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

  trait MeasuresCompatibility[N, SFrom <: PhysTypeSystem, From <: Measures[SFrom],
                                 STo <: PhysTypeSystem, To <: Measures[STo]]
    extends AbstractMeasuresCompatibility[N, From, To]
  {
    protected def mkMass = conversion[SFrom#Mass, STo#Mass]
    protected def mkTime = conversion[SFrom#Time, STo#Time]
    protected def mkDist = conversion[SFrom#Distance, STo#Distance]
    protected def mkTemp = conversion[SFrom#Temperature, STo#Temperature]
    protected def mkLumI = conversion[SFrom#LuminousIntensity, STo#LuminousIntensity]
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

  trait SiCompatibility[N, SFrom <: PhysTypeSystem, From <: Measures[SFrom]]
    extends MeasuresCompatibility[N, SFrom, From, PhysTypeSystem.SI, SI]

  trait BigScale extends Measures[PhysTypeSystem.BigScale] with TimeBigInt[PhysTypeSystem.BigScale]

  object BigScale{
    trait Float extends BigScale{
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

  class BigScaleMeasuresAreSICompatible[N] extends SiCompatibility[N, PhysTypeSystem.BigScale, BigScale]

  object BigScaleMeasuresAreSICompatible{
    object Float extends BigScaleMeasuresAreSICompatible[Float]{
      implicit lazy val time = mkTime(((365 * 24 + 5) * 60 + 48) * 60 + 46) //365 days 5 hours 48 minutes 46 seconds
      implicit lazy val dist = mkDist(9.4605284e15f)
      implicit lazy val mass = mkMass(1)
      implicit lazy val temp = mkTemp(1)
      implicit lazy val lumI = mkLumI(1)
    }
  }
}

trait AbstractMeasuresCompatibility[N, From <: AbstractMeasures[_], To <: AbstractMeasures[_]]{
  protected def conversion[F <: PhysType, T <: PhysType] = PhysTypedConversion.inst[N, F, T] _
}
