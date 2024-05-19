package no.ntnu.idatg2003.model.game.engine;

import java.util.ArrayList;
import java.util.List;
import no.ntnu.idatg2003.model.math.datatypes.Vector2D;
import no.ntnu.idatg2003.utility.LoggerUtil;

/**
 * A class to generate and draw the Mandelbrot set on a canvas.
 */
public class Mandelbrot implements ChaosGameSubject {

    private final List<ChaosGameObserver> observers = new ArrayList<>();
    private ChaosCanvas canvas;
    private final int width;
    private final int height;

    /**
     * Constructs a Mandelbrot object with specified width and height.
     *
     * @param width  The width of the canvas.
     * @param height The height of the canvas.
     */
    public Mandelbrot(int width, int height) {
        this.width = width;
        this.height = height;
        canvas = new ChaosCanvas(new Vector2D(-2, -2), new Vector2D(2, 2), width, height);
    }

    public void drawMandelbrot() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double ReC = indexToCoordinate(x); // Convert x index to real part
                double ImC = indexToCoordinate(y); // Convert y index to imaginary part
                double ReZ, ImZ, tmp;
                ReZ = ImZ = 0;
                int maxIterations = 40;
                int iterations = 0; // Keep track of iterations
                while (ReZ * ReZ + ImZ * ImZ < 4 && maxIterations > 0) {
                    tmp = ReZ * ReZ - ImZ * ImZ + ReC;
                    ImZ = 2.0 * ReZ * ImZ + ImC;
                    ReZ = tmp;
                    maxIterations--;
                    iterations++; // Increment iterations
                }
                // Set pixel color based on iterations
                int color = getColor(iterations);
                canvas.putPixel(x, y, color); // Set pixel color
            }
        }
        notifyObservers();
        LoggerUtil.logInfo("Mandelbrot set drawn.");
    }

    private double indexToCoordinate(int index) {
        return (double) (index - 500) / 250;
    }

    /**
     * Method to get the color of a pixel based on the number of iterations.
     *
     * @param iterations The number of iterations.
     * @return The color of the pixel.
     */
    private int getColor(int iterations) {
        return iterations * 255 / 40; // Adjust for maximum iterations
    }

    /**
     * Gets the canvas where the Mandelbrot set is drawn.
     *
     * @return The canvas.
     */
    public ChaosCanvas getCanvas() {
        return canvas;
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
