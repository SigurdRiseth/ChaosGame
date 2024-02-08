import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

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
}
