package no.ntnu.idatg2003.game_engine;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import no.ntnu.idatg2003.transformations.Transform2D;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * This class is a test class for the ChaosGameFileHandler class.
 */
class ChaosGameFileHandlerTest {

  /**
   * Tests if the readFromFile() method returns the correct ChaosGameDescription for the Julia set
   */
  @Test
  public void readFromFileJuliaTest() {
    ChaosGameDescription description = ChaosGameFileHandler.readFromFile("src/test/resources/Julia.csv");
    assertEquals(description.getMinCoords().getX0(), -1.6, 0.001);
    assertEquals(description.getMinCoords().getX1(), -1, 0.001);
    assertEquals(description.getMaxCoords().getX0(), 1.6, 0.001);
    assertEquals(description.getMaxCoords().getX1(), 1, 0.001);
    assertEquals(description.getTransforms().getFirst().toString(), "-0.74543, 0.11301", "The transform is not correct");
  }

  /**
   * Tests if the readFromFile() method returns the correct ChaosGameDescription for the Barnsley Fern
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
  public void readFromFileAffineTest(int transformIndex, double a00, double a01, double a10, double a11, double b0, double b1) {
    ChaosGameDescription description = ChaosGameFileHandler.readFromFile("src/test/resources/barnsley-fern.csv");
    String expected = a00 + ", " + a01 + ", " + a10 + ", " + a11 + ", " + b0 + "," + b1;
    List<Transform2D> transforms = description.getTransforms();

    assertEquals(transforms.size(), 4, "The number of transforms is not correct");
    assertEquals(description.getMinCoords().getX0(), -2.65, 0.001);
    assertEquals(description.getMinCoords().getX1(), 0, 0.001);
    assertEquals(description.getMaxCoords().getX0(), 2.65, 0.001);
    assertEquals(description.getMaxCoords().getX1(), 10, 0.001);
    assertEquals(description.getTransforms().get(transformIndex).toString(), expected, "The transforms values are not correct");
  }

  /**
   * Tests if the writeToFile() method writes the correct ChaosGameDescription to a file.
   */
  @Test
  public void writeToFileTest() {
    ChaosGameDescription description = ChaosGameFileHandler.readFromFile("src/test/resources/Julia.csv");
    ChaosGameFileHandler.writeToFile(description, "src/test/resources/JuliaTest.csv");
    ChaosGameDescription newDescription = ChaosGameFileHandler.readFromFile("src/test/resources/JuliaTest.csv");

    assertEquals(description.getMinCoords().getX0(), newDescription.getMinCoords().getX0(), 0.001);
    assertEquals(description.getMinCoords().getX1(), newDescription.getMinCoords().getX1(), 0.001);
    assertEquals(description.getMaxCoords().getX0(), newDescription.getMaxCoords().getX0(), 0.001);
    assertEquals(description.getMaxCoords().getX1(), newDescription.getMaxCoords().getX1(), 0.001);
    assertEquals(description.getTransforms().getFirst().toString(), newDescription.getTransforms().getFirst().toString(), "The transform is not correct");
  }
}