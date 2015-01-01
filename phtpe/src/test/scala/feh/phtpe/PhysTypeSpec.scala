package feh.phtpe

import org.specs2.Specification
import feh.phtpe.short._

class PhysTypeSpec extends Specification {
  def is = s2""" ${ "PhysType Specification".title }

  __Equality__
    ${ PhysType.areEqual[kg**m/(s^_2), Newton] }
    ${ PhysType.areEqual[(kg**m/s)^_2, kg**m**N] }
    ${ PhysType.areEqual[(kg**m/s)^_2, kg**N] must beFalse }
    ${ PhysType.areEqual[Newton/kg, m/(s^_2)] }
    ${ PhysType.areEqual[Newton^_2, Newton ** Newton] }
    ${ PhysType.areEqual[s ^- _1, s ^ -[_1]] }
    ${ PhysType.areEqual[s / s, Neutral] }
    ${ PhysType.areEqual[kg ** ((m/s)^_2), N ** m] }
    ${ PhysType.areEqual[C/(kg ** ((m/s)^_2)), C/(N ** m)] }
  """
}
