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

class ChaosGameTest {

  private ChaosGameDescription description;

  @BeforeEach
  void setUp() {
    Vector2D minCoords = new Vector2D(0, 0);
    Vector2D maxCoords = new Vector2D(1, 1);

    JuliaTransform juliaTransform1 = new JuliaTransform(new Complex(1,2), 1);
    JuliaTransform juliaTransform2 = new JuliaTransform(new Complex(1,2), -1);
    List<Transform2D> transforms = List.of(juliaTransform1, juliaTransform2);

    description = new ChaosGameDescription(minCoords, maxCoords, transforms);
  }

  @Test
  void testConstructor() {
    ChaosGame chaosGame = new ChaosGame(description, 800, 600);
    assertNotNull(chaosGame);
  }

  @Test
  void testRunSteps() {
    ChaosGame chaosGame = new ChaosGame(description, 800, 600);
    assertDoesNotThrow(() -> chaosGame.runSteps(1000));
  }

  @Test
  void testNullDescriptionConstructor() {
    assertThrows(NullPointerException.class, () -> new ChaosGame(null, 800, 600));
  }

  @Test
  void testNegativeWidthConstructor() {
    assertThrows(IllegalArgumentException.class, () -> new ChaosGame(description, -800, 600));
  }

  @Test
  void testNegativeHeightConstructor() {
    assertThrows(IllegalArgumentException.class, () -> new ChaosGame(description, 800, -600));
  }

  @Test
  void testNegativeSteps() {
    ChaosGame chaosGame = new ChaosGame(description, 800, 600);
    assertThrows(IllegalArgumentException.class, () -> chaosGame.runSteps(-100));
  }

  @Test
  void testRegisterNullObserver() {
    ChaosGame chaosGame = new ChaosGame(description, 800, 600);
    assertThrows(NullPointerException.class, () -> chaosGame.registerObserver(null));
  }

  @Test
  void testRemoveNullObserver() {
    ChaosGame chaosGame = new ChaosGame(description, 800, 600);
    assertThrows(NullPointerException.class, () -> chaosGame.removeObserver(null));
  }
}

