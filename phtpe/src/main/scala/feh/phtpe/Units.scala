package feh.phtpe

object Units{
  object SI{
    class Second private() extends PhysType.Atom
    class Meter private() extends PhysType.Atom
    class Kilogram private() extends PhysType.Atom

    import feh.phtpe.Units.SI.Short._

    class Newton private() extends (kg ** m / (s ^ _2)) with PhysType.Unit
    
    trait Short{
      final type s   = Second
      final type m   = Meter
      final type kg  = Kilogram

      final type N   = Newton
    }

    object Short extends Short

    trait Alias{
      lazy val short: Short = Short

      final type Second   = SI.Second
      final type Meter    = SI.Meter
      final type Kilogram = SI.Kilogram

      final type Newton   = SI.Newton
    }
  }
}

trait Measures{

}
