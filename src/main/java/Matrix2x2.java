/**
 * This class forms a 2x2 matrix, and does so using 4 fields that define the places where numbers
 * stand in a matrix.
 *
 * @author Theodor
 * @since 06.02.2024
 * @version 0.0.1
 */


public class Matrix2x2 {
  private double a00;
  private double a01;
  private double a10;
  private double a11;


  /**
   * Constructor for the 2x2 matrix and its values a00, a01, a10 and a11.
   *
   * @param a00
   * @param a01
   * @param a10
   * @param a11
   */
  public Matrix2x2 (double a00, double a01, double a10, double a11){
    this.a00 = a00;
    this.a01 = a01;
    this.a10 = a10;
    this.a11 = a11;
  }

  /**
   * The method multiplies a matrix and a vector from the Vector2D class.
   *
   * @param vector
   * @see Vector2D
   * @return the product of the matrix and the vector.
   */
  public Vector2D multiply(Vector2D vector) {
      double x0 = this.a00 * vector.getX0() + this.a01 * vector.getX1();
      double x1 = this.a10 * vector.getX0() + this.a11 * vector.getX1();

      return new Vector2D(x0, x1);
  }

}
