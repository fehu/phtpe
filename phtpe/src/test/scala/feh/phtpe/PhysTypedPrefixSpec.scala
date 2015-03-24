package feh.phtpe

import feh.phtpe.Prefixes._
import org.specs2.Specification

class PhysTypedPrefixSpec extends Specification {
  def is = "SI Prefixes\n".title ^
    new PhysTypedIncrementalPrefixSpec ^
    new PhysTypedDecrementalPrefixSpec ^
    new NumericOverflow ^
    new PhysTypedBundledPrefixSpec
}

class PhysTypedIncrementalPrefixSpec extends Specification {
  def is = s2""" ${ "Incremental".title }
    __ Int__
    ${ 1.of[Deca, Meter]   phEquals 10.of[Meter] }
    ${ 1.of[Hecto, Meter]  phEquals 100.of[Meter] }
    ${ 1.of[Kilo, Meter]   phEquals 1000.of[Meter] }
    ${ 1.of[Mega, Meter]   phEquals 1e6.of[Meter] }
    ${ 1.of[Giga, Meter]   phEquals 1e9.of[Meter] }

    __Long__
    ${ 1L.of[Deca, Meter]  phEquals 10.of[Meter] }
    ${ 1L.of[Hecto, Meter] phEquals 100.of[Meter] }
    ${ 1L.of[Kilo, Meter]  phEquals 1000.of[Meter] }
    ${ 1L.of[Mega, Meter]  phEquals 1e6.of[Meter] }
    ${ 1L.of[Giga, Meter]  phEquals 1e9.of[Meter] }
    ${ 1L.of[Tera, Meter]  phEquals 1e12.of[Meter] }

    __BigInt__
    ${ BigInt(1).of[Deca, Meter]  phEquals 10.of[Meter] }
    ${ BigInt(1).of[Hecto, Meter] phEquals 100.of[Meter] }
    ${ BigInt(1).of[Kilo, Meter]  phEquals 1000.of[Meter] }
    ${ BigInt(1).of[Mega, Meter]  phEquals 1e6.of[Meter] }
    ${ BigInt(1).of[Giga, Meter]  phEquals 1e9.of[Meter] }
    ${ BigInt(1).of[Tera, Meter]  phEquals 1e12.of[Meter] }

    __Float__
    ${ 1f.of[Deca, Meter]  phEquals 10.of[Meter] }
    ${ 1f.of[Hecto, Meter] phEquals 100.of[Meter] }
    ${ 1f.of[Kilo, Meter]  phEquals 1000.of[Meter] }
    ${ 1f.of[Mega, Meter]  phEquals 1e6f.of[Meter] }
    ${ 1f.of[Giga, Meter]  phEquals 1e9f.of[Meter] }
    ${ 1f.of[Tera, Meter]  phEquals 1e12f.of[Meter] }

    note that:
      ${ (1f.of[Tera, Meter] phEquals 1e12d.of[Meter]) isFailure }
    because
      ${ 1e12f != 1e12d }

    __Double__
    ${ 1d.of[Deca, Meter]  phEquals 10.of[Meter] }
    ${ 1d.of[Hecto, Meter] phEquals 100.of[Meter] }
    ${ 1d.of[Kilo, Meter]  phEquals 1000.of[Meter] }
    ${ 1d.of[Mega, Meter]  phEquals 1e6.of[Meter] }
    ${ 1d.of[Giga, Meter]  phEquals 1e9.of[Meter] }
    ${ 1d.of[Tera, Meter]  phEquals 1e12.of[Meter] }

    __BigDecimal__
    ${ BigDecimal(1).of[Deca, Meter]  phEquals 10.of[Meter] }
    ${ BigDecimal(1).of[Hecto, Meter] phEquals 100.of[Meter] }
    ${ BigDecimal(1).of[Kilo, Meter]  phEquals 1000.of[Meter] }
    ${ BigDecimal(1).of[Mega, Meter]  phEquals 1e6.of[Meter] }
    ${ BigDecimal(1).of[Giga, Meter]  phEquals 1e9.of[Meter] }
    ${ BigDecimal(1).of[Tera, Meter]  phEquals 1e12.of[Meter] }
  """
}


