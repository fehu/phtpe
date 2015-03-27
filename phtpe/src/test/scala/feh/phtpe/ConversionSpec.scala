package feh.phtpe

import org.specs2.Specification
import feh.phtpe.Units.BigScale.{Year, LightYear}
import feh.phtpe.PhysTypeSystem.BigScaleIsSICompatible
import feh.phtpe.Measures.BigScaleMeasuresAreSICompatible.Float._

class ConversionSpec extends Specification{
  def is = s2""" ${ "Conversion and Compatibility".title }
    __PhysTypeSystem Compatibility__
    ${import BigScaleIsSICompatible._; areConvertible[LightYear, Meter] must beTrue}
    ${import BigScaleIsSICompatible._; areConvertible[LightYear, Second] must beFalse}
    ${import BigScaleIsSICompatible._; areConvertible[Year, Second] must beTrue}

    __Measures Conversion__
    ${4f.of[Kilogram].convert[Kilogram] =@= 4f.of[Kilogram]}
    ${def foo(l: Float|LightYear) = l.convert[Meter]; foo(2).value == 9.4605284e15f * 2}

  """
}
