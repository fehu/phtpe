package feh.phtpe

import org.specs2.Specification
import feh.phtpe.short._
import org.specs2.execute.Snippets

class PhysTypeSpec extends Specification {
  def is = s2""" ${"PhysType Specification".name}
    import feh.phtpe._
    import short._

  __Equality__
    ${ PhysType.areEqual[kg**m/(s^_2), Newton] }
    ${ PhysType.areEqual[(kg**m/s)^_2, kg**m**N] }
    ${ PhysType.areEqual[(kg**m/s)^_2, kg**N] must beFalse }
  """
}

/*

 */