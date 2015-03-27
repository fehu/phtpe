## Conversion and Compatibility
  
     __PhysTypeSystem Compatibility__
         import BigScaleIsSICompatible._; areConvertible[LightYear, Meter] must beTrue (+)    
            import BigScaleIsSICompatible._; areConvertible[LightYear, Second] must beFalse (+)    
            import BigScaleIsSICompatible._; areConvertible[Year, Second] must beTrue (+)    
    
        __Measures Conversion__
            4f.of[Kilogram].convert[Kilogram] =@= 4f.of[Kilogram] (+)    
            def foo(l: Float|LightYear) = l.convert[Meter]; foo(2).value == 9.4605284e15f * 2 (+)  
  
    
| Conversion and Compatibility |
| Finished in 3 ms |
| 5 examples, 0 failure, 0 error |