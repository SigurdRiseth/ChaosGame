package no.ntnu.idatg2003.model.transformations;

import no.ntnu.idatg2003.model.math.datatypes.Vector2D;
import no.ntnu.idatg2003.utility.enums.TransformType;

/**
 * This interface represents a 2D transformation. It defines methods to transform a point
 * and retrieve the transformation type.
 *
 * <p>The interface is implemented by AffineTransform2D and JuliaTransform classes.</p>
 *
 * @author Sigurd Riseth, Theodor Sjetnan Utvik
 * @version 1.0.0
 * @since 12.02.2024
 */
public interface Transform2D {

  /**
   * Transforms a 2D point using the transformation.
   *
   * @param point The point to transform.
   * @return The transformed Vector2D.
   */
  Vector2D transform(Vector2D point);

  /**
   * Returns the transformation as a string.
   *
   * <p>This method is used to write the transformation to a CSV file.</p>
   *
   * @return The transformation as a string.
   */
  String toString();

  /**
   * Gets the type of the transformation.
   *
   * @return The type of the transformation.
   */
  TransformType getType();
}
