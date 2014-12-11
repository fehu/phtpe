package feh.phtpe

import org.specs2.Specification

import feh.phtpe.PhysTyped._
import feh.phtpe.short._

class PhysTypedSpec extends Specification {

  def is = s2""" ${"PhysTyped Specification".name}

  __Equality__
    ${ 5.@@[N] phEq 5.of[Newton] }
    ${ 6.@@[N] phEq 5.of[Newton] must beFalse }
    ${ 5.@@[N] phEq 5.of[kg] must beFalse }
    ${ 5.@@[(kg**m/s)^_2] phEq 5.@@[kg**m**N] }
    ${ 5.@@[(kg**m/s)^_2] phEq 5.@@[kg**N] must beFalse }

  __Sum__
    ${ 1.@@[N] + 2.@@[N] phEq 3.@@[N] }
    ${ 1.@@[N] + 2.@@[N] phEq 10.@@[N] must beFalse }
    ${ 1.@@[N] soft_+ 2.@@[N] map (_ phEq 3.@@[N]) must beSome(true) }
    ${ 1.@@[N] soft_+ 2.@@[N] map (_ phEq 10.@@[N]) must beSome(false) }
    ${ 1.@@[N] soft_+ 2.@@[kg] map (_ phEq 3.@@[N]) must beNone }

  __Subtraction__
    ${ 10.@@[kg] - 5.@@[kg] phEq 5.@@[kg] }
    ${ 10.@@[kg] - 2.@@[kg]  phEq 5.@@[kg] must beFalse }
    ${ 1.@@[kg] soft_- 2.@@[kg] map (_ phEq (-1).@@[kg]) must beSome(true) }
    ${ 1.@@[kg] soft_- 2.@@[kg] map (_ phEq (-1).@@[N]) must beSome(false) }
    ${ 1.@@[kg] soft_- 2.@@[kg] map (_ phEq   1.@@[kg]) must beSome(false) }
    ${ 1.@@[kg] soft_- 2.@@[s] map (_ phEq 3.@@[kg]) must beNone }
  """
}
