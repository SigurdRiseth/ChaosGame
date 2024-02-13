package no.ntnu.idatg2003.transformations;

import no.ntnu.idatg2003.math_datatypes.Vector2D;

/**
 * Interface for 2D transformations.
 *
 * Used in no.ntnu.idatg2003.transformations.AffineTransform2D and no.ntnu.idatg2003.transformations.JuliaTransform.
 */
public interface Transform2D {

  /**
   * Method to transform a point using the transformation.
   *
   * @param point the point to transform
   * @return transformed no.ntnu.idatg2003.math_datatypes.Vector2D
   */
  Vector2D transform(Vector2D point);
}
