## Prefixed Units

[+] __ __
```scala
	[+] type km = Prefixed[Kilo, Meter]; 1.of[km]    phEquals 1000.of[Meter]  
	[+] type km = Kilo@@Meter;           1.of[km]    phEquals 1000.of[Meter]  
	[+] type gr = Milli@@Kilogram;       1f.of[gr]   phEquals 1e-3f.of[Kilogram]  
	[+] type pF = Pico@@Farad;           1e12.of[pF] phEquals 1.of[Farad]  
    
	[+] type GV = Giga@@Volt; type TV = Tera@@Volt; 1000L.of[GV] phEquals 1L.of[TV]  
    
	[+] def foo(d: Int|Meter) = true;  type km = Kilo@@Meter; foo(2.of[km]) 
	[+] def foo(d: Int|Meter) = true;                         foo(2.of[Kilo@@Meter]) 
	[+] def foo[X](x: X|Meter) = true; type km = Kilo@@Meter; foo(5f.of[km]); true 
    
	[+] trait Foo[X]{ def x: X|Meter }; new Foo[Float]{ def x = 5f.of[Kilo@@Meter] }; true 
	[+] class Foo[X](x: X|Meter); type km = Kilo@@Meter;  new Foo[Float](5.of[km]); true 
	[+] case class Foo[X](x: X|Meter); type km = Kilo@@Meter; Foo(5f.of[km]); true 
   
```

| Prefixed Units | Finished in 43 ms | 11 examples, 0 failure, 0 error |

