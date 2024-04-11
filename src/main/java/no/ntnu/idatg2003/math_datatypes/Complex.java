package no.ntnu.idatg2003.math_datatypes;

/**
 * This class is a subclass of Vector2D and takes in the parameters: realPart and imaginaryPart. The
 * class has method which returns the square root of a complex number, using methods from Vector2D.
 *
 * @see Vector2D
 * @since 08.02.2024
 * @author Theodor Sjetnan Utvik, Sigurd Riseth
 * @version 0.0.1
 */
public class Complex extends Vector2D {

  /**
   * The constructor takes two parameters: realPart and imaginaryPart which will make an imaginary
   * number as a vector form.
   *
   * @param realPart the real part of the imaginary number.
   * @param imaginaryPart the imaginary part of the imaginary number.
   */
  public Complex(double realPart, double imaginaryPart) {
    super(realPart, imaginaryPart);
  }

  /**
   * This method return the square root of the complex number.
   *
   * @return square root of the complex number.
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
   * This method multiplies two complex numbers together.
   *
   * @param other the other complex number to multiply with.
   * @return the product of the two complex numbers.
   */
  public Complex multiply(Complex other) {  //TODO: Implementere denne for å utføre kvadratiske julia-sett ikke bare kvadratrot-sett
    double realPart = getX0() * other.getX0() - getX1() * other.getX1();
    double imaginaryPart = getX0() * other.getX1() + getX1() * other.getX0();
    return new Complex(realPart, imaginaryPart);
  }
}
