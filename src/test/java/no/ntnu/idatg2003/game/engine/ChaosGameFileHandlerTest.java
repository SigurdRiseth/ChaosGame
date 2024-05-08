package no.ntnu.idatg2003.game.engine;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import no.ntnu.idatg2003.model.game.engine.ChaosGameDescription;
import no.ntnu.idatg2003.model.game.engine.ChaosGameFileHandler;
import no.ntnu.idatg2003.model.transformations.Transform2D;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Test class for the {@link ChaosGameFileHandler} class.
 */
class ChaosGameFileHandlerTest {

  /**
   * Tests if the <code>readFromFile()</code> method returns the correct {@link ChaosGameDescription} for the preset Julia-set
   */
  @Test
  void readFromFileJuliaTest() {
    ChaosGameDescription description = ChaosGameFileHandler.readFromFile("src/test/resources/csv/preset.games/Julia.csv");
    assertEquals(-1.6, description.getMinCoords().getX0(), "The min x0 value is not correct");
    assertEquals(-1, description.getMinCoords().getX1(), "The min x1 value is not correct");
    assertEquals(1.6, description.getMaxCoords().getX0(), "The max x0 value is not correct");
    assertEquals(1, description.getMaxCoords().getX1(), "The max x1 value is not correct");
    assertEquals("-0.74543, 0.11301", description.getTransforms().getFirst().toString(), "The transform is not correct");
  }

  /**
   * Tests if the <code>readFromFile()</code> method returns the correct {@link ChaosGameDescription} for the preset Barnsley-Fern
   *
   * @param transformIndex the index of the transform
   * @param a00 the value of a00
   * @param a01 the value of a01
   * @param a10 the value of a10
   * @param a11 the value of a11
   * @param b0 the value of b0
   * @param b1 the value of b1
   */
  @ParameterizedTest(name = "Transform {0} has values a00={1}, a01={2}, a10={3}, a11={4}, b0={5}, b1={6}")
  @CsvSource ({
      "0, 0.0, 0.0, 0.0, 0.16, 0.0,0.0",
      "1, 0.85, 0.04, -0.04, 0.85, 0.0,1.6",
      "2, .2, -.26, .23, .22, 0, 1.6",
      "3, -.15, .28, .26, .24, 0, .44"
  })
  void readFromFileAffineTest(int transformIndex, double a00, double a01, double a10, double a11, double b0, double b1) {
    ChaosGameDescription description = ChaosGameFileHandler.readFromFile("src/test/resources/csv/preset.games/barnsley-fern.csv");
    String expected = a00 + ", " + a01 + ", " + a10 + ", " + a11 + ", " + b0 + "," + b1;
    List<Transform2D> transforms = description.getTransforms();

    assertEquals(4, transforms.size(), "The number of transforms is not correct");
    assertEquals(-2.65, description.getMinCoords().getX0(), "The min x0 value is not correct");
    assertEquals(0, description.getMinCoords().getX1(), "The min x1 value is not correct");
    assertEquals(2.65, description.getMaxCoords().getX0(), "The max x0 value is not correct");
    assertEquals(10, description.getMaxCoords().getX1(), "The max x1 value is not correct");
    assertEquals(expected, description.getTransforms().get(transformIndex).toString(), "The transforms values are not correct");
  }

  /**
   * Tests if the <code>writeToFile()</code> method writes the correct {@link ChaosGameDescription} to a file.
   *
   * <p>
   *   Note that this test is dependent on that the <code>readFromFile()</code> method works correctly.
   * </p>
   */
  @Test
  void writeToFileTest() {
    ChaosGameDescription expectedDescription = ChaosGameFileHandler.readFromFile("src/test/resources/csv/preset.games/Julia.csv");

    ChaosGameFileHandler.writeToFile(expectedDescription, "src/test/resources/csv/preset.games/JuliaTest.csv");
    ChaosGameDescription resultDescription = ChaosGameFileHandler.readFromFile("src/test/resources/csv/preset.games/JuliaTest.csv");

    assertEquals(expectedDescription.getMinCoords().getX0(), resultDescription.getMinCoords().getX0(), "The min x0 value is not correct");
    assertEquals(expectedDescription.getMinCoords().getX1(), resultDescription.getMinCoords().getX1(), "The min x1 value is not correct");
    assertEquals(expectedDescription.getMaxCoords().getX0(), resultDescription.getMaxCoords().getX0(), "The max x0 value is not correct");
    assertEquals(expectedDescription.getMaxCoords().getX1(), resultDescription.getMaxCoords().getX1(), "The max x1 value is not correct");
    assertEquals(expectedDescription.getTransforms().getFirst().toString(), resultDescription.getTransforms().getFirst().toString(), "The transform is not correct");
  }
}