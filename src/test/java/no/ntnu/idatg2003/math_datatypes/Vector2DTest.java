package no.ntnu.idatg2003.math_datatypes;

import static org.junit.jupiter.api.Assertions.*;

import no.ntnu.idatg2003.math_datatypes.Vector2D;
import org.junit.jupiter.api.Test;

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
}