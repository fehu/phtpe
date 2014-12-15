## Vector Measures Specification

[+] __Creation__
```scala
	[+] zeros[Int, _2].of[kg]    phEquals (0, 0).vector[kg]  
	[+] zeros[Double, _3].of[m]  phEquals (0, 0, 0).vector[m]  
	[+] ones[Float, _2].of[m/s]  phEquals (1, 1).vector[m/s]  
	[+] ones[Long, _3].of[N]     phEquals (1, 1, 1).vector[N]  
```

[+] __Elements__
```scala
	[+] zeros[Float, _3].of[N/m]*5 |> {v => (v.x =@= v.y) and (v.y =@= v.z) and (v.z =@= 0.of[kg/(s^_2)]) }  
	[+] (zeros[Float, _2].of[N/m]._2 =@= 1.of[kg/(s^_2)]) must beFalse  
```

[+] __Scalar Operations__
```scala
	[+] ones[Int, _3].of[m/s] * 10.of[s]          phEquals (10, 10, 10).vector[m]  
	[+] ones[Float, _2].of[m] / 2.of[s]           phEquals (.5, .5).vector[m/s]  
	[+] ones[Float, _2].of[N].abs                 phEquals math.sqrt(2).toFloat.of[N]  
	[+] ones[Float, _2].of[N/m].*(5).normalize._1 phEquals (1f/math.sqrt(2).toFloat).of[kg/(s^_2)]  
	[+] (2, 3).vector[m/s] * 2.of[s]              phEquals (4, 6).vector[m]  
	[+] (2d, 2d).vector[m] / 2.of[s]              phEquals ones[Int, _2].of[m/s]  
	[+] (2d, 2d).vector[m] / 2.of[m]              phEquals ones[Int, _2]  
```

[+] __Vector Operations__
```scala
	[+] (1, 1).vector[m] + (1, 0).vector[m]            phEquals  (2, 1).vector[m]  
	[+] (2, 1).vector[N] - (1, 0).vector[N]            phEquals ones[Int, _2].of[N]  
	[+] (2, 3).vector[m] ** (3, 2).vector[s^-_1]       phEquals (6, 6).vector[m/s]  
	[+] (2, 3).vector[m] `.` (3, 2).vector[s^-_1]      phEquals 12.of[m/s]  
	[+] (1, 2, 3).vector[m] X (3, 2, 1).vector[s^-_1]  phEquals (-4, 8, -4).vector[m/s]  
   
```

| Vector Measures Specification | Finished in 13 ms | 18 examples, 20 expectations, 0 failure, 0 error |