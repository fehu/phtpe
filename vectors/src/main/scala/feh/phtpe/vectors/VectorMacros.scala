package feh.phtpe.vectors

import feh.phtpe.{PhysType, AbstractVector}
import feh.phtpe.PhysType.PositiveIntegerConstant
import scala.reflect.macros.whitebox

object VectorMacros {
  def zeros[N: c.WeakTypeTag, D <: PositiveIntegerConstant: c.WeakTypeTag](c: whitebox.Context): c.Expr[AbstractVector{ type Dim = D; type Num = N }] = {
    import c.universe._

    val N = c.weakTypeOf[N] //TypeRef(ThisType(scala), scala.Double, List())
    val D = c.weakTypeOf[D] //TypeRef(SingleType(SingleType(SingleType(ThisType(<root>), feh), feh.phtpe), feh.phtpe.package), TypeName("_2"), List())

    val numTree = N match {
      case tpe if tpe <:< typeOf[Double]  => q"implicitly[Numeric[Double]]"
      case tpe if tpe <:< typeOf[Float]   => q"implicitly[Numeric[Float]]"
      case tpe if tpe <:< typeOf[Long]    => q"implicitly[Numeric[Long]]"
      case tpe if tpe <:< typeOf[Int]     => q"implicitly[Numeric[Int]]"

    }

    val x = D match {
      case tpe if tpe <:< typeOf[PhysType._2] => q"""
        new Tuple2(0, 0) with feh.phtpe.vectors.Vector2D {
          type Num = $N
          def num: Numeric[Num] = $numTree
        }"""
    }

//    c.abort(NoPosition, s"N = ${showRaw(N)}\nD=${showRaw(D)}\nx=$x")
    c.Expr(x)
  }
}
