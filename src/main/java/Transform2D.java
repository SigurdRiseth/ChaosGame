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
}
