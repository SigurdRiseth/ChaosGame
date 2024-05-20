package no.ntnu.idatg2003.model.math.datatypes;

/**
 * A class to represent a 2D vector.
 *
 * <p>his class is used to represent a 2D vector and perform operations on it. The operations are
 * listed below:
 *
 * <ul>
 *   <li>add
 *   <li>subtract
 * </ul>
 *
 * @version 0.0.1
 * @since 06.02.2024
 * @author Sigurd Riseth
 */
public class Vector2D {
  private final double x0;
  private final double x1;

  /**
   * Constructor for the Vector2D class.
   *
   * @param x0 the x0 value of the vector
   * @param x1 the x1 value of the vector
   * @throws IllegalArgumentException if the input values are NaN or infinite
   */
  public Vector2D(double x0, double x1) throws IllegalArgumentException {
    if (Double.isNaN(x0) || Double.isNaN(x1)) {
      throw new IllegalArgumentException("The input values cannot be NaN");
    }
    if (Double.isInfinite(x0) || Double.isInfinite(x1)) {
      throw new IllegalArgumentException("The input values cannot be infinite");
    }
    this.x0 = x0;
    this.x1 = x1;
  }

  /**
   * Get the x0 value of the vector.
   *
   * @return the x0 value of the vector
   */
  public double getX0() {
    return this.x0;
  }

  /**
   * Get the x1 value of the vector.
   *
   * @return the x1 value of the vector
   */
  public double getX1() {
    return this.x1;
  }

  @Override
  public String toString() {
    return (x0 + ", " + x1);
  }

  /**
   * Add the input vector to the existing vector.
   *
   * @param other the vector to add to the existing vector
   * @return the original vector added to the input vector
   * @throws IllegalArgumentException if the input vector is null
   */
  public Vector2D add(Vector2D other) throws IllegalArgumentException {
    if (other == null) {
      throw new IllegalArgumentException("The input vector cannot be null");
    }
    return new Vector2D((other.getX0() + x0), (other.getX1()) + x1);
  }

  /**
   * Subtract the input vector from the existing vector.
   *
   * @param other the vector to subtract the existing vector with
   * @return the original vector subtracted the input vector
   * @throws IllegalArgumentException if the input vector is null
   */
  public Vector2D subtract(Vector2D other) throws IllegalArgumentException {
    if (other == null) {
      throw new IllegalArgumentException("The input vector cannot be null");
    }
    return new Vector2D((x0 - other.getX0()), (x1 - other.getX1()));
  }

  public double getLength() {
    return Math.sqrt(x0 * x0 + x1 * x1);
  }

}
