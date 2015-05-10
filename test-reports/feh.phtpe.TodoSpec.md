## TODO

[+] __== won't work__
```scala
	[+] ( 1.of[kg] == 1.of[kg] ).isFailure  
	[+] ( (1 to 10 map(_.of[kg]) map (_ * 2)) == Range(2, 22, 2).map(_.of[kg]) ).isFailure  
```

[+] __ordering for PhysTyped__
```scala
          won't compile: (1 to 10 ).map(_.of[kg]).min =@= 1.of[kg]
          won't compile: (1 to 10 ).map(_.of[kg]).max =@= 10.of[kg]
```

[+] __PhysTyped is Numeric__
```scala
          won't compile: (1 to 10).map(_.of[kg]).sum      =@= (1 to 10).sum.of[kg]
          won't compile: (1 to 10).map(_.of[kg]).product  =@= (1 to 10).product.of[kg]
     
```

| TODO | Finished in 13 ms | 2 examples, 0 failure, 0 error |

