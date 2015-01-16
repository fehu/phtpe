## PhysType Specification

[+] __Equality__
```scala
	[+] PhysType.areEqual[kg**m/(s^_2), Newton]  
	[+] PhysType.areEqual[(kg**m/s)^_2, kg**m**N]  
	[+] PhysType.areEqual[(kg**m/s)^_2, kg**N] must beFalse  
	[+] PhysType.areEqual[Newton/kg, m/(s^_2)]  
	[+] PhysType.areEqual[Newton^_2, Newton ** Newton]  
	[+] PhysType.areEqual[s ^- _1, s ^ -[_1]]  
	[+] PhysType.areEqual[s / s, Neutral]  
	[+] PhysType.areEqual[kg ** ((m/s)^_2), N ** m]  
	[+] PhysType.areEqual[C/(kg ** ((m/s)^_2)), C/(N ** m)]  
	[+] PhysType.areEqual[Pascal**(m^_2), Newton]  
```

[+] __Aliases__
```scala
	[+] type X = kg/(s^_2);    PhysType.areEqual[X, N/m]  
	[+] type X = kg**(s^-_2);  PhysType.areEqual[X, N/m]  
	[+] type X = kg**(kg^-_1); PhysType.areEqual[X, Neutral]  
   
```

| PhysType Specification | Finished in 22 ms | 13 examples, 0 failure, 0 error |