class PhysTypedDecrementalPrefixSpec extends Specification {
  def is = s2""" ${ "Decremental".title }
    __Float__
    ${ 1f.of[Deci, Meter]  phEquals 0.1f.of[Meter] }
    ${ 1f.of[Centi, Meter] phEquals 0.01f.of[Meter] }
    ${ 1f.of[Milli, Meter] phEquals 0.001f.of[Meter] }
    ${ 1f.of[Micro, Meter] phEquals 1e-6f.of[Meter] }
    ${ 1f.of[Nano, Meter]  phEquals 1e-9f.of[Meter] }
    ${ 1f.of[Pico, Meter]  phEquals 1e-12f.of[Meter] }

    __Double__
    ${ 1d.of[Deci, Meter]  phEquals 0.1.of[Meter] }
    ${ 1d.of[Centi, Meter] phEquals 0.01.of[Meter] }
    ${ 1d.of[Milli, Meter] phEquals 0.001.of[Meter] }
    ${ 1d.of[Micro, Meter] phEquals 1e-6.of[Meter] }
    ${ 1d.of[Nano, Meter]  phEquals 1e-9.of[Meter] }
    ${ 1d.of[Pico, Meter]  phEquals 1e-12.of[Meter] }

    __BigDecimal__
    ${ BigDecimal(1).of[Deci, Meter]  phEquals 0.1.of[Meter] }
    ${ BigDecimal(1).of[Centi, Meter] phEquals 0.01.of[Meter] }
    ${ BigDecimal(1).of[Milli, Meter] phEquals 0.001.of[Meter] }
    ${ BigDecimal(1).of[Micro, Meter] phEquals 1e-6.of[Meter] }
    ${ BigDecimal(1).of[Nano, Meter]  phEquals 1e-9.of[Meter] }
    ${ BigDecimal(1).of[Pico, Meter]  phEquals 1e-12.of[Meter] }
  """
}

class NumericOverflow extends Specification{
  def is = s2""" ${ "WARNING".title }
    __Prefixes might cause errors due to java's primitive types overflow__
    ${ 1000.of[Giga, Volt].value != 1L.of[Tera, Volt].value }
  """
}

class PhysTypedBundledPrefixSpec extends Specification{
  def is = s2""" ${ "Prefixed Units".title }
    __ __
    ${ type km = Prefixed[Kilo, Meter]; 1.of[km]    phEquals 1000.of[Meter] }
    ${ type km = Kilo@@Meter;           1.of[km]    phEquals 1000.of[Meter] }
    ${ type gr = Milli@@Kilogram;       1f.of[gr]   phEquals 1e-3f.of[Kilogram] }
    ${ type pF = Pico@@Farad;           1e12.of[pF] phEquals 1.of[Farad] }

    ${ type GV = Giga@@Volt; type TV = Tera@@Volt; 1000L.of[GV] phEquals 1L.of[TV] }

    ${def foo(d: Int|Meter) = true;  type km = Kilo@@Meter; foo(2.of[km])}
    ${def foo(d: Int|Meter) = true;                         foo(2.of[Kilo@@Meter])}
    ${def foo[X](x: X|Meter) = true; type km = Kilo@@Meter; foo(5f.of[km]); true}

    ${trait Foo[X]{ def x: X|Meter }; new Foo[Float]{ def x = 5f.of[Kilo@@Meter] }; true}
    ${class Foo[X](x: X|Meter); type km = Kilo@@Meter;  new Foo[Float](5.of[km]); true}
    ${case class Foo[X](x: X|Meter); type km = Kilo@@Meter; Foo(5f.of[km]); true}
  """
}
