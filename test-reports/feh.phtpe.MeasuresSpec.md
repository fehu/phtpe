## __PhysTypeSystem and Measures

[+] __PhysTypeSystem__
```scala
	[+] PhysType.areEqual[Measures.SI.Float#Distance#T, Meter] 
	[+] def foo[Sys <: PhysTypeSystem](v: Float|Sys#Mass) = v; foo[PhysTypeSystem.SI](5f).typeEqual[Kilogram] 
```

[+] __Measures__
```scala
	[+] PhysType.areEqual[Measures.SI.Float#Distance#T, Meter]  
	[+] PhysType.areEqual[Measures.SI#Speed#T, Meter / Second] 
	[+] type M = Measures.SI; PhysType.areEqual[M#Speed#T, M#Distance#T / M#Time#T]  
    
	[+] def foo[M <: Measures[_]](v: M#Mass) = true; foo[Measures.SI.Float](5f) 
	[+] trait Foo[M <: Measures[_]]{ def x: M#Mass }; new Foo[Measures.SI.Float]{ def x = 5f }; true 
   
```

| __PhysTypeSystem and Measures | Finished in 31 ms | 7 examples, 0 failure, 0 error |

