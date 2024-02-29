package no.ntnu.idatg2003.transformations;

import no.ntnu.idatg2003.math_datatypes.Vector2D;

/**
 * Interface for 2D transformations.
 *
 * Used in AffineTransform2D and JuliaTransform.
 */
public interface Transform2D {

  /**
   * Method to transform a point using the transformation.
   *
   * @param point the point to transform
   * @return transformed Vector2D
   */
  Vector2D transform(Vector2D point);

  /**
   * Method to get the transformation as a string.
   *
   * @return the transformation as a string
   */
  String toString();


}
