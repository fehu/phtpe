package feh.phtpe

import scala.language.experimental.macros

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
    }
    object Alias extends Alias
  }

  sealed trait IntegerConstant

  final class _1 extends IntegerConstant
  final class _2 extends IntegerConstant
  final class _3 extends IntegerConstant
  final class _4 extends IntegerConstant
  final class _5 extends IntegerConstant
  final class _6 extends IntegerConstant
  final class _7 extends IntegerConstant
  final class _8 extends IntegerConstant

  /** aborts on compile */
  def proveEqual[Tpe <: PhysType, Expected <: PhysType]: scala.Unit = macro TypeEqualProves.atCompile[Tpe, Expected]

  def areEqual[Tpe <: PhysType, Expected <: PhysType]: Boolean = macro TypeEqualProves.equal[Tpe, Expected]
}
