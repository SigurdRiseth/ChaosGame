package no.ntnu.idatg2003.controller;

import javafx.scene.Scene;
import no.ntnu.idatg2003.view.ChaosGameApp;
import no.ntnu.idatg2003.view.RunCustomGameMenu;

/**
 * The controller class controls the RunCustomGameMenu view. It is responsible for handling user
 * choices from the RunCustomGameMenu view.
 *
 * @version 0.0.1
 * @since 10.05.2024
 * @see RunCustomGameMenu
 * @see ChaosGameApp
 * @author Sigurd Riseth
 */

public class RunCustomGameMenuController implements ControllerInterface {

  private final ChaosGameApp app;
  private final RunCustomGameMenu runCustomGameMenuView;

  public RunCustomGameMenuController(ChaosGameApp app) {
    this.app = app;
    this.runCustomGameMenuView = new RunCustomGameMenu(this);
  }


  public void returnToLastPage() {
    app.showRunGameScene();
  }

  @Override
  public Scene getScene() {
    return runCustomGameMenuView.getScene();
  }

  public void runCustomGame(String file) {
    app.runCustomGame(file);
  }
}
