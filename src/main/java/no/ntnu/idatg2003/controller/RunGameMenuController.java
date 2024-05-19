package no.ntnu.idatg2003.controller;

import javafx.scene.Scene;
import no.ntnu.idatg2003.utility.enums.PresetTransforms;
import no.ntnu.idatg2003.view.ChaosGameApp;
import no.ntnu.idatg2003.view.RunGameMenu;

/**
 * The controller is responsible for handling user choices from the RunGameMenu view.
 *
 * @version 0.0.2
 * @since 8.05.2024
 * @see RunGameMenu
 * @see ChaosGameApp
 * @author Sigurd Riseth
 */

public class RunGameMenuController implements ControllerInterface{

  private RunGameMenu view;
  private ChaosGameApp app;

  public RunGameMenuController(ChaosGameApp app) {
    this.app = app;
    this.view = new RunGameMenu(this);
  }

  @Override
  public Scene getScene(){
    return view.getScene();
  }

  public void openPresetGameScene(PresetTransforms type) {
    app.showPresetsGameScene(type);
  }

  public void openFrontPageScene() {
    app.showMainScene();
  }

  public void openCustomGameMenu() {
    app.showRunCustomGamesScene();
  }
}
