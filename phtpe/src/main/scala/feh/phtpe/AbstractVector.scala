package feh.phtpe

import feh.phtpe.PhysType.PositiveIntegerConstant

trait AbstractVector[Num, Dim <: PositiveIntegerConstant] {
  def num: Numeric[Num]
}
