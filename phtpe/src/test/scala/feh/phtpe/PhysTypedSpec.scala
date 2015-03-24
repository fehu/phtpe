package feh.phtpe

import feh.phtpe.short._
import org.specs2.Specification

class PhysTypedSpec extends Specification {

  def is = s2""" ${"PhysTyped Specification".name}

  __Equality__
    ${ 5.@@[N] phEquals 5.of[Newton] }
    ${ 6.of[N] phEquals 5.of[Newton] must beFalse }
    ${ 5.of[N] phEquals 5.of[kg] must beFalse }

    ${ 5.of[(kg**m/s)^_2] phEquals 5.of[kg**m**N] }
    ${ 5.of[(kg**m/s)^_2] phEquals 5.of[kg**N] must beFalse }

  __Negation__
    ${ (-2).of[N] phEquals -2.of[N] }
    ${ -2.of[N] phEquals 2.of[N] must beFalse }

  __Sum__
    ${ 1.of[N] + 2.of[N] phEquals 3.of[N] }
    ${ 1.of[N] + 2.of[N] phEquals 10.of[N] must beFalse }

    ${ 1.of[N] soft_+ 2.of[N] map (_ phEquals 3.of[N]) must beSome(true) }
    ${ 1.of[N] soft_+ 2.of[N] map (_ phEquals 10.of[N]) must beSome(false) }
    ${ 1.of[N] soft_+ 2.of[kg] map (_ phEquals 3.of[N]) must beNone }

  __Subtraction__
    ${ 10.of[kg] - 5.of[kg] phEquals 5.of[kg] }
    ${ 10.of[kg] - 2.of[kg]  phEquals 5.of[kg] must beFalse }

    ${ 1.of[kg] soft_- 2.of[kg] map (_ phEquals -1.of[kg]) must beSome(true) }
    ${ 1.of[kg] soft_- 2.of[kg] map (_ phEquals -1.of[N]) must beSome(false) }
    ${ 1.of[kg] soft_- 2.of[kg] map (_ phEquals  1.of[kg]) must beSome(false) }
    ${ 1.of[kg] soft_- 2.of[s] map (_ phEquals 3.of[kg]) must beNone }

  __Multiplication__
    ${ 2.of[N] * 3.of[s] phEquals 6.of[kg**m/s] }
    ${ 2.of[N] * 3.of[s] phEquals 6.of[kg**m] must beFalse}
    ${ 2.of[N] * 2.of[s] phEquals 6.of[kg**m/s] must beFalse}

    ${ 2.of[N] * 3 phEquals 6.of[N] }
    ${ 3 ** 2.of[N] phEquals 6.of[N] }

  __Division__
    ${ (6.of[N] / 3.of[kg]) phEquals 2.of[m/(s^_2)] }
    ${ (6.of[N] / 3.of[kg]) phEquals 3.of[m/(s^_2)] must beFalse }

    ${ 10.of[N] / 2 phEquals 5.of[N] }
    ${ 10 \ 2.of[N] phEquals 5.of[N ^-_1] }

    ${ (7.of[N] / 2.of[kg]) phEquals 3.of[m/(s^_2)] }
    ${ (7d.of[N] / 2d.of[kg]) phEquals 3d.of[m/(s^_2)] must beFalse }
    ${ (7d.of[N] / 2d.of[kg]) phEquals 3.5.of[m/(s^_2)] }
    ${ (7d.of[kg] / 2d.of[kg]) phEquals 3.5 }

  __Power__
    ${ 3.of[s] ^ _2 phEquals 9.of[s^_2] }
    ${ 3.of[s] ^ _2 phEquals 9.of[s] must beFalse}
    ${ 3.of[s] ^ _2 phEquals 10.of[s^_2] must beFalse}

  __As Argument__
    ${def foo(v: Float | Kilogram) = true; foo(5f)}
  """
}
