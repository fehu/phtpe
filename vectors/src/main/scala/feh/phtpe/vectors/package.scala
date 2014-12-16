package feh.phtpe

import feh.phtpe.vectors.Vector.{VectorCreation, VectorImplicits}

package object vectors extends VectorCreation
  with VectorImplicits
  with VectorMeasureOpsImplicits
  with VectorMeasuresTransformsImplicits
{

}
