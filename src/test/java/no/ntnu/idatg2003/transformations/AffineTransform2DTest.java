package no.ntnu.idatg2003.transformations;

import static org.junit.jupiter.api.Assertions.*;

import no.ntnu.idatg2003.model.math.datatypes.Matrix2x2;
import no.ntnu.idatg2003.model.math.datatypes.Vector2D;
import no.ntnu.idatg2003.model.transformations.AffineTransform2D;
import org.junit.jupiter.api.Test;

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
}