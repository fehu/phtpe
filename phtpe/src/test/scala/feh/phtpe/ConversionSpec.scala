package feh.phtpe

import feh.phtpe.Measures.BigScaleMeasuresAreSICompatible
import org.specs2.Specification
import Units.BigScale.LightYear
import PhysTypeSystem.BigScaleIsSICompatible._
import Measures.BigScaleMeasuresAreSICompatible.Float._

class ConversionSpec extends Specification{
  def is = s2""" ${ "Conversion and Compatibility".title }
    __PhysTypeSystem Compatibility__
    ${areConvertible[LightYear, Meter] must beTrue}
    ${areConvertible[LightYear, Second] must beFalse}

    __Measures Conversion @TODO__
    ${4f.of[Kilogram].convert[Kilogram](implicitly, BigScaleMeasuresAreSICompatible.Float.mass) =@= 4f.of[Kilogram]}
    ${def foo(l: Float | LightYear) = l.convert[Meter](implicitly, BigScaleMeasuresAreSICompatible.Float.dist); foo(2).value == 9.4605284e15f * 2}
  """

  //${4f.of[LightYear].convert[Meter](implicitly, BigScaleMeasuresAreSICompatible.Float.dist)}
  //${def foo(l: Float|LightYear) = l.convert[Meter](implicitly, BigScaleMeasuresAreSICompatible.Float.dist); foo(2).value == 9.4605284e15f*2}

  //${4f.of[Kilogram].convert[Kilogram] =@= 4f.of[Kilogram]}
}
