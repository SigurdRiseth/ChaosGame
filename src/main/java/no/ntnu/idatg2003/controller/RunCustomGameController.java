package no.ntnu.idatg2003.controller;

import javafx.scene.Scene;
import no.ntnu.idatg2003.view.ChaosGameApp;
import no.ntnu.idatg2003.view.RunCustomGame;

public class RunCustomGameController {

  private final ChaosGameApp app;
  private final RunCustomGame runCustomGameView;

  public RunCustomGameController(ChaosGameApp app) {
    this.app = app;
    this.runCustomGameView = new RunCustomGame(this);
  }


  public void returnToLastPage() {
    app.showRunGameScene();
  }

  public Scene getScene() {
    return runCustomGameView.getScene();
  }
}
