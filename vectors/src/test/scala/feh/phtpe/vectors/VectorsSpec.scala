package feh.phtpe.vectors

import feh.util._
import feh.phtpe._
import org.specs2.Specification

class VectorsSpec extends Specification{ def is = s2""" ${ "Vectors Specification".title }
  __Creation__
    ${ zeros[Int, _2]    mustEqual (0, 0)}
    ${ zeros[Double, _3] mustEqual (0, 0, 0)}
    ${ ones[Float, _2]   mustEqual (1, 1)}
    ${ ones[Long, _3]    mustEqual (1, 1, 1)}

  __Elements__
    ${ ones[Long, _3]    |> {v => v._1 === 1 and v._2 === 1 and v._3 === 1} }
    ${ zeros[Double, _2] |> {v => v._1 === 0 and v._2 === 0} }

  __Scalar Operations__
    ${ ones[Int, _2]  * 10              mustEqual (10, 10) }
    ${ ones[Float, _2]/ 2               mustEqual (.5, .5) }
    ${ ones[Float, _2].abs              mustEqual math.sqrt(2).toFloat }
    ${ (ones[Float, _2]*5).normalize._1 mustEqual 1f/math.sqrt(2).toFloat }
    ${ (2, 3).vec * 2                   mustEqual (4, 6) }
    ${ (2d, 2d, 2d).vec / 2             mustEqual ones[Int, _3] }
  """
}
