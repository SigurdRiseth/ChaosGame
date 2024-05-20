package no.ntnu.idatg2003.model.game.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import no.ntnu.idatg2003.model.math.datatypes.Vector2D;
import no.ntnu.idatg2003.model.transformations.Transform2D;
import no.ntnu.idatg2003.utility.logging.LoggerUtil;

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
   * @throws NullPointerException if the description is null
   * @throws IllegalArgumentException if the width or height is less than or equal to 0
   */
  public ChaosGame(ChaosGameDescription description, int width, int height) throws IllegalArgumentException {
    Objects.requireNonNull(description, "The description cannot be null");
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("The width and height must be positive");
    }
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
   * @throws IllegalArgumentException if steps is negative
   */
  public void runSteps(int steps) {
    if (steps < 0) {
      throw new IllegalArgumentException("Steps must be non-negative");
    }
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
          notifyProgress(progress);
        }
      }
    } catch (Exception e) {
      LoggerUtil.logError("An error occurred while running the game: " + e.getMessage());
    }
    finally {
      notifyObservers();
    }
  }

  /**
   * Method to register an observer.
   *
   * @param observer The observer to register.
   * @throws NullPointerException if the observer is null
   */
  @Override
  public void registerObserver(ChaosGameObserver observer) {
    Objects.requireNonNull(observer, "The observer to add cannot be null");
    observers.add(observer);
  }

  /**
   * Method to remove an observer from the list of observers.
   *
   * @param observer The observer to remove.
   * @throws NullPointerException if the observer is null
   */
  @Override
  public void removeObserver(ChaosGameObserver observer) {
    Objects.requireNonNull(observer, "The observer to remove cannot be null");
    observers.remove(observer);
  }

  /**
   * Method to notify all observers.
   */
  @Override
  public void notifyObservers() {
    for (ChaosGameObserver observer : observers) {
      observer.update();
    }
  }

  /**
   * Method to notify all progress observers.
   *
   * @param progress The progress to notify.
   */
  public void notifyProgress(int progress) {
    for (ChaosGameObserver observer : observers) {
      if (observer instanceof ChaosGameProgressObserver progressObserver) {
        progressObserver.updateProgress(progress);
      }
    }
  }

}
