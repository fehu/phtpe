Physical Typing
=====

Scala 2.11.5: ![Build status by travis-ci](https://travis-ci.org/fehu/phtpe.svg)

```scala
import feh.phtpe._
import short._
import Prefixes._
import vectors._ // "feh.phtpe" %% "vectors" needed
```

|   |   |   |
|:--|:--|:-:|
|[PhysType](phtpe/src/main/scala/feh/phtpe/PhysType.scala)|A physical type|[specification](phtpe/test-reports/feh.phtpe.PhysTypeSpec.md) ([source](phtpe/src/test/scala/feh/phtpe/PhysTypeSpec.scala))|
|[PhysTyped[Num, Tpe]](phtpe/src/main/scala/feh/phtpe/PhysTyped.scala)|A physicaly typed numeric value|[specification](phtpe/test-reports/feh.phtpe.PhysTypedSpec.md) ([source](phtpe/src/test/scala/feh/phtpe/PhysTypedSpec.scala))|
|[Prefixes](phtpe/src/main/scala/feh/phtpe/Prefixes.scala)|SI prefixes for physicaly typed numerics|[specification](phtpe/test-reports/feh.phtpe.PhysTypedPrefixSpec.md) ([source](phtpe/src/test/scala/feh/phtpe/PhysTypedPrefixSpec.scala))|
| | Some Examples | [Examples](phtpe/test-reports/feh.phtpe.MiscExamples.md) |
|[AbstractVector](phtpe/src/main/scala/feh/phtpe/AbstractVector.scala)| Abstract Vector, supporting physical typing | |
|[Vector](vectors/src/main/scala/feh/phtpe/vectors/Vector.scala)| Vectors Implementation | [specification](vectors/test-reports/feh.phtpe.vectors.VectorsSpec.md) ([source](vectors/src/test/scala/feh/phtpe/vectors/VectorsSpec.scala))|
|[VectorMeasures](vectors/src/main/scala/feh/phtpe/vectors/VectorMeasures.scala)| PhysTyped Vector wrappers | [specification](vectors/test-reports/feh.phtpe.vectors.VectorMeasuresSpec.md) ([source](vectors/src/test/scala/feh/phtpe/vectors/VectorMeasuresSpec.scala))| 


```scala
scala> 3.of[kg] + 4.of[kg]
res0: feh.phtpe.PhysTyped[Int,feh.phtpe.short.kg] = 7|[Kilogram: 1]

scala> 3f.of[kg] + 4f.of[Milli, kg]
res1: feh.phtpe.PhysTyped[Float,feh.phtpe.short.kg] = 3.004|[Kilogram: 1]

scala> 3.of[kg] + 4.of[s]
<console>:17: error: could not find implicit value for parameter ev: feh.phtpe.PhysTypeEqualEvidence[feh.phtpe.short.kg,feh.phtpe.short.s]
              3.of[kg] + 4.of[s]
                       ^

scala> 3.of[kg] + 4.of[N/m**(s^_2)]
res2: feh.phtpe.PhysTyped[Int,feh.phtpe.short.kg] = 7|[Kilogram: 1]

scala> 6.of[m] / 2.of[m] + 1
res0: feh.phtpe.PhysTyped[Int,feh.phtpe./[feh.phtpe.short.m,feh.phtpe.short.m]] = 4|[Neutral: 0]
              
scala> val mass: Double|kg = 12
mass: feh.phtpe.|[Double,feh.phtpe.short.kg] = 12.0|[Kilogram: 1]

scala> (3, 3, 2).vector[m/s] * 4.of[s] - (1, 1, 0).vector[m]*4 =@= ones[Int, _3].of[m]*8
res5: Boolean = true

scala> (3, 3, 2).vector[m/s] * 4.of[s] - (1, 1, 0).vector[m]*4 =@= (1, 2, 3).vector[m]
res8: Boolean = false

scala> (3, 3, 2).vector[m/s] * 4.of[s] - (1, 1, 0).vector[m]*4 =@= (1, 2, 3).vector[kg]
<console>:17: error: could not find implicit value for parameter ev: feh.phtpe.PhysTypeEqualEvidence[feh.phtpe.**[feh.phtpe./[feh.phtpe.short.m,feh.phtpe.short.s],feh.phtpe.short.s],feh.phtpe.short.kg]
              (3, 3, 2).vector[m/s] * 4.of[s] - (1, 1, 0).vector[m]*4 =@= (1, 2, 3).vector[kg]
                                                                      ^              

scala> (3, 3, 2).vector[m/s] * 4.of[s] - (1, 1, 0).vector[m/s]*4 =@= (1, 2, 3).vector[m/s]
<console>:17: error: could not find implicit value for parameter ev: feh.phtpe.PhysTypeEqualEvidence[feh.phtpe.**[feh.phtpe./[feh.phtpe.short.m,feh.phtpe.short.s],feh.phtpe.short.s],feh.phtpe./[feh.phtpe.short.m,feh.phtpe.short.s]]
              (3, 3, 2).vector[m/s] * 4.of[s] - (1, 1, 0).vector[m/s]*4 =@= (1, 2, 3).vector[m/s]
                                              ^              

```

|   |   |   |
|:--|:-:|:-:|
|**TODO**:| [typing](phtpe/TODO.md) | [vectors](vectors/TODO.md) |