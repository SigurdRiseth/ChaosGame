package no.ntnu.idatg2003.model.game.engine;

import java.util.List;
import java.util.Objects;
import no.ntnu.idatg2003.model.math.datatypes.Vector2D;
import no.ntnu.idatg2003.model.transformations.Transform2D;

/**
 * Represents the description of a Chaos Game, specifying its boundaries and transformation rules.
 * It contains the minimum and maximum coordinates for the game area, as well as a list of transformations.
 *
 * <p>This class is responsible for defining the spatial limits and transformation rules applied to points within these bounds.
 *
 * @author Sigurd Riseth, Theodor Sjetnan Utvik
 * @version 0.0.1
 * @since 27.02.2024
 * @see Vector2D
 * @see Transform2D
 */
public class ChaosGameDescription {

  private final Vector2D minCoords;
  private final Vector2D maxCoords;
  private final List<Transform2D> transforms;

  /**
   * Constructs a new ChaosGameDescription instance to define the boundaries and transformation
   * rules of the Chaos Game.
   *
   * @param minCoords  The minimum coordinates (lower-left corner) for the game's boundary.
   * @param maxCoords  The maximum coordinates (upper-right corner) for the game's boundary.
   * @param transforms A list of transformations to be applied within the game's boundaries.
   * @throws NullPointerException if any of the parameters are null
   */
  public ChaosGameDescription(
      Vector2D minCoords, Vector2D maxCoords, List<Transform2D> transforms) {

    Objects.requireNonNull(minCoords, "The minimum coordinates cannot be null");
    Objects.requireNonNull(maxCoords, "The maximum coordinates cannot be null");
    Objects.requireNonNull(transforms, "The list of transformations cannot be null");

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
   * Returns a list of the transformations.
   *
   * @return a list of transformations
   */
  public List<Transform2D> getTransforms() {
    return transforms;
  }
}
