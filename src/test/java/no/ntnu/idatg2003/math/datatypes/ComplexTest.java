package no.ntnu.idatg2003.math.datatypes;

import static org.junit.jupiter.api.Assertions.*;

import no.ntnu.idatg2003.model.math.datatypes.Complex;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * This class is a test class for the Complex class.
 *
 * @see Complex
 * @author Theodor Sjetnan Utvik, Sigurd Riseth
 * @since 08.02.2024
 * @version 0.0.1
 *
 */
class ComplexTest {


  /**
   * Tests if the sqrt() method returns the correct result. The values are retrieved from the
   * first part of the exam text.
   */
  @Test
  void sqrtPositiveTest() {
    Complex complex = new Complex(0.1, -0.4);
    Complex resultComplex = complex.sqrt();
    assertEquals(0.506, resultComplex.getX0(), 0.001,
        "Expected real part to be 0.506");
    assertEquals(
        -0.395, resultComplex.getX1(),
        0.001, "Expected imaginary part to be -0.395");
  }

  /**
   * Tests if the sqrt() method returns the correct result.
   *
   * @param x0 real part of the complex number
   * @param x1 imaginary part of the complex number
   * @param expectedX0 expected real part of the resulting complex number
   * @param expectedX1 expected imaginary part of the resulting complex number
   */
  @ParameterizedTest(name = "sqrt({0}^2, {1}^2) = ({2}, {3})")
  @CsvSource({
      "0.1, -0.4, 0.506, -0.395",
      "0.1, 0.4, 0.506, 0.395",
      "0.1, 0, 0.316, 0"
  })
  void sqrtParameterizedTest(double x0, double x1, double expectedX0, double expectedX1) {
    Complex complex = new Complex(x0, x1);
    Complex expected = new Complex(expectedX0, expectedX1);
    Complex result = complex.sqrt();
    assertEquals(expected.getX0(), result.getX0(), 0.001,
        "Expected real part to be " + expected.getX0());
    assertEquals(expected.getX1(), result.getX1(), 0.001,
        "Expected imaginary part to be " + expected.getX1());
  }

  @ParameterizedTest(name = "({0}, {1}i)({2}, {3}i) = ({4}, {5}i)")
  @CsvSource({
      "1, 2, 3, 4, -5, 10",
      "1, 2, -3, 4, -11, -2",
      "1, 2, 0, 0, 0, 0"
  })
  void multiplyTest(double x0, double x1, double y0, double y1, double expectedX0, double expectedX1) {
    Complex complex = new Complex(x0, x1);
    Complex other = new Complex(y0, y1);
    Complex expected = new Complex(expectedX0, expectedX1);
    Complex result = complex.multiply(other);
    assertEquals(expected.getX0(), result.getX0(), 0.001,
        "Expected real part to be " + expected.getX0());
    assertEquals(expected.getX1(), result.getX1(), 0.001,
        "Expected imaginary part to be " + expected.getX1());
  }

  @ParameterizedTest(name = "({0}, {1}i) + ({2}, {3}i) = ({4}, {5}i)")
  @CsvSource({
      "1, 2, 3, 4, 4, 6",
      "1, 2, -3, 4, -2, 6",
      "1, 2, 0, 0, 1, 2"
  })
  void addTest(double x0, double x1, double y0, double y1, double expectedX0, double expectedX1) {
    Complex complex = new Complex(x0, x1);
    Complex other = new Complex(y0, y1);
    Complex expected = new Complex(expectedX0, expectedX1);
    Complex result = complex.add(other);
    assertEquals(expected.getX0(), result.getX0(), 0.001,
        "Expected real part to be " + expected.getX0());
    assertEquals(expected.getX1(), result.getX1(), 0.001,
        "Expected imaginary part to be " + expected.getX1());
  }
}
