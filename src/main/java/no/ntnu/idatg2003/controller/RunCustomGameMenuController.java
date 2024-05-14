package no.ntnu.idatg2003.controller;

import javafx.scene.Scene;
import no.ntnu.idatg2003.view.ChaosGameApp;
import no.ntnu.idatg2003.view.RunCustomGameMenu;

public class RunCustomGameMenuController {

  private final ChaosGameApp app;
  private final RunCustomGameMenu runCustomGameMenuView;

  public RunCustomGameMenuController(ChaosGameApp app) {
    this.app = app;
    this.runCustomGameMenuView = new RunCustomGameMenu(this);
  }


  public void returnToLastPage() {
    app.showRunGameScene();
  }

  public Scene getScene() {
    return runCustomGameMenuView.getScene();
  }

  public void runCustomGame(String file) {
    app.runCustomGame(file);
  }
}
