## PhysTypedSpec
  
```scala
import feh.phtpe.PhysTyped._
import feh.phtpe._
import short._
```  
 
[+] __Equality__
```scala
    [+] 5.@@[N] phEq 5.of[Newton]      
    [+] 6.@@[N] phEq 5.of[Newton] must beFalse      
    [+] 5.@@[N] phEq 5.of[kg] must beFalse      
    [+] 5.@@[(kg**m/s)^_2] phEq 5.@@[kg**m**N]  
    [+] 5.@@[(kg**m/s)^_2] phEq 5.@@[kg**N] must beFalse
```
[+] __Sum__
```scala
    [+] 1.@@[N] + 2.@@[N] phEq 3.@@[N]      
    [+] 1.@@[N] + 2.@@[N] phEq 10.@@[N] must beFalse      
    [+] 1.@@[N] soft_+ 2.@@[N] map (_ phEq 3.@@[N]) must beSome(true)      
    [+] 1.@@[N] soft_+ 2.@@[N] map (_ phEq 10.@@[N]) must beSome(false)      
    [+] 1.@@[N] soft_+ 2.@@[kg] map (_ phEq 3.@@[N]) must beNone    
```
[+] __Subtraction__ 
```scala
    [+] 10.@@[kg] - 5.@@[kg] phEq 5.@@[kg]      
    [+] 10.@@[kg] - 2.@@[kg]  phEq 5.@@[kg] must beFalse      
    [+] 1.@@[kg] soft_- 2.@@[kg] map (_ phEq (-1).@@[kg]) must beSome(true)      
    [+] 1.@@[kg] soft_- 2.@@[kg] map (_ phEq (-1).@@[N]) must beSome(false)      
    [+] 1.@@[kg] soft_- 2.@@[kg] map (_ phEq   1.@@[kg]) must beSome(false)      
    [+] 1.@@[kg] soft_- 2.@@[s] map (_ phEq 3.@@[kg]) must beNone            
```            
            
| PhysTypedSpec | Finished in 11 ms | 16 examples, 0 failure, 0 error |
