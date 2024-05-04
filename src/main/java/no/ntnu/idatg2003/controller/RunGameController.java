package no.ntnu.idatg2003.controller;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import no.ntnu.idatg2003.view.ChaosGameApp;
import no.ntnu.idatg2003.view.FrontPage;
import no.ntnu.idatg2003.view.PresetGameView;
import no.ntnu.idatg2003.view.RunGameView;

public class RunGameController {

  private RunGameView view;
  private ChaosGameApp app;

  public RunGameController(ChaosGameApp app) {
    this.app = app;
    this.view = new RunGameView(this);
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
}
