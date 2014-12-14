package feh.phtpe.vectors

import feh.phtpe._
import org.specs2.Specification

class VectorsSpec extends Specification{ def is = s2""" ${ "Vectors Specification".title }
  __Creation__
    ${ zeros[Int, _2]    mustEqual (0, 0)}
    ${ zeros[Double, _3] mustEqual (0, 0, 0)}
    ${ ones[Float, _2]   mustEqual (1, 1)}
    ${ ones[Long, _3]    mustEqual (1, 1, 1)}

  __Scalar Operations__
    ${ ones[Int, _2]  * 10          mustEqual (10, 10) }
    ${ ones[Float, _2]/ 2           mustEqual (.5, .5) }
    ${ ones[Float, _2].abs          mustEqual 1 }
    ${(ones[Float, _2]*5).normalize mustEqual (1, 1) }
  """
}
