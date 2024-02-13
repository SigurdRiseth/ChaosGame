package no.ntnu.idatg2003.transformations;

import no.ntnu.idatg2003.math_datatypes.Complex;
import no.ntnu.idatg2003.math_datatypes.Vector2D;

/**
 * Class to represent the Julia transformation.
 *
 * <p>This class is used to represent the Julia transformation and perform operations on it. </p>
 *
 * @author Sigurd Riseth, Theodor Sjetnan Utvik
 * @version 0.0.1
 * @see Transform2D
 * @see Complex
 * @see Vector2D
 * @since 12.02.2024
 */
public class JuliaTransform implements Transform2D {

  private Complex point;
  private int sign;

  /**
   * Constructor for the Julia transformation.
   *
   * @param point the complex constant
   * @param sign  the sign of the transformation
   */
  public JuliaTransform(Complex point, int sign) {
    this.point = point;
    this.sign = (int) Math.signum(sign);
  }

  /**
   * Method to transform a point using the Julia transformation.
   * <p>
   *   <b>Formula:</b> z → ±sqrt(z - c)
   *   <br>
   *  Where z is the parameter, c is the point specified in the constructor and ± part depends on sign.
   * </p>
   *
   * @param point the point to transform
   * @return Julia transformed no.ntnu.idatg2003.math_datatypes.Vector2D
   */
  public Vector2D transform(Vector2D point) {
    Complex complexPoint = new Complex(point.getX0() - this.point.getX0(),
        point.getX1() - this.point.getX1());
    complexPoint = complexPoint.sqrt();
    return new Vector2D(sign * complexPoint.getX0(), sign * complexPoint.getX1());
  }

}
