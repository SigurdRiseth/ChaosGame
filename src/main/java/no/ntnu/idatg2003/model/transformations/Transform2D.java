package no.ntnu.idatg2003.model.transformations;

import no.ntnu.idatg2003.model.math.datatypes.Vector2D;
import no.ntnu.idatg2003.utility.enums.TransformType;

/**
 * Interface for 2D transformations.
 *
 * <p>Used in AffineTransform2D and JuliaTransform.</p>
 *
 * @see AffineTransform2D
 * @see JuliaTransform
 * @author Sigurd Riseth, Theodor Sjetnan Utvik
 * @version 0.0.1
 * @since 12.02.2024
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
   * <p>Used to write the transform down to a csv-file.</p>
   *
   * @return the transformation as a string
   */
  String toString();

  /**
   * Method to get the type of the transformation.
   *
   * @return the type of the transformation
   */
  TransformType getType();
}
