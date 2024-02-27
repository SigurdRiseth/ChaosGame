package no.ntnu.idatg2003.game_engine;

import java.util.List;
import java.util.Random;
import no.ntnu.idatg2003.math_datatypes.Vector2D;
import no.ntnu.idatg2003.transformations.Transform2D;

public class ChaosGame {

  private ChaosCanvas canvas;
  private ChaosGameDescription description;
  private Vector2D currentPoint;
  public Random random;

  public ChaosGame(ChaosGameDescription description, int width, int height) {
    this.description = description;
    this.canvas = new ChaosCanvas(description.getMinCoords(), description.getMaxCoords(),
        width, height);
    this.currentPoint = new Vector2D(0, 0);
  }

  public ChaosCanvas getCanvas() {
    return canvas;
  }

  public void runSteps(int steps) {
    random = new Random(); // Random number.

    for (int i = 0; i < steps; i++) {
      int randomTransform = random.nextInt(description.getTransforms().size());
      List<Transform2D> transforms = description.getTransforms();
      currentPoint = transforms.get(randomTransform).transform(currentPoint);
      canvas.putPixel(currentPoint);
    }
  }

}
