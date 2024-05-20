package no.ntnu.idatg2003.game.engine;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import no.ntnu.idatg2003.model.game.engine.ChaosGameDescription;
import no.ntnu.idatg2003.model.math.datatypes.Matrix2x2;
import no.ntnu.idatg2003.model.math.datatypes.Vector2D;
import no.ntnu.idatg2003.model.transformations.AffineTransform2D;
import no.ntnu.idatg2003.model.transformations.Transform2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ChaosGameDescriptionTest {

  private Vector2D minCoords;
  private Vector2D maxCoords;
  private List<Transform2D> transforms;

  @BeforeEach
  void setUp() {
    minCoords = new Vector2D(0, 0);
    maxCoords = new Vector2D(1, 1);
    AffineTransform2D transform = new AffineTransform2D(
        new Matrix2x2(1, 0, 0, 1), new Vector2D(0, 0)
    );
    transforms = List.of(transform);
  }

  @Test
  void testConstructor() {
    ChaosGameDescription description = new ChaosGameDescription(minCoords, maxCoords, transforms);

    assertSame(minCoords, description.getMinCoords(),
        "The minCoords should be the same as the one given in the constructor");
    assertSame(maxCoords, description.getMaxCoords(),
        "The maxCoords should be the same as the one given in the constructor");
    assertSame(transforms, description.getTransforms(),
        "The transforms should be the same as the one given in the constructor");
  }

  @Test
  void nullVectorCoords() {
    assertThrows(NullPointerException.class, () -> new ChaosGameDescription(null, maxCoords, transforms),
        "The constructor should throw a NullPointerException when the minCoords parameter is null.");
    assertThrows(NullPointerException.class, () -> new ChaosGameDescription(minCoords, null, transforms),
        "The constructor should throw a NullPointerException when the maxCoords parameter is null.");
  }

  @Test
  void nullTransforms() {
    assertThrows(NullPointerException.class, () -> new ChaosGameDescription(minCoords, maxCoords, null),
        "The constructor should throw a NullPointerException when the transforms parameter is null.");
  }

}
