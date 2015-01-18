package feh.phtpe

import org.specs2.Specification
import feh.phtpe.short._

class TodoSpec extends Specification{
  def is = s2""" ${ "TODO".title }

    __== won't work__
    ${ ( 1.of[kg] == 1.of[kg] ).isFailure }
    ${ ( (1 to 10 map(_.of[kg]) map (_ * 2)) == Range(2, 22, 2).map(_.of[kg]) ).isFailure }

    __ordering for PhysTyped__
      won't compile: (1 to 10 ).map(_.of[kg]).min =@= 1.of[kg]
      won't compile: (1 to 10 ).map(_.of[kg]).max =@= 10.of[kg]

    __PhysTyped is Numeric__
      won't compile: (1 to 10).map(_.of[kg]).sum      =@= (1 to 10).sum.of[kg]
      won't compile: (1 to 10).map(_.of[kg]).product  =@= (1 to 10).product.of[kg]
  """

}
