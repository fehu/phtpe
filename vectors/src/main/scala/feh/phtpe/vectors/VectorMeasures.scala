package feh.phtpe.vectors

import feh.phtpe._
import feh.phtpe.vectors.Vector.VectorOpsImplicits

trait VectorMeasureOpsImplicits extends VectorOpsImplicits{

  implicit final class VectorMeasureFromTupleCreation[T <: Product, V <: AbstractVector](t: T)
                                                                                        (implicit isVector: T => V,
                                                                                                  ev: VectorTypeEvidence[V]
                                                                                          )
  {
    def vector[Tpe <: PhysType: PhysTypeStringDecomposition] = PhysTyped[V, Tpe](t)
  }
  
  implicit class VectorMeasureOps[V <: AbstractVector: VectorTypeEvidence, Tpe <: PhysType](v: Measure.Vector[V, Tpe]){
    private implicit def num = implicitly[VectorTypeEvidence[V]].num

    def *(n: V#Num)(implicit ev: PhysTypeStringDecomposition[Tpe]): V|Tpe = v.value * n
    def *[Tpe2 <: PhysType](n: V#Num|Tpe2)(implicit ev: PhysTypeStringDecomposition[Tpe**Tpe2]): V|(Tpe**Tpe2) = v.value * n.value

    /** by-elem */
    def **[Tpe2 <: PhysType](v2: AbstractVector{ type Dim = V#Dim; type Num = V#Num }|Tpe2)
                            (implicit ev: PhysTypeStringDecomposition[Tpe**Tpe2]): V|(Tpe**Tpe2) = v.value ** v2.value

    /** dot product */
    def `.`[Tpe2 <: PhysType](v2: AbstractVector{ type Dim = V#Dim; type Num = V#Num }|Tpe2)
                             (implicit ev: PhysTypeStringDecomposition[Tpe**Tpe2]): V#Num|(Tpe**Tpe2) = (v.value `.` v2.value)
    def dot[Tpe2 <: PhysType](v2: AbstractVector{ type Dim = V#Dim; type Num = V#Num }|Tpe2)
                             (implicit ev: PhysTypeStringDecomposition[Tpe**Tpe2]): V#Num|(Tpe**Tpe2) = `.`(v2)
  }

  implicit class FractionalVectorMeasureOps[V <: AbstractVector, Tpe <: PhysType](v: Measure.Vector[V, Tpe])
                                                                             (implicit ev: VectorTypeEvidence[V],
                                                                                       num: Fractional[V#Num],
//                                                                                       numV: Numeric[V],
                                                                                       d: PhysTypeStringDecomposition[Tpe])
  {
    def abs: V#Num|Tpe = v.value.abs
    def normalize: V|Tpe  = v.value.normalize
    def /(n: V#Num): V|Tpe = v.value / n
    def /[Tpe2 <: PhysType](v2: V#Num|Tpe2)(implicit ev2: PhysTypeStringDecomposition[Tpe/Tpe2]): V|(Tpe/Tpe2) = v.value / v2.value
  }

  implicit class Vector2DMeasureOps[V <: Vector2D: VectorTypeEvidence, Tpe <: PhysType: PhysTypeStringDecomposition](v: Measure.Vector[V, Tpe])
  {
    private implicit def num = implicitly[VectorTypeEvidence[V]].num.asInstanceOf[Numeric[V#Num]]

    def _1: V#Num|Tpe = v.value.asInstanceOf[Product2[V#Num, V#Num]]._1
    def _2: V#Num|Tpe = v.value.asInstanceOf[Product2[V#Num, V#Num]]._2

    def x = _1
    def y = _2
  }

  implicit class Vector3DMeasureOps[V <: Vector3D: VectorTypeEvidence, Tpe <: PhysType: PhysTypeStringDecomposition](v: Measure.Vector[V, Tpe])
  {
    private implicit def num = implicitly[VectorTypeEvidence[V]].num.asInstanceOf[Numeric[V#Num]]

    def _1: V#Num|Tpe = v.value.asInstanceOf[Product3[V#Num, V#Num, V#Num]]._1
    def _2: V#Num|Tpe = v.value.asInstanceOf[Product3[V#Num, V#Num, V#Num]]._2
    def _3: V#Num|Tpe = v.value.asInstanceOf[Product3[V#Num, V#Num, V#Num]]._3

    def x = _1
    def y = _2
    def z = _3

    /** vector product */
    def X[Tpe2 <: PhysType](n: V|Tpe2)(implicit ev: PhysTypeStringDecomposition[Tpe**Tpe2]): V|(Tpe**Tpe2) = v.value X n.value
  }
}
