package no.ntnu.idatg2003.transformations;

import no.ntnu.idatg2003.math_datatypes.Complex;
import no.ntnu.idatg2003.math_datatypes.Vector2D;

/**
 * Class to represent the Julia transformation.
 *
 * <p>This class is used to represent the Julia transformation and perform operations on it.</p>
 *
 * @author Sigurd Riseth, Theodor Sjetnan Utvik
 * @version 0.0.1
 * @see Transform2D
 * @see Complex
 * @see Vector2D
 * @since 12.02.2024
 */
public class JuliaTransform implements Transform2D {

  private final Complex complexConstant;
  private final int sign;

  /**
   * Constructor for the Julia transformation.
   *
   * <p>Field <code>sign</code> is set to either positive or negative 1 depending on input sign</p>
   *
   * @param complexConstant the complex constant
   * @param sign the sign of the transformation
   */
  public JuliaTransform(Complex complexConstant, int sign) {
    this.complexConstant = complexConstant;
    this.sign = (int) Math.signum(sign);
  }

  /**
   * Method to transform a point using the Julia transformation.
   *
   * <p><b>Formula:</b> z → ±sqrt(z - c) <br>
   * Where z is the parameter, c is the complexConstant and ± part depends on the sign field.
   *
   * @param point the point to transform
   * @return Julia transformed Vector2D
   */
  public Vector2D transform(Vector2D point) {
    Complex complexPoint =
        new Complex(
            point.getX0() - complexConstant.getX0(), point.getX1() - complexConstant.getX1());
    complexPoint = complexPoint.sqrt();
    return new Vector2D(sign * complexPoint.getX0(), sign * complexPoint.getX1());
  }

  /**
   * Method to get the transformation as a string.
   *
   * @return the transformation as a string
   */
  @Override
  public String toString() {
    return String.format("%s, %s", complexConstant.getX0(), complexConstant.getX1());
  }
}
