package feh.phtpe

import org.specs2.Specification

class MeasuresSpec extends Specification{
  def is = s2""" ${ "__PhysTypeSystem and Measures".title }
    __PhysTypeSystem__
    ${PhysType.areEqual[Measures.SI.Float#Distance#T, Meter]}
    ${def foo[Sys <: PhysTypeSystem](v: Float|Sys#Mass) = v; foo[PhysTypeSystem.SI](5f).typeEqual[Kilogram]}

    __Measures__
    ${PhysType.areEqual[Measures.SI.Float#Speed#T, Meter / Second] }
    ${PhysType.areEqual[Measures.SI.Float#Speed#T, Measures.SI.Float#Distance#T / Measures.SI.Float#Time#T] }
    ${def foo[M <: Measures[_]](v: M#Mass) = true; foo[Measures.SI.Float](5f)}
    ${trait Foo[M <: Measures[_]]{ def x: M#Mass }; new Foo[Measures.SI.Float]{ def x = 5f }; true}
  """
}