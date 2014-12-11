package feh.phtpe

import org.specs2.Specification
import org.specs2.execute.Snippets

import feh.phtpe.PhysTyped._
import feh.phtpe.short._

class PhysTypedSpec extends Specification with Snippets{

  def is = s2""" ${"PhysTyped Specification".name}

  __Equality Tests__
    ${ 5.@@[N] phEq 5.of[Newton] }
    ${ 6.@@[N] phEq 5.of[Newton] must beFalse }
    ${ 5.@@[N] phEq 5.of[kg] must beFalse }
    ${ 5.@@[(kg**m/s)^_2] phEq (10 / 2).@@[kg**m**N] }
    ${ 5.@@[(kg**m/s)^_2] phEq (10 / 2).@@[kg**N] must beFalse }
  """
}

/*

 */