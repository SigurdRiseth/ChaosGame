package no.ntnu.idatg2003.model.game.engine;

import java.util.ArrayList;
import java.util.List;
import no.ntnu.idatg2003.model.math.datatypes.Complex;
import no.ntnu.idatg2003.model.math.datatypes.Vector2D;
import no.ntnu.idatg2003.utility.logging.LoggerUtil;

/**
 * A class to generate and draw the Mandelbrot set on a canvas.
 */
public class Mandelbrot implements ChaosGameSubject {

    private final List<ChaosGameObserver> observers = new ArrayList<>();
    private final ChaosCanvas canvas;
    private final int width;
    private final int height;
    private static final int MAX_ITERATIONS = 40;

    /**
     * Constructs a Mandelbrot object with specified width and height.
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

    private Complex indexToComplex(int x, int y) {
        double real = indexToCoordinate(x);
        double imaginary = indexToCoordinate(y);
        return new Complex(real, imaginary);
    }

    private double indexToCoordinate(int index) {
        return (double) (index - 500) / 250;
    }

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
        observers.add(observer);
    }

    /**
     * Removes an observer from the list of observers.
     *
     * @param observer The observer to remove.
     */
    @Override
    public void removeObserver(ChaosGameObserver observer) {
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
