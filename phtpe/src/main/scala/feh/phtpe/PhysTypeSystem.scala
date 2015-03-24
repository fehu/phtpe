package feh.phtpe

import feh.phtpe.Prefixes._

trait AbstractPhysTypeSystem

trait AbstractPhysTypeSystemCompatibility[From <: AbstractPhysTypeSystem, To <: AbstractPhysTypeSystem]{
  protected def conversion[F <: PhysType, T <: PhysType] = PhysTypeConversion.inst[F, T]
}


trait PhysTypeSystem extends AbstractPhysTypeSystem{
  type Mass            <: PhysType
  type Time            <: PhysType
  type Distance        <: PhysType
  type Temperature     <: PhysType
//  type Luminance       <: PhysType
  type LuminousIntensity <: PhysType

  type Speed        = Distance / Time
  type Acceleration = Distance / (Time ^ _2)
  type Force        = Mass ** Distance / (Time ^ _2)
}

trait PhysTypeSystemCompatibility[From <: PhysTypeSystem, To <: PhysTypeSystem] extends AbstractPhysTypeSystemCompatibility[From, To]{
  implicit def mass = conversion[From#Mass, To#Mass]
  implicit def time = conversion[From#Time, To#Time]
  implicit def dist = conversion[From#Distance, To#Distance]
  implicit def temp = conversion[From#Temperature, To#Temperature]
  implicit def lumI = conversion[From#LuminousIntensity, To#LuminousIntensity]
}

object PhysTypeSystem{
  trait SI extends PhysTypeSystem{
    type Mass         = Kilogram
    type Luminance    = Candela
    type Temperature  = Kelvin
    type Distance     = Meter
    type Time         = Second
  }
  trait SICompatibility[From <: PhysTypeSystem] extends PhysTypeSystemCompatibility[From, SI]

}

