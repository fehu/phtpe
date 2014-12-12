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
  val speedApproximator2 = new SpeedInAtmosphereApproximation(10d, _ * 1.of[kg/s]/100)
  
  val in1Second1 = speedApproximator1.engineWorking(3e10.of[N], 1.of[Second])
  val in1Second2 = speedApproximator2.engineWorking(3e10, 1d)

  in1Second1.typeEqual[m/s] and in1Second2.typeEqual[m/s] and in1Second1.phEquals(in1Second2)
```  
    
| PhysTyped Usage Examples | Finished in 9 ms | 1 example, 3 expectations (+2), 0 failure, 0 error |