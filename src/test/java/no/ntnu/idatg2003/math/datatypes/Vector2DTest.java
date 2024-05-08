package no.ntnu.idatg2003.math.datatypes;

import static org.junit.jupiter.api.Assertions.*;

import no.ntnu.idatg2003.model.math.datatypes.Vector2D;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Test class for the Vector2D class.
 * <p>
 *   Tests the add and subtract methods of the Vector2D class.
 * </p>
 */
class Vector2DTest {

  /**
   * Test for the add method in the Vector2D class.
   * <p>
   *   Adds the v1 vector to the v2 vector and checks if the result is correct.
   * </p>
   */
  @Test
  void add() {
    Vector2D v1 = new Vector2D(1, 2);
    Vector2D v2 = new Vector2D(3, 4);
    Vector2D v3 = v1.add(v2);
    assertEquals(4, v3.getX0(), "The x0 value of the vector should be 4");
    assertEquals(6, v3.getX1(), "The x1 value of the vector should be 6");
  }

  /**
   * Test for the add method in the Vector2D class.
   *
   * @param x0 x0 value of the first vector
   * @param x1 x1 value of the first vector
   * @param y0 x0 value of the second vector
   * @param y1 x1 value of the second vector
   * @param expectedX0 expected x0 value of the resulting vector
   * @param expectedX1 expected x1 value of the resulting vector
   */
  @ParameterizedTest(name = "Vector2D({0}, {1}) + Vector2D({2}, {3}) = Vector2D({4}, {5})")
  @CsvSource({
      "1, 2, 3, 4, 4, 6",
      "1, 2, -3, 4, -2, 6",
      "1, 2, 0, 0, 1, 2"
  })
  void addParameterizedTest(double x0, double x1, double y0, double y1, double expectedX0, double expectedX1) {
    Vector2D v1 = new Vector2D(x0, x1);
    Vector2D v2 = new Vector2D(y0, y1);
    Vector2D expected = new Vector2D(expectedX0, expectedX1);
    Vector2D result = v1.add(v2);
    assertEquals(expected.getX0(), result.getX0(), "Expected x0 to be " + expected.getX0() + ", but was " + result.getX0());
    assertEquals(expected.getX1(), result.getX1(), "Expected x1 to be " + expected.getX1() + ", but was " + result.getX1());
  }

  /**
   * Test for the subtract method in the Vector2D class.
   * <p>
   *   Subtracts the v2 vector from the v1 vector and checks if the result is correct.
   * </p>
   */
  @Test
  void subtract() {
    Vector2D v1 = new Vector2D(1, 2);
    Vector2D v2 = new Vector2D(3, 4);
    Vector2D v3 = v1.subtract(v2);
    assertEquals(-2, v3.getX0(), "The x0 value of the vector should be -2");
    assertEquals(-2, v3.getX1(), "The x1 value of the vector should be -2");
  }

  /**
   * Test for the subtract method in the Vector2D class.
   *
   * @param x0 x0 value of the first vector
   * @param x1 x1 value of the first vector
   * @param y0 x0 value of the second vector
   * @param y1 x1 value of the second vector
   * @param expectedX0 expected x0 value of the resulting vector
   * @param expectedX1 expected x1 value of the resulting vector
   */
  @ParameterizedTest(name = "Vector2D({0}, {1}) - Vector2D({2}, {3}) = Vector2D({4}, {5})")
  @CsvSource({
      "1, 2, 3, 4, -2, -2",
      "1, 2, -3, 4, 4, -2",
      "1, 2, 0, 0, 1, 2"
  })
  void subtractParameterizedTest(double x0, double x1, double y0, double y1, double expectedX0, double expectedX1) {
    Vector2D v1 = new Vector2D(x0, x1);
    Vector2D v2 = new Vector2D(y0, y1);
    Vector2D expected = new Vector2D(expectedX0, expectedX1);
    Vector2D result = v1.subtract(v2);
    assertEquals(expected.getX0(), result.getX0(), "Expected x0 to be " + expected.getX0() + ", but was " + result.getX0());
    assertEquals(expected.getX1(), result.getX1(), "Expected x1 to be " + expected.getX1() + ", but was " + result.getX1());
  }

}