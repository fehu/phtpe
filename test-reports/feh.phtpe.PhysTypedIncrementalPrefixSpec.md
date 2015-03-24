## Incremental

[+] __ Int__
```scala
	[+] 1.of[Deca, Meter]   phEquals 10.of[Meter]  
	[+] 1.of[Hecto, Meter]  phEquals 100.of[Meter]  
	[+] 1.of[Kilo, Meter]   phEquals 1000.of[Meter]  
	[+] 1.of[Mega, Meter]   phEquals 1e6.of[Meter]  
	[+] 1.of[Giga, Meter]   phEquals 1e9.of[Meter]  
```

[+] __Long__
```scala
	[+] 1L.of[Deca, Meter]  phEquals 10.of[Meter]  
	[+] 1L.of[Hecto, Meter] phEquals 100.of[Meter]  
	[+] 1L.of[Kilo, Meter]  phEquals 1000.of[Meter]  
	[+] 1L.of[Mega, Meter]  phEquals 1e6.of[Meter]  
	[+] 1L.of[Giga, Meter]  phEquals 1e9.of[Meter]  
	[+] 1L.of[Tera, Meter]  phEquals 1e12.of[Meter]  
```

[+] __BigInt__
```scala
	[+] BigInt(1).of[Deca, Meter]  phEquals 10.of[Meter]  
	[+] BigInt(1).of[Hecto, Meter] phEquals 100.of[Meter]  
	[+] BigInt(1).of[Kilo, Meter]  phEquals 1000.of[Meter]  
	[+] BigInt(1).of[Mega, Meter]  phEquals 1e6.of[Meter]  
	[+] BigInt(1).of[Giga, Meter]  phEquals 1e9.of[Meter]  
	[+] BigInt(1).of[Tera, Meter]  phEquals 1e12.of[Meter]  
```

[+] __Float__
```scala
	[+] 1f.of[Deca, Meter]  phEquals 10.of[Meter]  
	[+] 1f.of[Hecto, Meter] phEquals 100.of[Meter]  
	[+] 1f.of[Kilo, Meter]  phEquals 1000.of[Meter]  
	[+] 1f.of[Mega, Meter]  phEquals 1e6f.of[Meter]  
	[+] 1f.of[Giga, Meter]  phEquals 1e9f.of[Meter]  
	[+] 1f.of[Tera, Meter]  phEquals 1e12f.of[Meter]  
    
        note that:
	[+] (1f.of[Tera, Meter] phEquals 1e12d.of[Meter]) isFailure  
        because
	[+] 1e12f != 1e12d  
```

[+] __Double__
```scala
	[+] 1d.of[Deca, Meter]  phEquals 10.of[Meter]  
	[+] 1d.of[Hecto, Meter] phEquals 100.of[Meter]  
	[+] 1d.of[Kilo, Meter]  phEquals 1000.of[Meter]  
	[+] 1d.of[Mega, Meter]  phEquals 1e6.of[Meter]  
	[+] 1d.of[Giga, Meter]  phEquals 1e9.of[Meter]  
	[+] 1d.of[Tera, Meter]  phEquals 1e12.of[Meter]  
```

[+] __BigDecimal__
```scala
	[+] BigDecimal(1).of[Deca, Meter]  phEquals 10.of[Meter]  
	[+] BigDecimal(1).of[Hecto, Meter] phEquals 100.of[Meter]  
	[+] BigDecimal(1).of[Kilo, Meter]  phEquals 1000.of[Meter]  
	[+] BigDecimal(1).of[Mega, Meter]  phEquals 1e6.of[Meter]  
	[+] BigDecimal(1).of[Giga, Meter]  phEquals 1e9.of[Meter]  
	[+] BigDecimal(1).of[Tera, Meter]  phEquals 1e12.of[Meter]  
   
```

| Incremental | Finished in 31 ms | 37 examples, 0 failure, 0 error |

