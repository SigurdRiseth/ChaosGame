package no.ntnu.idatg2003.controller;

import javafx.scene.Scene;
import no.ntnu.idatg2003.model.game.engine.ChaosCanvas;
import no.ntnu.idatg2003.model.game.engine.ChaosGameDescriptionFactory;
import no.ntnu.idatg2003.model.game.engine.Mandelbrot;
import no.ntnu.idatg2003.model.math.datatypes.Complex;
import no.ntnu.idatg2003.utility.LoggerUtil;
import no.ntnu.idatg2003.view.ChaosGameApp;
import no.ntnu.idatg2003.view.MandelbrotView;

public class MandelbrotController implements ControllerInterface {

  private Mandelbrot mandelbrot;
  private ChaosGameApp app;
  private MandelbrotView view;

  public MandelbrotController(ChaosGameApp app) {
    this.app = app;
    this.view = new MandelbrotView(this);
    this.mandelbrot = new Mandelbrot(1000, 1000);
    mandelbrot.registerObserver(view);
  }

  public Scene getScene() {
    return view.getScene();
  }

  public void createMandelbrotGame() {
    LoggerUtil.logInfo("Starting to draw Mandelbrot set.");
    mandelbrot.drawMandelbrot();
  }

  public void returnToFrontPage() {
    app.showMainScene();
  }

  public ChaosCanvas getCanvas() {
    return mandelbrot.getCanvas();
  }

  public void drawJuliaSet(double re, double im) {
    app.runCustomGame(ChaosGameDescriptionFactory.createJuliaSet(new Complex(re, im)));
  }
}
