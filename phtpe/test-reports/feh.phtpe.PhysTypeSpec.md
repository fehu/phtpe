## PhysTypeSpec
  
     import feh.phtpe._
     import short._
 
   __Equality__
          PhysType.areEqual[kg**m/(s^_2), Newton]  (+)    
             PhysType.areEqual[(kg**m/s)^_2, kg**m**N]  (+)    
             PhysType.areEqual[(kg**m/s)^_2, kg**N] must beFalse  (+)    
             PhysType.areEqual[Newton/kg, m/(s^_2)]  (+)    
             PhysType.areEqual[s ^- _1, s ^ -[_1]]  (+)  
    
| PhysTypeSpec |
| Finished in 10 ms |
| 5 examples, 0 failure, 0 error |