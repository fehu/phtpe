## PhysTypeSpec
  
```scala  
import feh.phtpe._
import short._
```

[+] __Equality Tests__ 
```scala 
    [+] PhysType.areEqual[kg**m/(s^_2), Newton]
    [+] PhysType.areEqual[(kg**m/s)^_2, kg**m**N]    
    [+] PhysType.areEqual[(kg**m/s)^_2, kg**N] must beFalse
```
    
| PhysTypeSpec |
| Finished in 10 ms |
| 3 examples, 0 failure, 0 error |