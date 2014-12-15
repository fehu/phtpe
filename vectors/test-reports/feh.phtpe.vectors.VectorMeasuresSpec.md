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
```

| Vector Measures Specification | Finished in 11 ms | 12 examples, 14 expectations, 0 failure, 0 error |