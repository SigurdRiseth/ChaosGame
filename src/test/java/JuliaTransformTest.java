import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

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
   * Tests if the transform() method returns the correct result for a Julia transformation. The values are retrieved from the
   * first part of the exam text.
   */
  @Test
  void transformTest() {
    JuliaTransform juliaTransform = new JuliaTransform(new Complex(0.3, 0.6), 1);
    Vector2D point = new Vector2D(0.4, 0.2);
    Vector2D transformedPoint = juliaTransform.transform(point);
    assertEquals(0.506, transformedPoint.getX0(), 0.001);
    assertEquals(-0.395, transformedPoint.getX1(), 0.001);
  }
}