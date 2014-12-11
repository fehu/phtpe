package feh.phtpe

import org.specs2.Specification
import feh.phtpe.short._

class PhysTypeSpec extends Specification {
  def is = s2""" ${"PhysType Specification".name}
    import feh.phtpe._
    import short._

  __Equality__
    ${ PhysType.areEqual[kg**m/(s^_2), Newton] }
    ${ PhysType.areEqual[(kg**m/s)^_2, kg**m**N] }
    ${ PhysType.areEqual[(kg**m/s)^_2, kg**N] must beFalse }
    ${ PhysType.areEqual[Newton/kg, m/(s^_2)] }
    ${ PhysType.areEqual[s ^- _1, s ^ -[_1]] }
  """
}

/*

 */