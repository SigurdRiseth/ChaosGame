package no.ntnu.idatg2003.transformations;

import static org.junit.jupiter.api.Assertions.*;

import no.ntnu.idatg2003.math_datatypes.Complex;
import no.ntnu.idatg2003.math_datatypes.Vector2D;
import no.ntnu.idatg2003.transformations.JuliaTransform;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * This class is a test class for the JuliaTransform class.
 *
 * @author Sigurd Riseth, Theodor Sjetnan Utvik
 * @version 0.0.1
 * @see JuliaTransform
 * @see Complex
 * @see Vector2D
 * @since 12.02.2024
 */
class JuliaTransformTest {

  /**
   * Tests if the transform() method returns the correct result for a Julia transformation. The
   * values are retrieved from the first part of the exam text.
   */
  @Test
  @DisplayName("sqrt((0.4+0.2i)-(0.3+0.6i)) = 0.506-0.395i")
  void transformTest() {
    JuliaTransform juliaTransform = new JuliaTransform(new Complex(0.3, 0.6),
        1);
    Vector2D point = new Vector2D(0.4, 0.2);
    Vector2D transformedPoint = juliaTransform.transform(point);
    assertEquals(0.506, transformedPoint.getX0(), 0.001,
        "Expected x0 to be 0.506");
    assertEquals(-0.395, transformedPoint.getX1(), 0.001,
        "Expected x1 to be -0.395");
  }

  /**
   * Tests if the transform() method returns the correct result for a Julia transformation. Tests
   * the method for numerous values using the parametrized test.
   */
  @ParameterizedTest(name = "{6} * sqrt(({0} + {1}i)-({2} + {3}i)) = {4} + {5}i")
  @CsvSource({
      "0.4, 0.2, 0.3, 0.6, 0.506, -0.395, 1",
      "0.4, 0.2, 0.3, 0.6, -0.506, 0.395, -1",
      "0.4, 0.2, 0.1, 0.4, 0.575, -0.174, 1",
      "0.4, 0.2, 0.1, 0.4, -0.575, 0.174, -1",
      "-0.1, 0.4, 0.1, -0.4, 0.559, 0.716, 1",
      "-0.1, 0.4, 0.1, -0.4, -0.559, -0.716, -1"
  })
  void transformTestParameterized(double x0, double x1, double c0, double c1, double expectedX0,
      double expectedX1, int sign) {
    JuliaTransform juliaTransform = new JuliaTransform(new Complex(c0, c1), sign);
    Vector2D point = new Vector2D(x0, x1);
    Vector2D transformedPoint = juliaTransform.transform(point);
    assertEquals(expectedX0, transformedPoint.getX0(), 0.001,
        "Expected x0 to be " + expectedX0);
    assertEquals(expectedX1, transformedPoint.getX1(), 0.001,
        "Expected x1 to be " + expectedX1);
  }
}