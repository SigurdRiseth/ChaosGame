package no.ntnu.idatg2003.transformations;

import no.ntnu.idatg2003.math_datatypes.Matrix2x2;
import no.ntnu.idatg2003.math_datatypes.Vector2D;

/**
 * Class to represent the Affine transformation x → Ax+b
 */
public class AffineTransform2D implements Transform2D {
  private final Matrix2x2 matrix;
  private final Vector2D vector;

  /**
   * Constructor for the Affine transformation.
   *
   * @param matrix the matrix A
   * @param vector the vector b
   */
  public AffineTransform2D(Matrix2x2 matrix, Vector2D vector) {
    this.matrix = matrix;
    this.vector = vector;
  }

  /**
   * Method to transform a vector using the Affine transformation.
   *
   * @param point the vector to transform, known as x
   * @return Affine transformed Vector2D
   */
  public Vector2D transform(Vector2D point) {
    return matrix.multiply(point).add(vector);
  }
}
