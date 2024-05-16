package no.ntnu.idatg2003.model.game.engine;

import no.ntnu.idatg2003.model.math.datatypes.Complex;
import no.ntnu.idatg2003.model.math.datatypes.Vector2D;

/**
 * A class to generate and draw the Mandelbrot set on a canvas.
 */
public class Mandelbrot {

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

    /**
     * Draws the Mandelbrot set on the canvas.
     */
    public void drawMandelbrot() {
        for (int y = 0; y < width; y++) {
            for (int x = 0; x < height; x++) {
                double ReZ, ImZ, ReC, ImC, tmp;
                Complex point = new Complex(0, 0); //TODO: Forbedre denne metoden. Prøv å implementere JuliaTransform feks.
                ReZ = ImZ = 0;
                ReC = (double) (x - 500) / 250; // Adjust the scaling factor
                ImC = (double) (y - 500) / 250; // Adjust the scaling factor
                Complex c = new Complex(ReC, ImC);
                int maxIterations = 80;
                while (ReZ * ReZ + ImZ * ImZ < 4 && maxIterations > 0) {
                    tmp = ReZ * ReZ - ImZ * ImZ + ReC;
                    ImZ = 2.0 * ReZ * ImZ + ImC;
                    ReZ = tmp;
                    maxIterations--;
                    canvas.putPixel(new Complex(ReC, ImC));
                }
            }
        }
    }

    /**
     * Gets the canvas where the Mandelbrot set is drawn.
     *
     * @return The canvas.
     */
    public ChaosCanvas getCanvas() {
        return canvas;
    }
}
