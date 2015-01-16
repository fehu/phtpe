package feh.phtpe

object Units{
  object SI{
    class Second    private() extends PhysType.Atom
    class Meter     private() extends PhysType.Atom
    class Kilogram  private() extends PhysType.Atom
    class Ampere    private() extends PhysType.Atom
    class Kelvin    private() extends PhysType.Atom
    class Mole      private() extends PhysType.Atom
    class Candela   private() extends PhysType.Atom

    import feh.phtpe.Units.SI.Short._

    class Newton    private() extends (kg ** m / (s ^ _2)) with PhysType.Unit
    class Pascal    private() extends (N/(m^_2)) with PhysType.Unit
    class Joule     private() extends (N ** m) with PhysType.Unit
    class Watt      private() extends (Joule/s) with PhysType.Unit
    class Coulomb   private() extends (A ** s) with PhysType.Unit
    class Volt      private() extends (Joule / Coulomb) with PhysType.Unit
    class Farad     private() extends (Joule / Coulomb) with PhysType.Unit

    trait Short{
      final type s   = Second
      final type m   = Meter
      final type kg  = Kilogram
      final type A   = Ampere
      final type K   = Kelvin
      final type mol = Mole
      final type cd  = Candela

      final type N   = Newton
      final type J   = Joule
      final type W   = Watt
      final type C   = Coulomb
      final type V   = Volt
      final type F   = Farad
    }

    object Short extends Short

    trait Alias{
      lazy val short: Short = Short

      final type Second   = SI.Second
      final type Meter    = SI.Meter
      final type Kilogram = SI.Kilogram
      final type Ampere   = SI.Ampere
      final type Kelvin   = SI.Kelvin
      final type Mole     = SI.Mole
      final type Candela  = SI.Candela

      final type Newton   = SI.Newton
      final type Pascal   = SI.Pascal
      final type Joule    = SI.Joule
      final type Watt     = SI.Watt
      final type Coulomb  = SI.Coulomb
      final type Volt     = SI.Volt
      final type Farad    = SI.Farad
    }
  }
}
