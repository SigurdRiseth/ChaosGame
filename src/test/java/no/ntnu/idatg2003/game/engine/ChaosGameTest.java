package no.ntnu.idatg2003.game.engine;

import java.util.List;
import no.ntnu.idatg2003.model.game.engine.ChaosGame;
import no.ntnu.idatg2003.model.game.engine.ChaosGameDescription;
import no.ntnu.idatg2003.model.math.datatypes.Complex;
import no.ntnu.idatg2003.model.math.datatypes.Vector2D;
import no.ntnu.idatg2003.model.transformations.JuliaTransform;
import no.ntnu.idatg2003.model.transformations.Transform2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the ChaosGame class.
 */
class ChaosGameTest {

  private ChaosGameDescription description;

  /**
   * Set up test environment before each test.
   */
  @BeforeEach
  void setUp() {
    Vector2D minCoords = new Vector2D(0, 0);
    Vector2D maxCoords = new Vector2D(1, 1);

    JuliaTransform juliaTransform1 = new JuliaTransform(new Complex(1,2), 1);
    JuliaTransform juliaTransform2 = new JuliaTransform(new Complex(1,2), -1);
    List<Transform2D> transforms = List.of(juliaTransform1, juliaTransform2);

    description = new ChaosGameDescription(minCoords, maxCoords, transforms);
  }

  /**
   * Test constructor with valid parameters.
   */
  @Test
  void testConstructor() {
    ChaosGame chaosGame = new ChaosGame(description, 800, 600);
    assertNotNull(chaosGame, "ChaosGame object should not be null");
  }

  /**
   * Test runSteps method with a positive number of steps.
   */
  @Test
  void testRunSteps() {
    ChaosGame chaosGame = new ChaosGame(description, 800, 600);
    assertDoesNotThrow(() -> chaosGame.runSteps(1000), "runSteps should not throw any exceptions");
  }

  /**
   * Test constructor with a null description parameter.
   */
  @Test
  void testNullDescriptionConstructor() {
    assertThrows(NullPointerException.class, () -> new ChaosGame(null, 800, 600), "Constructor should throw NullPointerException for null description");
  }

  /**
   * Test constructor with a negative width parameter.
   */
  @Test
  void testNegativeWidthConstructor() {
    assertThrows(IllegalArgumentException.class, () -> new ChaosGame(description, -800, 600), "Constructor should throw IllegalArgumentException for negative width");
  }

  /**
   * Test constructor with a negative height parameter.
   */
  @Test
  void testNegativeHeightConstructor() {
    assertThrows(IllegalArgumentException.class, () -> new ChaosGame(description, 800, -600), "Constructor should throw IllegalArgumentException for negative height");
  }

  /**
   * Test runSteps method with a negative number of steps.
   */
  @Test
  void testNegativeSteps() {
    ChaosGame chaosGame = new ChaosGame(description, 800, 600);
    assertThrows(IllegalArgumentException.class, () -> chaosGame.runSteps(-100), "runSteps should throw IllegalArgumentException for negative steps");
  }

  /**
   * Test registering a null observer.
   */
  @Test
  void testRegisterNullObserver() {
    ChaosGame chaosGame = new ChaosGame(description, 800, 600);
    assertThrows(NullPointerException.class, () -> chaosGame.registerObserver(null), "registerObserver should throw NullPointerException for null observer");
  }

  /**
   * Test removing a null observer.
   */
  @Test
  void testRemoveNullObserver() {
    ChaosGame chaosGame = new ChaosGame(description, 800, 600);
    assertThrows(NullPointerException.class, () -> chaosGame.removeObserver(null), "removeObserver should throw NullPointerException for null observer");
  }
}
