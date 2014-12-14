package feh.phtpe.vectors

import feh.phtpe._
import feh.phtpe.vectors.Vector.VectorOpsImplicits

trait VectorMeasureOpsImplicits extends VectorOpsImplicits{

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
}
