package feh.phtpe.vectors

import feh.phtpe._
import PhysType.PositiveIntegerConstant
import scala.language.experimental.macros

trait VectorTypeEvidence[V <: AbstractVector]{
  type Num = V#Num
  type Dim = V#Dim

  val num: Numeric[Num]
  val dim: Dim

  def toSeq(v: V): Seq[Num]
  def fromSeq: Seq[Num] => V
}


object Vector{
  trait VectorCreation{
    /** N must be numeric */
    def zeros[N, D <: PositiveIntegerConstant]: AbstractVector{ type Dim = D; type Num = N } = macro VectorMacros.zeros[N, D]
    def ones [N, D <: PositiveIntegerConstant]: AbstractVector{ type Dim = D; type Num = N } = macro VectorMacros.ones[N, D]

    implicit final class VectorFromTupleCreation[T <: Product, V <: AbstractVector](t: T)(implicit isVector: T => V){
      def vec: V = t
    }
  }

  trait VectorImplicits{
    implicit def vectorIsNumeric[V <: AbstractVector](implicit ev: VectorTypeEvidence[V]) = new VectorIsNumeric[V]

    implicit def vectorTypeEvidence[V <: AbstractVector]: VectorTypeEvidence[V] = macro VectorMacros.vectorTypeEvidence[V]

    implicit def tuple2IsVector[N: Numeric](t: (N, N)): Vector2D{ type Num = N } =
      new Tuple2(t._1, t._2) with Vector2D { type Num = N }

    implicit def tuple3IsVector[N: Numeric](t: Tuple3[N, N, N]): Vector3D{ type Num = N } =
      new Tuple3(t._1, t._2, t._3) with Vector3D { type Num = N }
  }

  class VectorIsNumeric[V <: AbstractVector](implicit ev: VectorTypeEvidence[V]) extends Numeric[V] {
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
      def unary_- = num.negate(v)
      def +(v2: V) = num.plus(v, v2)
      def -(v2: V) = num.minus(v, v2)

      def *(n: V#Num): V = ev.fromSeq(ev.toSeq(v) map (ev.num.times(_, n)))

      /** by-elem product */
      def **(v2: V): V = num.times(v, v2)

      /** dot product */
      def `.`(v2: V): V#Num = ev.toSeq(**(v2)).sum(ev.num)
      def dot(v2: V): V#Num = `.`(v2)

//      def toDouble[V2 <: AbstractVector{ type Dim = V#Dim; type Num = Double}]: V2 = invalid("toDouble")
//      def toFloat[V2 <: AbstractVector{ type Dim = V#Dim; type Num = Float}]: V2 = invalid("toFloat")
//      def toLong[V2 <: AbstractVector{ type Dim = V#Dim; type Num = Long}]: V2 = invalid("toLong")
//      def toInt[V2 <: AbstractVector{ type Dim = V#Dim; type Num = Int}]: V2 = invalid("toInt")
    }

    implicit class FractionalVectorOps[V <: AbstractVector: Numeric](v: V)(implicit ev: VectorTypeEvidence[V], num: Fractional[V#Num]){
      def abs: V#Num = sqrt( ev.toSeq(v).map(n => ev.num.times(n, n)).sum(ev.num) )
      def normalize: V = {
        val seq = ev.toSeq(v)
        val a = abs
        ev fromSeq seq.map(divide(_, a))
      }
      def /(n: V#Num): V = ev fromSeq ev.toSeq(v).map(divide(_, n))
    }

    implicit class Vector3DOps[V <: Vector3D](v: V)(implicit ev: VectorTypeEvidence[V]){
      private lazy val vv = v.asInstanceOf[Product3[V#Num, V#Num, V#Num]]

      import ev.num.mkNumericOps
      /** vector product */
      def X(v2: V): V = {
        val vv2 = v2.asInstanceOf[Product3[V#Num, V#Num, V#Num]]
        ev.fromSeq(Seq(
          vv._2*vv2._3 - vv._3*vv2._2,
          vv._3*vv2._1 - vv._1*vv2._3,
          vv._1*vv2._2 - vv._2*vv2._1
        ))
      }
    }
  }


  private def invalid(op: String) = sys.error(s"operation '$op' is not supported for vectors")

  private def divide[Num: Numeric](n1: Num, n2: Num): Num = implicitly[Numeric[Num]] match {
    case integral: Integral[_]      => integral.quot(n1, n2)
    case fractional: Fractional[_]  => fractional.div(n1, n2)
  }

  private def sqrt[Num: Fractional](n: Num): Num = n match {
    case d: Double  => math.sqrt(d).asInstanceOf[Num]
    case f: Float   => math.sqrt(f).toFloat.asInstanceOf[Num]
  }

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

