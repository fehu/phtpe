package feh.phtpe

object Units{
  import feh.phtpe.PhysType._
  import feh.phtpe.PhysType.Composite.Alias._

  object SI{
    final class Second extends Atom
    final class Meter extends Atom
    final class Kilogram extends Atom

    import feh.phtpe.Units.SI.Alias._

    final class Newton extends (kg ** m / (s ^ _2)) with Unit

    trait Alias{
      final type s   = Second
      final type m   = Meter
      final type kg  = Kilogram

      final type N   = Newton
    }

    object Alias extends Alias
  }
}

trait Measures{

}
