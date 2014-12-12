package feh.phtpe

import org.specs2.Specification
import PhysTyped._
import short._

class MiscExamples extends Specification{ def is = s2""" ${ "PhysTyped Usage Examples".title }
  __Speed of an object in atmosphere__                                                      
  ${
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

    val in1Second1 = speedApproximator1.engineWorking(3e10.of[N], 1.of[Second])
    val in1Second2 = speedApproximator2.engineWorking(3e10, 1)

    in1Second1.typeEqual[m/s] and in1Second2.typeEqual[m/s] and in1Second1.phEquals(in1Second2)
  }

  """
}
