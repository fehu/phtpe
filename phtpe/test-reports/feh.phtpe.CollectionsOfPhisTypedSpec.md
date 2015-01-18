## CollectionsOfPhisTypedSpec

[+] __map__
```scala
	[+] ( 1 to 10 map (_.of[kg]) map(_ * 2) ).map(_.value) == Range(2, 22, 2)
```

[+] __reduce__
```scala
	[+] ( 1 to 10 map(_.of[kg]) ).reduceLeft(R_ + _)  =@= 55.of[kg]
```

[+] __fold__
```scala
	[+] (0.of[kg] /: (1 to 10))(_ + _.of[kg])        =@= 55.of[kg]  
	[+] (0.of[kg] /: (1 to 10).map(_.of[kg]))(_ + _) =@= 55.of[kg]  
```

[todo] __min/max__

[todo] __sum/product__

| CollectionsOfPhisTypedSpec | Finished in 3 ms | 6 examples, 0 failure, 0 error, 2 pending |