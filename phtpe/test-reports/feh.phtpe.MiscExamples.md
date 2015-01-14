## PhysTyped Usage Examples

[+] __Speed of an object in atmosphere__
```scala                                                      
     
      /* It is not serious, just `phtpe` usage example */
      class SpeedInAtmosphereApproximation(val mass: Double|kg, val airResistance: Double|(m/s) => Double|N){
        private var currentSpeed: Double|(m/s) = 0d
        def engineWorking(force: Double|Newton, time: Double|Second): Double|(m/s) = {
          val resistance = airResistance(currentSpeed)
          val finalForce = force - resistance
          val speed = finalForce*time/mass
          currentSpeed = speed
          speed
        }
      }
      val speedApproximator1 = new SpeedInAtmosphereApproximation(10.of[kg], _ * 0.01.of[kg/s])
	  val speedApproximator2 = new SpeedInAtmosphereApproximation(10, _ / 100.of[s/kg])
  
   
```

| PhysTyped Usage Examples | Finished in 19 ms | 1 example, 3 expectations, 0 failure, 0 error |