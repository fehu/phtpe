package feh.phtpe

import org.specs2.Specification
import Units.SI._
import Units.SI.Alias._
import PhysType.Composite.Alias._
import PhysType._

class PhysTypeSpec extends Specification{
  def is = s2""" ${"PhysType Specification".name}
    __Equality Tests__
    ${ areEqual[kg**m/(s^_2), Newton] }
    ${ areEqual[(kg**m/s)^_2, kg**m**N] }
    ${ areEqual[(kg**m/s)^_2, kg**N] must beFalse }
  """
}

/*

 */