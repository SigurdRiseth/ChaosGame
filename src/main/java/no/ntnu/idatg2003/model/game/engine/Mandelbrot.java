package no.ntnu.idatg2003.model.game.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import no.ntnu.idatg2003.model.math.datatypes.Complex;
import no.ntnu.idatg2003.model.math.datatypes.Vector2D;
import no.ntnu.idatg2003.utility.logging.LoggerUtil;

/**
 * A class to generate and draw the Mandelbrot set on a canvas.
 * The Mandelbrot class implements the ChaosGameSubject interface.
 * It provides methods to draw the Mandelbrot set and register, remove, and notify observers.
 *
 * <p>The Mandelbrot class generates the Mandelbrot set by iterating through each pixel on the canvas,
 * converting its coordinates to a complex number, and calculating the number of iterations required for
 * the corresponding complex number to escape a certain threshold. The number of iterations determines the
 * color of the pixel.
 *
 * @author Sigurd Riseth
 * @version 1.0.0
 * @see ChaosGameSubject
 * @see ChaosGameObserver
 * @see ChaosCanvas
 * @see Complex
 * @since 27.02.2024
 */
public class Mandelbrot implements ChaosGameSubject {

  private static final int MAX_ITERATIONS = 40;
  private final List<ChaosGameObserver> observers = new ArrayList<>();
  private final ChaosCanvas canvas;
  private final int width;
  private final int height;

  /**
   * Constructs a Mandelbrot object with the specified width and height.
   *
   * @param width  The width of the canvas.
   * @param height The height of the canvas.
   * @throws IllegalArgumentException if the width or height is less than or equal to 0
   */
  public Mandelbrot(int width, int height) {
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("The width and height must be positive");
    }
    this.width = width;
    this.height = height;
    canvas = new ChaosCanvas(new Vector2D(-2, -2), new Vector2D(2, 2), width, height);
  }

  /**
   * Draws the Mandelbrot set on the canvas.
   */
  public void drawMandelbrot() {
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        Complex c = indexToComplex(x, y);
        int iterations = calculateMandelbrot(c);
        canvas.putPixel(x, y, getColor(iterations));
      }
    }
    notifyObservers();
    LoggerUtil.logInfo("Mandelbrot set drawn.");
  }


  /**
   * Converts pixel coordinates to a complex number.
   *
   * @param x The x-coordinate of the pixel.
   * @param y The y-coordinate of the pixel.
   * @return The corresponding complex number.
   */
  private Complex indexToComplex(int x, int y) {
    double real = indexToCoordinate(x);
    double imaginary = indexToCoordinate(y);
    return new Complex(real, imaginary);
  }

  /**
   * Converts pixel index to a coordinate value.
   *
   * @param index The index value.
   * @return The corresponding coordinate value.
   */
  private double indexToCoordinate(int index) {
    return (double) (index - 500) / 250;
  }

  /**
   * Calculates the number of iterations required for the given complex number to escape a certain threshold.
   *
   * @param c The complex number.
   * @return The number of iterations.
   */
  private int calculateMandelbrot(Complex c) {
    Complex z = new Complex(0, 0);
    int iterations = 0;
    while (z.getLength() < 2 && iterations < MAX_ITERATIONS) {
      z = z.multiply(z).add(c);
      iterations++;
    }
    return iterations;
  }

  /**
   * Method to get the color of a pixel based on the number of iterations.
   *
   * @param iterations The number of iterations.
   * @return The color of the pixel.
   */
  private int getColor(int iterations) {
    return iterations * 255 / MAX_ITERATIONS; // Grayscale
  }

  /**
   * Gets the canvas where the Mandelbrot set is drawn.
   *
   * @return The canvas.
   */
  public ChaosCanvas getCanvas() {
    return canvas;
  }

  /**
   * Registers an observer to the list of observers.
   *
   * @param observer The observer to register.
   */
  @Override
  public void registerObserver(ChaosGameObserver observer) {
    Objects.requireNonNull(observer);
    observers.add(observer);
  }

  /**
   * Removes an observer from the list of observers.
   *
   * @param observer The observer to remove.
   */
  @Override
  public void removeObserver(ChaosGameObserver observer) {
    Objects.requireNonNull(observer);
    observers.remove(observer);
  }

  /**
   * Notifies all observers that the state has changed.
   */
  @Override
  public void notifyObservers() {
    for (ChaosGameObserver observer : observers) {
      observer.update();
    }
  }
}
