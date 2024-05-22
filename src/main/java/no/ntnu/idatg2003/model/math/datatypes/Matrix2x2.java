package no.ntnu.idatg2003.model.math.datatypes;

/**
 * This class represents a 2x2 matrix. It defines the four elements of the matrix:
 * a00, a01, a10, and a11.
 *
 * <p>The class provides methods to multiply the matrix by a vector, as well as getters
 * for accessing the individual elements of the matrix.
 *
 * @author Theodor Sjetnan Utvik, Sigurd Riseth
 * @version 1.0.0
 * @since 06.02.2024
 */
public class Matrix2x2 {

  private final double a00;
  private final double a01;
  private final double a10;
  private final double a11;

  /**
   * Constructs a 2x2 matrix with the specified values for its elements.
   *
   * @param a00 The value in the first row and first column.
   * @param a01 The value in the first row and second column.
   * @param a10 The value in the second row and first column.
   * @param a11 The value in the second row and second column.
   * @throws IllegalArgumentException If any of the input values are NaN or infinite.
   */
  public Matrix2x2(double a00, double a01, double a10, double a11) throws IllegalArgumentException {
    if (Double.isNaN(a00) || Double.isNaN(a01) || Double.isNaN(a10) || Double.isNaN(a11)) {
      throw new IllegalArgumentException("The input values cannot be NaN");
    }
    if (Double.isInfinite(a00) || Double.isInfinite(a01) || Double.isInfinite(a10)
        || Double.isInfinite(a11)) {
      throw new IllegalArgumentException("The input values cannot be infinite");
    }
    this.a00 = a00;
    this.a01 = a01;
    this.a10 = a10;
    this.a11 = a11;
  }

  /**
   * Multiplies the matrix by a vector.
   *
   * @param vector The vector to multiply with the matrix.
   * @return The product of the matrix and the vector.
   * @throws IllegalArgumentException If the input vector is null.
   * @see Vector2D
   */
  public Vector2D multiply(Vector2D vector) throws IllegalArgumentException {
    if (vector == null) {
      throw new IllegalArgumentException("The input vector cannot be null");
    }
    double x0 = this.a00 * vector.getX0() + this.a01 * vector.getX1();
    double x1 = this.a10 * vector.getX0() + this.a11 * vector.getX1();

    return new Vector2D(x0, x1);
  }

  /**
   * Get the value in the first row and first column of the matrix.
   *
   * @return the value in the first row and first column of the matrix.
   */
  public double getA00() {
    return a00;
  }

  /**
   * Get the value in the first row and second column of the matrix.
   *
   * @return the value in the first row and second column of the matrix.
   */
  public double getA01() {
    return a01;
  }

  /**
   * Get the value in the second row and first column of the matrix.
   *
   * @return the value in the second row and first column of the matrix.
   */
  public double getA10() {
    return a10;
  }

  /**
   * Get the value in the second row and second column of the matrix.
   *
   * @return the value in the second row and second column of the matrix.
   */
  public double getA11() {
    return a11;
  }
}
