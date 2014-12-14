package feh.phtpe.vectors

import feh.phtpe.PhysType.PositiveIntegerConstant
import feh.phtpe.{AbstractVector, PhysType}

import scala.reflect.macros.whitebox

object VectorMacros {

  def zeros[N: c.WeakTypeTag, D <: PositiveIntegerConstant: c.WeakTypeTag](c: whitebox.Context): c.Expr[AbstractVector{ type Dim = D; type Num = N }] =
    empty[N, D](c)(c.universe.reify((num: Numeric[N]) => num.zero))

  def ones[N: c.WeakTypeTag, D <: PositiveIntegerConstant: c.WeakTypeTag](c: whitebox.Context): c.Expr[AbstractVector{ type Dim = D; type Num = N }] =
    empty[N, D](c)(c.universe.reify((num: Numeric[N]) => num.one))

  private def empty[N: c.WeakTypeTag, D <: PositiveIntegerConstant: c.WeakTypeTag](c: whitebox.Context)(fill: => c.Expr[Numeric[N] => N]) = {
    import c.universe._

    val N = c.weakTypeOf[N]
    val D = c.weakTypeOf[D]

    val numTree = q"implicitly[Numeric[$N]]"
    def f = q"$fill($numTree)"

    def buildVector(body: c.Tree) = D match {
      case tpe if tpe <:< typeOf[PhysType._2] => q"new Tuple2($f, $f)     with feh.phtpe.vectors.Vector2D{ type Num = $N; $body }"
      case tpe if tpe <:< typeOf[PhysType._3] => q"new Tuple3($f, $f, $f) with feh.phtpe.vectors.Vector3D{ type Num = $N; $body }"
    }
    c.Expr[AbstractVector{ type Dim = D; type Num = N }](
      buildVector{ q"def num: Numeric[Num] = $numTree" }
    )
  }
}
