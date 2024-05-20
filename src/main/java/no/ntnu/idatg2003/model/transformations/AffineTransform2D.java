package no.ntnu.idatg2003.model.transformations;

import java.util.Objects;
import no.ntnu.idatg2003.model.math.datatypes.Matrix2x2;
import no.ntnu.idatg2003.model.math.datatypes.Vector2D;

/**
 * Class to represent the Affine transformation x → Ax+b
 *
 * @since 12.02.2024
 * @version 0.0.1
 * @see Transform2D
 * @see Matrix2x2
 * @see Vector2D
 * @author Sigurd Riseth, Theodor Sjetnan Utvik
 */
public class AffineTransform2D implements Transform2D {
  private final Matrix2x2 matrix;
  private final Vector2D vector;

  /**
   * Constructor for the Affine transformation.
   *
   * @param matrix the matrix A
   * @param vector the vector b
   * @throws NullPointerException if the matrix or vector is null
   */
  public AffineTransform2D(Matrix2x2 matrix, Vector2D vector) throws NullPointerException {
    Objects.requireNonNull(matrix, "The matrix cannot be null");
    Objects.requireNonNull(vector, "The vector cannot be null");
    this.matrix = matrix;
    this.vector = vector;
  }

  /**
   * Method to transform a vector using the Affine transformation.
   *
   * @param point the vector to transform, known as x
   * @return Affine transformed Vector2D
   * @throws NullPointerException if the input vector is null
   */
  @Override
  public Vector2D transform(Vector2D point) throws NullPointerException {
    Objects.requireNonNull(point, "The input vector cannot be null");
    return matrix.multiply(point).add(vector);
  }

  /**
   * Method to get the matrix of the transformation.
   *
   * @return the matrix of the transformation
   */
  public Matrix2x2 getMatrix() {
    return matrix;
  }

  /**
   * Method to get the vector of the transformation.
   *
   * @return the vector of the transformation
   */
  public Vector2D getVector() {
    return vector;
  }


  /**
   * Method to get the transformation as a string.
   *
   * @return the transformation as a string
   */
  @Override
  public String toString() {
    return String.format(
        "%s, %s, %s, %s, %s, %s",
        matrix.getA00(),
        matrix.getA01(),
        matrix.getA10(),
        matrix.getA11(),
        vector.getX0(),
        vector.getX1());
  }
}
