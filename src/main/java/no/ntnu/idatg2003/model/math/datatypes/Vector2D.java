package no.ntnu.idatg2003.model.math.datatypes;

/**
 * This class represents a 2D vector. It encapsulates two double values: x0 and x1.
 *
 * <p>The class provides methods to perform basic vector operations such as addition and subtraction.
 *
 * @author Sigurd Riseth
 * @version 1.0.0
 * @since 06.02.2024
 */
public class Vector2D {

  private final double x0;
  private final double x1;

  /**
   * Constructs a 2D vector with the specified x0 and x1 values.
   *
   * @param x0 The value along the x-axis.
   * @param x1 The value along the y-axis.
   * @throws IllegalArgumentException If any of the input values are NaN or infinite.
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
   * Adds another vector to this vector.
   *
   * @param other The vector to add.
   * @return The sum of this vector and the other vector.
   * @throws IllegalArgumentException If the input vector is null.
   */
  public Vector2D add(Vector2D other) throws IllegalArgumentException {
    if (other == null) {
      throw new IllegalArgumentException("The input vector cannot be null");
    }
    return new Vector2D((other.getX0() + x0), (other.getX1()) + x1);
  }

  /**
   * Subtracts another vector from this vector.
   *
   * @param other The vector to subtract.
   * @return The difference between this vector and the other vector.
   * @throws IllegalArgumentException If the input vector is null.
   */
  public Vector2D subtract(Vector2D other) throws IllegalArgumentException {
    if (other == null) {
      throw new IllegalArgumentException("The input vector cannot be null");
    }
    return new Vector2D((x0 - other.getX0()), (x1 - other.getX1()));
  }

  /**
   * Gets the length of the vector.
   *
   * @return The length of the vector.
   */
  public double getLength() {
    return Math.sqrt(x0 * x0 + x1 * x1);
  }

}
