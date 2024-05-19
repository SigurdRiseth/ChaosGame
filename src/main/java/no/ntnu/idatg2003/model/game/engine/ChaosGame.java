package no.ntnu.idatg2003.model.game.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import no.ntnu.idatg2003.model.math.datatypes.Vector2D;
import no.ntnu.idatg2003.model.transformations.Transform2D;
import no.ntnu.idatg2003.utility.LoggerUtil;

/**
 * This class represents a Chaos Game. It contains a canvas, a description of the game, a random
 * object and a current point. It also contains methods to run the game for a given number of steps
 * and to get the canvas. It implements the ChaosGameSubject interface.
 *
 * @see ChaosGameSubject
 * @see ChaosGameObserver
 * @see ChaosCanvas
 * @see ChaosGameDescription
 * @see Vector2D
 * @author Sigurd Riseth, Theodor Sjetnan Utvik
 * @version 0.0.2
 */

public class ChaosGame implements ChaosGameSubject {

  private final ArrayList<ChaosGameObserver> observers = new ArrayList<>();
  private final Random random;
  private final ChaosCanvas canvas;
  private final ChaosGameDescription description;
  private Vector2D currentPoint;

  /**
   * Constructor for the ChaosGame.
   *
   * @param description the description of the game
   * @param width the width of the canvas
   * @param height the height of the canvas
   */
  public ChaosGame(ChaosGameDescription description, int width, int height) {
    this.description = description;
    this.canvas =
        new ChaosCanvas(description.getMinCoords(), description.getMaxCoords(), width, height);
    this.currentPoint = new Vector2D(0, 0);
    this.random = new Random();
  }

  /**
   * Method to get the canvas.
   *
   * @return the canvas
   */
  public ChaosCanvas getCanvas() {
    return canvas;
  }

  /**
   * Method to run the game for a given number of steps.
   *
   * @param steps the number of steps to run
   */
  public void runSteps(int steps) {
    canvas.clear();
    int progress = 0;
    try {
      for (int i = 0; i < steps; i++) {
        int randomTransform = random.nextInt(description.getTransforms().size());
        List<Transform2D> transforms = description.getTransforms();
        currentPoint = transforms.get(randomTransform).transform(currentPoint);
        canvas.putPixel(currentPoint);

        int newProgress = (int) ((i + 1) * 100.0 / steps);

        if (newProgress > progress) {
          progress = newProgress;
          //notifyProgress(progress);
        }
      }
    } catch (Exception e) {
      LoggerUtil.logError("Error: ",e);
    }
    finally {
      notifyObservers();
    }
  }


  @Override
  public void registerObserver(ChaosGameObserver observer) {
    observers.add(observer);
  }

  @Override
  public void removeObserver(ChaosGameObserver observer) {
    observers.remove(observer);
  }

  @Override
  public void notifyObservers() {
    for (ChaosGameObserver observer : observers) {
      observer.update();
    }
  }

}
