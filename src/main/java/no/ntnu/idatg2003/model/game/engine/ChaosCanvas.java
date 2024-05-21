package no.ntnu.idatg2003.model.game.engine;

import java.util.Objects;
import no.ntnu.idatg2003.model.math.datatypes.Matrix2x2;
import no.ntnu.idatg2003.model.math.datatypes.Vector2D;
import no.ntnu.idatg2003.model.transformations.AffineTransform2D;

/**
 * Represents a canvas for the Chaos Game, providing methods to manipulate pixel values.
 * The canvas is represented as a 2D array of integers.
 *
 * <p>This class includes methods to retrieve and set pixel values, as well as clearing the canvas.
 *
 * @author Sigurd Riseth, Theodor Sjetnan Utvik
 * @version 0.0.1
 * @since 27.02.2024
 * @see Vector2D
 * @see AffineTransform2D
 * @see Matrix2x2
 */
public class ChaosCanvas {

  private final AffineTransform2D transformCoordsToIndices;
  private final int width;
  private final int height;
  private int[][] canvas;

  /**
   * Constructor for the ChaosCanvas. It initializes the canvas with the given dimensions and
   * creates an AffineTransform2D object to transform coordinates to indices. It also initializes
   * the canvas with the given dimensions.
   *
   * @param minCoords the minimum coordinates for the canvas
   * @param maxCoords the maximum coordinates for the canvas
   * @param width     the width of the canvas
   * @param height    the height of the canvas
   * @throws NullPointerException     if the minimum or maximum coordinates are null
   * @throws IllegalArgumentException if the width or height is less than or equal to 0
   */
  public ChaosCanvas(Vector2D minCoords, Vector2D maxCoords, int width, int height) {
    Objects.requireNonNull(minCoords, "The minimum coordinates cannot be null");
    Objects.requireNonNull(maxCoords, "The maximum coordinates cannot be null");
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("The width and height must be positive");
    }
    this.width = width;
    this.height = height;
    this.canvas = new int[height][width];

    Matrix2x2 matrixA =
        new Matrix2x2(
            0,
            (height - 1) / (minCoords.getX1() - maxCoords.getX1()),
            (width - 1) / (maxCoords.getX0() - minCoords.getX0()),
            0);

    Vector2D b =
        new Vector2D(
            ((height - 1) * maxCoords.getX1()) / (maxCoords.getX1() - minCoords.getX1()),
            ((width - 1) * minCoords.getX0()) / (minCoords.getX0() - maxCoords.getX0()));

    this.transformCoordsToIndices = new AffineTransform2D(matrixA, b);
  }

  /**
   * Retrieves the value of the pixel at the specified point on the canvas.
   *
   * @param point the point for which to retrieve the pixel value
   * @return the value of the pixel at the specified point
   */
  public int getPixel(Vector2D point) {
    Vector2D indexPoint = transformCoordsToIndices.transform(point);
    int xIndex = (int) Math.round(indexPoint.getX0());
    int yIndex = (int) Math.round(indexPoint.getX1());
    return canvas[xIndex][yIndex];
  }

  /**
   * Increases the value of the pixel at the specified point on the canvas.
   *
   * @param point the point at which to set the pixel value
   * @throws IndexOutOfBoundsException if the point is outside the canvas
   */
  public void putPixel(Vector2D point) throws IndexOutOfBoundsException {
    Vector2D indexPoint = transformCoordsToIndices.transform(point);
    int xIndex = (int) Math.round(indexPoint.getX0());
    int yIndex = (int) Math.round(indexPoint.getX1());
    this.canvas[xIndex][yIndex] += 1;
  }

  /**
   * Sets the value of the pixel at the specified coordinates on the canvas.
   *
   * @param x     the x-coordinate of the pixel
   * @param y     the y-coordinate of the pixel
   * @param value the value to set for the pixel
   * @throws IndexOutOfBoundsException if the coordinates are outside the canvas
   */
  public void putPixel(int x, int y, int value) {
    this.canvas[y][x] = value;
  }

  /**
   * Retrieves the 2D array representing the canvas.
   *
   * @return the canvas array
   */
  public int[][] getCanvasArray() {
    return this.canvas;
  }

  /**
   * Clears the canvas by creating a new empty canvas array.
   */
  public void clear() {
    this.canvas = new int[height][width];
  }

}
