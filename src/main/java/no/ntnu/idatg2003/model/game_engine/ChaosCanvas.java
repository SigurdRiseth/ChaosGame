package no.ntnu.idatg2003.model.game_engine;

import no.ntnu.idatg2003.model.math_datatypes.Matrix2x2;
import no.ntnu.idatg2003.model.math_datatypes.Vector2D;
import no.ntnu.idatg2003.model.transformations.AffineTransform2D;

/**
 * This class represents a canvas for the Chaos Game. It contains a 2D array of integers, and
 * methods to get and put pixels on the canvas. It also contains a method to clear the canvas.
 *
 * @since 27.02.2024
 * @version 0.0.1
 * @see Vector2D
 * @see AffineTransform2D
 * @see Matrix2x2
 * @author Sigurd Riseth, Theodor Sjetnan Utvik
 */
public class ChaosCanvas {
  private final AffineTransform2D transformCoordsToIndices;
  private int[][] canvas;
  private final int width;
  private final int height;

  /**
   * Constructor for the ChaosCanvas. It initializes the canvas with the given dimensions and
   * creates an AffineTransform2D object to transform coordinates to indices. It also initializes
   * the canvas with the given dimensions.
   *
   * @param minCoords the minimum coordinates for the canvas
   * @param maxCoords the maximum coordinates for the canvas
   * @param width the width of the canvas
   * @param height the height of the canvas
   */
  public ChaosCanvas(Vector2D minCoords, Vector2D maxCoords, int width, int height) {
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
   * The method return the value of a pixel at a given point.
   *
   * @param point the point to get the pixel value from
   * @return the value of the pixel at the given point
   */
  public int getPixel(Vector2D point) {
    Vector2D indexPoint = transformCoordsToIndices.transform(point);
    return canvas[(int) indexPoint.getX0()][(int) indexPoint.getX1()]; //TODO: rund av til nærmeste heltall
  }

  /**
   * The method places a point at the given index on the canvas.
   *
   * @param point the point to place on the canvas
   */
  public void putPixel(Vector2D point) {
    Vector2D indexPoint = transformCoordsToIndices.transform(point);
    this.canvas[(int) indexPoint.getX0()][(int) indexPoint.getX1()] = 1;
  }

  /**
   * The method returns the canvas array.
   *
   * @return the canvas array
   */
  public int[][] getCanvasArray() {
    return this.canvas;
  }

  /** The method clears the canvas by creating a new canvas array. */
  public void clear() {
    this.canvas = new int[height][width];
  }

}
