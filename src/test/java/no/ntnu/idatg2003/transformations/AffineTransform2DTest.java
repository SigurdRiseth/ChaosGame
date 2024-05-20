package no.ntnu.idatg2003.transformations;

import static org.junit.jupiter.api.Assertions.*;

import no.ntnu.idatg2003.model.math.datatypes.Matrix2x2;
import no.ntnu.idatg2003.model.math.datatypes.Vector2D;
import no.ntnu.idatg2003.model.transformations.AffineTransform2D;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * This class is a test class for the AffineTransform2D class.
 *
 * @author Sigurd Riseth, Theodor Sjetnan Utvik
 * @version 0.0.1
 * @see AffineTransform2D
 * @since 12.02.2024
 */
class AffineTransform2DTest {

  /**
   * Tests if the transform() method returns the correct result for a affine transformation. The
   * values are retrieved from the first part of the exam text.
   */
  @Test
  void transformTest() {
    AffineTransform2D affineTransform2D = new AffineTransform2D(new Matrix2x2(0.5, 1, 1, 0.5),
        new Vector2D(3, 1));
    Vector2D point = new Vector2D(1, 2);
    Vector2D transformedPoint = affineTransform2D.transform(point);
    assertEquals(5.5, transformedPoint.getX0(), 0.001, "Expected x0 to be 5.5");
    assertEquals(3, transformedPoint.getX1(), 0.001, "Expected x1 to be 3");
  }

  /**
   * Tests if the transform() method returns the correct result for a affine transformation. Tests
   * the method for numerous values using the parametrized test.
   */
  @ParameterizedTest(name = "[{0}, {1}; {2}, {3}]*({4},{5}) + ({6},{7}) = ({8},{9})")
  @CsvSource({
    "1,2,3,4,3,-3,1,1,-2,-2",
    "1,2,3,4,0,1,0,-1,2,3",
    "1,2,3,4,1,0,1,0,2,3",
    "1,2,3,4,0,0,0,0,0,0",
  })
  void transformTestParameterized(double a00, double a01, double a10, double a11, double x0, double x1,
      double b0, double b1, double expectedX0, double expectedX1) {
    AffineTransform2D affineTransform2D = new AffineTransform2D(new Matrix2x2(a00, a01, a10, a11),
        new Vector2D(b0, b1));
    Vector2D point = new Vector2D(x0, x1);
    Vector2D transformedPoint = affineTransform2D.transform(point);
    assertEquals(expectedX0, transformedPoint.getX0(), 0.001, "Expected x0 to be " + expectedX0);
    assertEquals(expectedX1, transformedPoint.getX1(), 0.001, "Expected x1 to be " + expectedX1);
  }

  @Test
  void nullMatrixTest() {
    Vector2D vector = new Vector2D(1, 1);
    assertThrows(NullPointerException.class, () -> new AffineTransform2D(null, vector));
  }

  @Test
  void nullVectorTest() {
    Matrix2x2 matrix = new Matrix2x2(1, 1, 1, 1);
    assertThrows(NullPointerException.class, () -> new AffineTransform2D(matrix, null));
  }

  @Test
  void nullTransformTest() {
    AffineTransform2D affineTransform2D = new AffineTransform2D(new Matrix2x2(1, 1, 1, 1), new Vector2D(1, 1));
    assertThrows(NullPointerException.class, () -> affineTransform2D.transform(null));
  }
}