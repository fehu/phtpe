package feh.phtpe

import feh.phtpe.PhysType.PositiveIntegerConstant

trait AbstractVector{
  type Num
  type Dim <: PositiveIntegerConstant
}
