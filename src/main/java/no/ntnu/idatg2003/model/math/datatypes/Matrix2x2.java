package no.ntnu.idatg2003.model.math.datatypes;

/**
 * This class forms a 2x2 matrix, and does so using 4 fields that define the places where numbers
 * stand in a matrix.
 *
 * @author Theodor Sjetnan Utvik, Sigurd Riseth
 * @since 06.02.2024
 * @version 0.0.1
 */
public class Matrix2x2 {
  private final double a00;
  private final double a01;
  private final double a10;
  private final double a11;

  /**
   * Constructor for the 2x2 matrix and its values a00, a01, a10 and a11.
   *
   * @param a00 the value in the first row and first column of the matrix.
   * @param a01 the value in the first row and second column of the matrix.
   * @param a10 the value in the second row and first column of the matrix.
   * @param a11 the value in the second row and second column of the matrix.
   * @throws IllegalArgumentException if the input values are NaN or infinite
   */
  public Matrix2x2(double a00, double a01, double a10, double a11) throws IllegalArgumentException {
    if (Double.isNaN(a00) || Double.isNaN(a01) || Double.isNaN(a10) || Double.isNaN(a11)) {
      throw new IllegalArgumentException("The input values cannot be NaN");
    }
    if (Double.isInfinite(a00) || Double.isInfinite(a01) || Double.isInfinite(a10) || Double.isInfinite(a11)) {
      throw new IllegalArgumentException("The input values cannot be infinite");
    }
    this.a00 = a00;
    this.a01 = a01;
    this.a10 = a10;
    this.a11 = a11;
  }

  /**
   * The method multiplies a matrix and a vector from the Vector2D class.
   *
   * @param vector the vector to be multiplied with the matrix.
   * @see Vector2D
   * @return the product of the matrix and the vector.
   * @throws IllegalArgumentException if the input vector is null
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
