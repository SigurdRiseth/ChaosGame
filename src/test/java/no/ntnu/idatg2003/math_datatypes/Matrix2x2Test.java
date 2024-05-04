package no.ntnu.idatg2003.math_datatypes;

import static org.junit.jupiter.api.Assertions.*;

import no.ntnu.idatg2003.model.math_datatypes.Matrix2x2;
import no.ntnu.idatg2003.model.math_datatypes.Vector2D;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * This class is a test class for the Matrix2x2 class.
 */
class Matrix2x2Test {

  /**
   * Tests if the multiply() method returns the correct result.
   * Tests for multiple inputs using the parametrized test.
   */
  @ParameterizedTest(name = "Matrix2x2({0}, {1}, {2}, {3}) * Vector2D({4}, {5}) = Vector2D({6}, {7})")
  @CsvSource({
      "1, 2, 3, 4, 1, 0, 1, 3",
      "1, 2, 3, 4, -1, 2, 3, 5",
      "1, 2, 3, 4, 0, 0, 0, 0"
  })
  void multiplyParameterizedTest(double a00, double a01, double a10, double a11,
      double x0, double x1, double expectedX0, double expectedX1) {

    Matrix2x2 matrix = new Matrix2x2(a00, a01, a10, a11);
    Vector2D vector = new Vector2D(x0, x1);
    Vector2D expected = new Vector2D(expectedX0, expectedX1);
    Vector2D result = matrix.multiply(vector);
    assertEquals(expected.getX0(), result.getX0(), "Expected x0 to be " + expected.getX0() + ", but was " + result.getX0());
    assertEquals(expected.getX1(), result.getX1(), "Expected x1 to be " + expected.getX1() + ", but was " + result.getX1());
  }

}