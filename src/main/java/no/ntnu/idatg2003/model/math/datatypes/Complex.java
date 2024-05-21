package no.ntnu.idatg2003.model.math.datatypes;

/**
 * This class represents complex numbers and extends the Vector2D class. It contains methods for performing
 * operations specific to complex numbers, such as calculating the square root, multiplying two complex
 * numbers, and adding two complex numbers.
 *
 * <p>A complex number is represented as a vector in the complex plane, where the x-coordinate corresponds
 * to the real part and the y-coordinate corresponds to the imaginary part.
 *
 * @author Theodor Sjetnan Utvik, Sigurd Riseth
 * @version 1.0.0
 * @see Vector2D
 * @since 08.02.2024
 */
public class Complex extends Vector2D {

  /**
   * Constructs a Complex object with the specified real and imaginary parts.
   *
   * @param realPart      The real part of the complex number.
   * @param imaginaryPart The imaginary part of the complex number.
   */
  public Complex(double realPart, double imaginaryPart) {
    super(realPart, imaginaryPart);
  }

  /**
   * Computes the square root of the complex number.
   *
   * @return The square root of the complex number.
   */
  public Complex sqrt() {
    double magnitude =
        Math.sqrt(Math.pow(getX0(), 2) + Math.pow(getX1(), 2)); // Length of the vector
    double realPart = Math.sqrt((magnitude + getX0()) / 2); // Real part of result of square root
    double imaginaryPart =
        Math.signum(getX1())
            * Math.sqrt((magnitude - getX0()) / 2); // Imaginary part of result of square root

    return new Complex(realPart, imaginaryPart);
  }

  /**
   * Multiplies this complex number with another complex number.
   *
   * @param other The other complex number to multiply with.
   * @return The product of the two complex numbers.
   * @throws IllegalArgumentException If the input vector is null.
   */
  public Complex multiply(Vector2D other) throws IllegalArgumentException {
    if (other == null) {
      throw new IllegalArgumentException("The input vector cannot be null");
    }
    return new Complex(this.getX0() * other.getX0() - this.getX1() * other.getX1(),
        this.getX0() * other.getX1() + this.getX1() * other.getX0());
  }

  /**
   * Adds this complex number to another complex number.
   *
   * @param other The other complex number to add with.
   * @return The sum of the two complex numbers.
   * @throws IllegalArgumentException If the input vector is null.
   */
  @Override
  public Complex add(Vector2D other) throws IllegalArgumentException {
    if (other == null) {
      throw new IllegalArgumentException("The input vector cannot be null");
    }
    return new Complex(this.getX0() + other.getX0(), this.getX1() + other.getX1());
  }
}
