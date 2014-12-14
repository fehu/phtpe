package feh.phtpe.vectors

import feh.phtpe.PhysType.PositiveIntegerConstant
import feh.phtpe._

import scala.reflect.macros.whitebox

object VectorMacros {

  def zeros[N: c.WeakTypeTag, D <: PositiveIntegerConstant: c.WeakTypeTag](c: whitebox.Context): c.Expr[AbstractVector{ type Dim = D; type Num = N }] =
    empty[N, D](c)(c.universe.reify((num: Numeric[N]) => num.zero))

  def ones[N: c.WeakTypeTag, D <: PositiveIntegerConstant: c.WeakTypeTag](c: whitebox.Context): c.Expr[AbstractVector{ type Dim = D; type Num = N }] =
    empty[N, D](c)(c.universe.reify((num: Numeric[N]) => num.one))

  def vectorTypeEvidence[V <: AbstractVector: c.WeakTypeTag](c: whitebox.Context): c.Expr[VectorTypeEvidence[V]] = {
    import c.universe._

    val V = weakTypeOf[V]
    val Num = V.member(TypeName("Num")).typeSignature

    def seqExpr(count: Int) = for(i <- 0 until count) yield c.Expr[V#Num](q"seq($i)")

    val (dim, fromSeq) = V.member(TypeName("Dim")).typeSignature match {
      case tpe if tpe <:< typeOf[_2] => q"feh.phtpe._2" ->
        q"(seq: Seq[$Num]) => ${create[V#Num, V#Dim](c)(Num, typeOf[_2], seqExpr(2))}"
    }

    val ev = q"""
      new VectorTypeEvidence[$V] {
        val dim: Dim = $dim
        val num: Numeric[Num] = implicitly[Numeric[$Num]]

        def toSeq(v: $V): Seq[Num] = v.productIterator.toList.asInstanceOf[Seq[Num]]
        def fromSeq: (Seq[Num]) => $V = $fromSeq
      }
    """
    c.Expr[VectorTypeEvidence[V]](ev)
  }

//  private def fromProduct[N: c.WeakTypeTag, D <: PositiveIntegerConstant: c.WeakTypeTag](c: whitebox.Context)(fill: => c.Expr[Numeric[N] => N]) = {
//
//  }

  private def empty[N: c.WeakTypeTag, D <: PositiveIntegerConstant: c.WeakTypeTag](c: whitebox.Context)(fill: => c.Expr[Numeric[N] => N]) = {
    import c.universe._

    val N = c.weakTypeOf[N]
    val D = c.weakTypeOf[D]

    val d = D match {
      case tpe if tpe <:< typeOf[PhysType._2] => 2
      case tpe if tpe <:< typeOf[PhysType._3] => 3
    }

    val numTree = q"implicitly[Numeric[$N]]"
    def f = for(_ <- 1 to d) yield c.Expr[N](q"$fill($numTree)")

    create[N, D](c)(N, D, f)
  }

  private def create[N, D <: PositiveIntegerConstant](c: whitebox.Context)(N: c.Type, D: c.Type, fill: Seq[c.Expr[N]]) = {
    import c.universe._

    val numTree = q"implicitly[Numeric[$N]]"

    def buildVector(body: c.Tree) = D match {
      case tpe if tpe <:< typeOf[PhysType._2] => q"""new Tuple2(${fill(0)}, ${fill(1)})
        with feh.phtpe.vectors.Vector2D{ type Num = $N; $body }"""
      case tpe if tpe <:< typeOf[PhysType._3] => q"""new Tuple3(${fill(0)}, ${fill(1)}, ${fill(2)})
        with feh.phtpe.vectors.Vector3D{ type Num = $N; $body }"""
    }

    c.Expr[AbstractVector{ type Dim = D; type Num = N } ](
      buildVector{ q"def num: Numeric[Num] = $numTree" }
    )
  }
}
