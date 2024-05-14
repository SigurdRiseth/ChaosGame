package no.ntnu.idatg2003.controller;

import javafx.scene.Scene;
import no.ntnu.idatg2003.view.ChaosGameApp;
import no.ntnu.idatg2003.view.RunGameMenu;

public class RunGameMenuController {

  private RunGameMenu view;
  private ChaosGameApp app;

  public RunGameMenuController(ChaosGameApp app) {
    this.app = app;
    this.view = new RunGameMenu(this);
  }

  public Scene getScene(){
    return view.getScene();
  }

  public void openPresetGameScene(String type) {
    app.showPresetsGameScene(type);
  }

  public void openFrontPageScene() {
    app.showMainScene();
  }

  public void openCustomGameMenu() {
    app.showRunCustomGamesScene();
  }
}
