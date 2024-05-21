package no.ntnu.idatg2003.model.transformations;

import java.util.Objects;
import no.ntnu.idatg2003.model.math.datatypes.Complex;
import no.ntnu.idatg2003.model.math.datatypes.Vector2D;
import no.ntnu.idatg2003.utility.enums.TransformType;

/**
 * Represents the Julia transformation.
 *
 * <p>The Julia transformation is applied to points in the complex plane, transforming them according to the formula: z → ±sqrt(z - c),
 * where z is the parameter, c is the complex constant, and ± depends on the sign field.</p>
 *
 * @author Sigurd Riseth, Theodor Sjetnan Utvik
 * @version 1.0.0
 * @since 12.02.2024
 * @see Transform2D
 * @see Complex
 * @see Vector2D
 */
public class JuliaTransform implements Transform2D {

  private final Complex complexConstant;
  private final int sign;

  /**
   * Constructs a Julia transformation.
   *
   * @param complexConstant The complex constant.
   * @param sign            The sign of the transformation.
   * @throws NullPointerException if the complex constant is null
   */
  public JuliaTransform(Complex complexConstant, int sign) {
    Objects.requireNonNull(complexConstant, "The complex constant cannot be null");
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
   * @throws NullPointerException if the input vector is null
   */
  @Override
  public Vector2D transform(Vector2D point) {
    Objects.requireNonNull(point, "The input vector cannot be null");
    Complex complexPoint =
        new Complex(
            point.getX0() - complexConstant.getX0(), point.getX1() - complexConstant.getX1());
    complexPoint = complexPoint.sqrt();
    return new Vector2D(sign * complexPoint.getX0(), sign * complexPoint.getX1());
  }

  /**
   * Method to get the complex constant.
   *
   * @return the complex constant
   */
  public Complex getComplexConstant() {
    return complexConstant;
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

  /**
   * Method to get the type of the transformation.
   *
   * @return the type of the transformation
   */
  @Override
  public TransformType getType() {
    return TransformType.JULIA;
  }
}
