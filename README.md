Physical Typing
=====
_still under work_

```scala
import feh.phtpe._
import short._
import vectors._ // "feh.phtpe" %% "vectors" needed
```

|   |   |   |
|:--|:--|:-:|
|[PhysType](phtpe/src/main/scala/feh/phtpe/PhysType.scala)|A physical type|[specification](phtpe/test-reports/feh.phtpe.PhysTypeSpec.md) ([source](phtpe/src/test/scala/feh/phtpe/PhysTypeSpec.scala))|
|[PhysTyped[Num, Tpe]](phtpe/src/main/scala/feh/phtpe/PhysTyped.scala)|A physicaly typed numeric value|[specification](phtpe/test-reports/feh.phtpe.PhysTypedSpec.md) ([source](phtpe/src/test/scala/feh/phtpe/PhysTypedSpec.scala))|
| | Some Examples | [Examples](phtpe/test-reports/feh.phtpe.MiscExamples.md) |
|[AbstractVector](phtpe/src/main/scala/feh/phtpe/AbstractVector.scala)| Abstract Vector, supporting physical typing | |
|[Vector](vectors/src/main/scala/feh/phtpe/vectors/Vector.scala)| Vectors Implementation | [specification](vectors/test-reports/feh.phtpe.vectors.VectorsSpec.md) ([source](vectors/src/test/scala/feh/phtpe/vectors/VectorsSpec.scala))|
|[VectorMeasures](vectors/src/main/scala/feh/phtpe/vectors/VectorMeasures.scala)| PhysTyped Vector wrappers | [specification](vectors/test-reports/feh.phtpe.vectors.VectorMeasuresSpec.md) ([source](vectors/src/test/scala/feh/phtpe/vectors/VectorMeasuresSpec.scala))| 


```scala
scala> 3.of[kg] + 4.of[kg]
res0: feh.phtpe.PhysTyped[Int,feh.phtpe.short.kg] = PhysTyped(7)

scala> 3.of[kg] + 4.of[s]
<console>:17: error: could not find implicit value for parameter ev: feh.phtpe.PhysTypeEqualEvidence[feh.phtpe.short.kg,feh.phtpe.short.s]
              3.of[kg] + 4.of[s]

scala> 3.of[kg] + 4.of[N/m**(s^_2)]
res1: feh.phtpe.PhysTyped[Int,feh.phtpe.short.kg] = PhysTyped(7)
              
scala> var mass: Double|kg = _
mass: feh.phtpe.PhysTyped.|[Double,feh.phtpe.short.kg] = null

scala> mass = 12
mass: feh.phtpe.PhysTyped.|[Double,feh.phtpe.short.kg] = PhysTyped(12.0)
              
scala> def force(acceleration: Double|(m/(s^_2))): Double|Newton = acceleration*mass
force: (acceleration: feh.phtpe.PhysTyped.|[Double,feh.phtpe./[feh.phtpe.short.m,feh.phtpe.^[feh.phtpe.short.s,feh.phtpe._2]]])feh.phtpe.PhysTyped.|[Double,feh.phtpe.Newton]

scala> (3, 3, 2).vector[m/s] * 4.of[s] - (1, 1, 0).vector[m]*4 =@= ones[Int, _3].of[m]*8
res5: Boolean = true

scala> (3, 3, 2).vector[m/s] * 4.of[s] - (1, 1, 0).vector[m]*4 =@= (1, 2, 3).vector[m]
res8: Boolean = false

scala> (3, 3, 2).vector[m/s] * 4.of[s] - (1, 1, 0).vector[m]*4 =@= (1, 2, 3).vector[kg]
<console>:17: error: could not find implicit value for parameter ev: feh.phtpe.PhysTypeEqualEvidence[feh.phtpe.**[feh.phtpe./[feh.phtpe.short.m,feh.phtpe.short.s],feh.phtpe.short.s],feh.phtpe.short.kg]
              (3, 3, 2).vector[m/s] * 4.of[s] - (1, 1, 0).vector[m]*4 =@= (1, 2, 3).vector[kg]

scala> (3, 3, 2).vector[m/s] * 4.of[s] - (1, 1, 0).vector[m/s]*4 =@= (1, 2, 3).vector[m/s]
<console>:17: error: could not find implicit value for parameter ev: feh.phtpe.PhysTypeEqualEvidence[feh.phtpe.**[feh.phtpe./[feh.phtpe.short.m,feh.phtpe.short.s],feh.phtpe.short.s],feh.phtpe./[feh.phtpe.short.m,feh.phtpe.short.s]]
              (3, 3, 2).vector[m/s] * 4.of[s] - (1, 1, 0).vector[m/s]*4 =@= (1, 2, 3).vector[m/s]

```

|   |   |   |
|:--|:-:|:-:|
|**TODO**:| [typing](phtpe/TODO.md) | [vectors](vectors/TODO.md) |