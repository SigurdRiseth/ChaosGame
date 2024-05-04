package no.ntnu.idatg2003.model.game_engine;

import java.util.List;
import no.ntnu.idatg2003.model.math_datatypes.Vector2D;
import no.ntnu.idatg2003.model.transformations.Transform2D;

/**
 * This class gives the description for the Chaos Game. It contains the minimum and maximum
 * coordinates for the game, as well as a list of transformations.
 *
 * @since 27.02.2024
 * @version 0.0.1
 * @see Vector2D
 * @see Transform2D
 * @author Sigurd Riseth, Theodor Sjetnan Utvik
 */
public class ChaosGameDescription {

  private Vector2D minCoords;
  private Vector2D maxCoords;
  private List<Transform2D> transforms;

  /**
   * Constructs a new ChaosGameDescription instance to define the boundaries and transformation
   * rules of the Chaos Game. This constructor initializes the game's spatial limits and sets up the
   * transformations that will be applied to points within these bounds.
   *
   * @param minCoords The minimum coordinates (lower-left corner) for the game's boundary.
   * @param maxCoords The maximum coordinates (upper-right corner) for the game's boundary.
   * @param transforms A list of transformations !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
   */
  public ChaosGameDescription(
      Vector2D minCoords, Vector2D maxCoords, List<Transform2D> transforms) {
    this.minCoords = minCoords;
    this.maxCoords = maxCoords;
    this.transforms = transforms;
  }

  /**
   * Method to get the minimum coordinates for the game.
   *
   * @return the minimum coordinates for the game
   */
  public Vector2D getMinCoords() {
    return minCoords;
  }

  /**
   * Method to get the maximum coordinates for the game.
   *
   * @return the maximum coordinates for the game
   */
  public Vector2D getMaxCoords() {
    return maxCoords;
  }

  /**
   * Returns a list of transformations. !!!!!!!!!!!!!!!!!!!!
   *
   * @return a list of transformations
   */
  public List<Transform2D> getTransforms() { // TODO: Sho uld this be an iterator?
    return transforms;
  }
}
