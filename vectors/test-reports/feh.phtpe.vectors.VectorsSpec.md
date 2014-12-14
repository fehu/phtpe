## Vectors Specification
  
[+] __Creation__
```scala
    [+] zeros[Int, _2]    mustEqual (0, 0) 
    [+] zeros[Double, _3] mustEqual (0, 0, 0) 
    [+] ones[Float, _2]   mustEqual (1, 1) 
    [+] ones[Long, _3]    mustEqual (1, 1, 1)
```  
  
[+] __Scalar Operations__
```scala
     [+]  ones[Int, _2]  * 10             mustEqual (10, 10)  
     [+]  ones[Float, _2]/ 2              mustEqual (.5, .5)  
     [+]  ones[Float, _2].abs             mustEqual math.sqrt(2).toFloat  
     [+] (ones[Float, _2]*5).normalize._1 mustEqual 1f/math.sqrt(2).toFloat
```
    
| Vectors Specification | Finished in 11 ms | 8 examples, 0 failure, 0 error |