package feh.phtpe

import scala.language.experimental.macros
import scala.reflect.ClassTag

sealed trait PhysType

object PhysType {
  /** A named system unit */
  trait Unit {
    self: PhysType =>
  }

  trait Atom extends PhysType with Unit
  sealed trait Composite extends PhysType

  object Composite{
    trait Unary[P <: PhysType] extends Composite
    trait Binary[L <: PhysType, R <: PhysType] extends Composite

    object Unary{
      trait Power[Tpe <: PhysType, Pow <: IntegerConstant] extends Unary[Tpe]
    }

    object Binary{
      trait Multiplication[L <: PhysType, R <: PhysType] extends Binary[L, R]
      trait Division[L <: PhysType, R <: PhysType] extends Binary[L, R]
    }

    trait Alias{
//      final type x[L <: PhysType, R <: PhysType]  = Binary.Multiplication[L, R]
      final type **[L <: PhysType, R <: PhysType] = Binary.Multiplication[L, R]
      final type /[L <: PhysType, R <: PhysType]  = Binary.Division[L, R]
      final type ^[Tpe <: PhysType, Pow <: IntegerConstant] = Unary.Power[Tpe, Pow]

      final type -[C <: IntegerConstant] = NegativeIntegerConstant[C]
      final type ^-[Tpe <: PhysType, Pow <: IntegerConstant] = Unary.Power[Tpe, -[Pow]]
    }
    object Alias extends Alias
  }

  sealed trait IntegerConstant{ def int: Int }

  class _1 protected() extends IntegerConstant{ def int = 1 }
  class _2 protected() extends IntegerConstant{ def int = 2 }
  class _3 protected() extends IntegerConstant{ def int = 3 }
  class _4 protected() extends IntegerConstant{ def int = 4 }
  class _5 protected() extends IntegerConstant{ def int = 5 }
  class _6 protected() extends IntegerConstant{ def int = 6 }
  class _7 protected() extends IntegerConstant{ def int = 7 }
  class _8 protected() extends IntegerConstant{ def int = 8 }

  case object _1 extends _1
  case object _2 extends _2
  case object _3 extends _3
  case object _4 extends _4
  case object _5 extends _5
  case object _6 extends _6
  case object _7 extends _7
  case object _8 extends _8

  trait NegativeIntegerConstant[Original <: IntegerConstant] extends IntegerConstant

  /** aborts on compile */
  def proveEqual[Tpe <: PhysType, Expected <: PhysType]: scala.Unit = macro PhysTypeEqualProves.atCompile[Tpe, Expected]

  def areEqual[Tpe <: PhysType, Expected <: PhysType]: Boolean = macro PhysTypeEqualProves.equal[Tpe, Expected]

  implicit def equalEvidence[L <: PhysType, R <: PhysType]: PhysTypeEqualEvidence[L, R] = macro PhysTypeEqualProves.evidence[L, R]
  implicit def weakEqualEvidence[L <: PhysType, R <: PhysType]: WeakPhysTypeEqualEvidence[L, R] = macro PhysTypeEqualProves.weakEvidence[L, R]
}

class PhysTypeEqualEvidence[L <: PhysType, R <: PhysType]
class WeakPhysTypeEqualEvidence[L <: PhysType, R <: PhysType](val equal: Boolean){
  def asOption[Res](res: =>Res) = if(equal) Some(res) else None
}
