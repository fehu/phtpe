package feh.phtpe.vectors

import feh.phtpe._
import PhysType.PositiveIntegerConstant
import scala.language.experimental.macros

trait VectorTypeEvidence[V <: AbstractVector]{
  type Num = V#Num
  type Dim = V#Dim

  implicit val num: Numeric[Num]
  val dim: Dim

  def toSeq(v: V): Seq[Num] //= productIterator.toSeq.asInstanceOf[Seq[Num]]
  def fromSeq: Seq[Num] => V
}


object Vector{
  trait VectorCreation{
    /** N must be numeric */
    def zeros[N, D <: PositiveIntegerConstant]: AbstractVector{ type Dim = D; type Num = N } = macro VectorMacros.zeros[N, D]
    def ones [N, D <: PositiveIntegerConstant]: AbstractVector{ type Dim = D; type Num = N } = macro VectorMacros.ones[N, D]
  }

  trait VectorImplicits{
    implicit def vectorTypeEvidence[V <: AbstractVector]: VectorTypeEvidence[V] = ???

    implicit def aVectorIsNumeric[V <: AbstractVector](implicit ev: VectorTypeEvidence[V]) = new AVectorIsNumeric[V]

    implicit def tuple2IsVector[N: Numeric](t: (N, N)): Vector2D{ type Num = N } = ???
    implicit def tuple3IsVector[N: Numeric](t: (N, N, N)): Vector3D{ type Num = N } = ???
  }

  class AVectorIsNumeric[V <: AbstractVector](implicit ev: VectorTypeEvidence[V]) extends Numeric[V] {
    private def num = ev.num

    def negate(x: V) = ev.fromSeq(ev.toSeq(x).map(num.negate))
    def plus(x: V, y: V) = ev.fromSeq(ev.toSeq(x) zip ev.toSeq(y) map (num.plus _).tupled )
    def minus(x: V, y: V) = ev.fromSeq(ev.toSeq(x) zip ev.toSeq(y) map (num.minus _).tupled )
    def times(x: V, y: V) = ev.fromSeq(ev.toSeq(x) zip ev.toSeq(y) map (num.times _).tupled )

    def compare(x: V, y: V) = invalid("compare")
    def fromInt(x: Int) = invalid("fromInt")
    def toDouble(x: V) = invalid("toDouble")
    def toFloat(x: V) = invalid("toFloat")
    def toLong(x: V) = invalid("toLong")
    def toInt(x: V) = invalid("toInt")
  }

  trait VectorOpsImplicits {
    implicit class VectorOps[V <: AbstractVector](v: V)(implicit ev: VectorTypeEvidence[V], num: Numeric[V])
    {
      def *(n: V#Num): V = ev.fromSeq(ev.toSeq(v) map (ev.num.times(_, n)))
    }

    implicit class FractionalVectorOps[V <: AbstractVector: Numeric](v: V)(implicit ev: VectorTypeEvidence[V], num: Fractional[V#Num])
    {
      def abs: V#Num = sqrt( ev.toSeq(v).map(n => ev.num.times(n, n)).sum(ev.num) )
      def normalize: V = {
        val seq = ev.toSeq(v)
        val sum = seq.sum(ev.num)
        ev fromSeq seq.map(divide(_, sum))
      }
      def /(n: V#Num): V = ev fromSeq ev.toSeq(v).map(divide(_, n))
    }
  }


  private def invalid(op: String) = sys.error(s"operation '$op' is not supported for vectors")

  private def divide[Num: Numeric](n1: Num, n2: Num): Num = implicitly[Numeric[Num]] match {
    case integral: Integral[_]      => integral.quot(n1, n2)
    case fractional: Fractional[_]  => fractional.div(n1, n2)
  }

  private def sqrt[Num: Fractional](n: Num): Num = (n match {
    case d: Double  => math.sqrt(d)
    case f: Float   => math.sqrt(f).toFloat
  }).asInstanceOf[Num]
  
  object Ops extends VectorCreation with VectorImplicits with VectorOpsImplicits
}

trait Vector2D extends AbstractVector{
  self: Product2[_, _] =>

  type Dim = _2
}

trait Vector3D extends AbstractVector{
  self: Product3[_, _, _] =>

  type Dim = _3
}

