## CollectionsOfPhisTypedSpec
    
        __map__
             ( 1 to 10 map (_.of[kg]) map(_ * 2) ).map(_.value) == Range(2, 22, 2)  (+)    
    
        __reduce__
             ( 1 to 10 map(_.of[kg]) ).reduceLeft(_ + _)  =@= 55.of[kg]  (+)    
    
        __fold__
             (0.of[kg] /: (1 to 10))(_ + _.of[kg])        =@= 55.of[kg]  (+)    
             (0.of[kg] /: (1 to 10).map(_.of[kg]))(_ + _) =@= 55.of[kg]  (+)    
    
            __min/max__ TODO (*)    
    
            __sum/product__ TODO (*)  
  
    
| CollectionsOfPhisTypedSpec |
| Finished in 14 ms |
| 6 examples, 0 failure, 0 error, 2 pending |