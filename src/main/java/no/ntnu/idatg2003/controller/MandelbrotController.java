package no.ntnu.idatg2003.controller;

import javafx.scene.Scene;
import no.ntnu.idatg2003.model.game.engine.ChaosCanvas;
import no.ntnu.idatg2003.model.game.engine.ChaosGameDescriptionFactory;
import no.ntnu.idatg2003.model.game.engine.Mandelbrot;
import no.ntnu.idatg2003.model.math.datatypes.Complex;
import no.ntnu.idatg2003.utility.logging.LoggerUtil;
import no.ntnu.idatg2003.view.ChaosGameApp;
import no.ntnu.idatg2003.view.MandelbrotView;

/**
 * The MandelbrotController class is the controller for the Mandelbrot view.
 *
 * <p>
 * The MandelbrotController class is responsible for handling user input from the Mandelbrot view.
 * It is responsible for creating a Mandelbrot set and updating the view with the set.
 * </p>
 *
 * @author Sigurd Riseth
 * @version 0.0.1
 * @see Mandelbrot
 * @see MandelbrotView
 * @see ChaosGameApp
 * @see ChaosCanvas
 * @since 10.05.2024
 */
public class MandelbrotController implements ControllerInterface {

  private final Mandelbrot mandelbrot;
  private final ChaosGameApp app;
  private final MandelbrotView view;

  /**
   * Constructor for the MandelbrotController class.
   *
   * <p>
   * Creates a Mandelbrot set and registers the view as an observer.
   * </p>
   *
   * @param app The ChaosGameApp to use.
   */
  public MandelbrotController(ChaosGameApp app) {
    this.app = app;
    this.view = new MandelbrotView(this);
    this.mandelbrot = new Mandelbrot(1000, 1000);
    mandelbrot.registerObserver(view);
  }

  /**
   * Returns the scene for the view.
   *
   * @return The scene for the view.
   */
  @Override
  public Scene getScene() {
    return view.getScene();
  }

  /**
   * Creates the Mandelbrot set.
   */
  public void createMandelbrotGame() {
    LoggerUtil.logInfo("Starting to draw Mandelbrot set.");
    mandelbrot.drawMandelbrot();
  }

  /**
   * Returns to the front page.
   */
  public void returnToFrontPage() {
    app.showMainScene();
  }

  /**
   * Returns the canvas of the Mandelbrot set.
   *
   * @return The canvas of the Mandelbrot set.
   */
  public ChaosCanvas getCanvas() {
    return mandelbrot.getCanvas();
  }

  /**
   * Draws a Julia set with the given real and imaginary values.
   *
   * @param re the real value of the complex number
   * @param im the imaginary value of the complex number
   */
  public void drawJuliaSet(double re, double im) {
    app.runCustomGame(ChaosGameDescriptionFactory.createJuliaSet(new Complex(re, im)));
  }
}
