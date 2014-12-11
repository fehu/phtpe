## PhysTypedSpec
  
```scala
import feh.phtpe.PhysTyped._
import feh.phtpe._
import short._
```  
 
[+] __Equality Tests__
```scala
    [+] 5.@@[N] phEq 5.of[Newton]      
    [+] 6.@@[N] phEq 5.of[Newton] must beFalse      
    [+] 5.@@[N] phEq 5.of[kg] must beFalse      
    [+] 5.@@[(kg**m/s)^_2] phEq (10 / 2).@@[kg**m**N]      
    [+] 5.@@[(kg**m/s)^_2] phEq (10 / 2).@@[kg**N] must beFalse  
```
            
| PhysTypedSpec |
| Finished in 11 ms |
| 5 examples, 0 failure, 0 error |
