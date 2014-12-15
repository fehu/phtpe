package feh.phtpe.vectors

import feh.phtpe._
import feh.phtpe.vectors.Vector.VectorOpsImplicits

trait VectorMeasureOpsImplicits extends VectorOpsImplicits{

  implicit final class VectorMeasureFromTupleCreation[T <: Product, V <: AbstractVector](t: T)
                                                                                        (implicit isVector: T => V,
                                                                                                  ev: VectorTypeEvidence[V]
                                                                                          )
  {
    def vector[Tpe <: PhysType] = PhysTyped[V, Tpe](t)
  }
  
  implicit class VectorMeasureOps[V <: AbstractVector, Tpe <: PhysType](v: Measure.Vector[V, Tpe])
                                                                       (implicit ev: VectorTypeEvidence[V])
  {
    def *(n: V#Num): V|Tpe = new VectorOps(v.value) * n
    def *[Tpe2 <: PhysType](n: V#Num|Tpe2): V|(Tpe**Tpe2) = v.value * n.value
  }

  implicit class FractionalVectorMeasureOps[V <: AbstractVector, Tpe <: PhysType](v: Measure.Vector[V, Tpe])
                                                                                 (implicit ev: VectorTypeEvidence[V], num: Fractional[V#Num])
  {
    def abs: V#Num|Tpe = v.value.abs
    def normalize: V|Tpe  = v.value.normalize
    def /(n: V#Num): V|Tpe = v.value / n
    def /[Tpe2 <: PhysType](n: V#Num|Tpe2): V|(Tpe/Tpe2) = v.value / n.value
  }

  implicit class Vector2DMeasureOps[V <: Vector2D, Tpe <: PhysType](v: Measure.Vector[V, Tpe])
                                                                   (implicit ev: VectorTypeEvidence[V])
  {
    private implicit def num = ev.num.asInstanceOf[Numeric[V#Num]]

    def _1: V#Num|Tpe = v.value.asInstanceOf[Product2[V#Num, V#Num]]._1
    def _2: V#Num|Tpe = v.value.asInstanceOf[Product2[V#Num, V#Num]]._2

    def x = _1
    def y = _2
  }

  implicit class Vector4DMeasureOps[V <: Vector3D, Tpe <: PhysType](v: Measure.Vector[V, Tpe])
                                                                   (implicit ev: VectorTypeEvidence[V])
  {
    private implicit def num = ev.num.asInstanceOf[Numeric[V#Num]]

    def _1: V#Num|Tpe = v.value.asInstanceOf[Product3[V#Num, V#Num, V#Num]]._1
    def _2: V#Num|Tpe = v.value.asInstanceOf[Product3[V#Num, V#Num, V#Num]]._2
    def _3: V#Num|Tpe = v.value.asInstanceOf[Product3[V#Num, V#Num, V#Num]]._3

    def x = _1
    def y = _2
    def z = _3
  }
}
