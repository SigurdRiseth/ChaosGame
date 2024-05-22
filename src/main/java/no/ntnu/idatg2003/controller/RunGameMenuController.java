package no.ntnu.idatg2003.controller;

import javafx.scene.Scene;
import no.ntnu.idatg2003.utility.enums.PresetTransforms;
import no.ntnu.idatg2003.view.ChaosGameApp;
import no.ntnu.idatg2003.view.RunGameMenu;

/**
 * The controller is responsible for handling user choices from the RunGameMenu view.
 *
 * @author Sigurd Riseth
 * @version 0.0.2
 * @see RunGameMenu
 * @see ChaosGameApp
 * @since 8.05.2024
 */
public class RunGameMenuController implements ControllerInterface {

  private final RunGameMenu view;
  private final ChaosGameApp app;

  /**
   * Constructor for the RunGameMenuController class.
   *
   * @param app The ChaosGameApp to use.
   */
  public RunGameMenuController(ChaosGameApp app) {
    this.app = app;
    this.view = new RunGameMenu(this);
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
   * Opens the PresetGame scene with the given type.
   *
   * @param type The type of preset to open.
   */
  public void openPresetGameScene(PresetTransforms type) {
    app.showPresetsGameScene(type);
  }

  /**
   * Opens the front page scene.
   */
  public void openFrontPageScene() {
    app.showMainScene();
  }

  /**
   * Opens the custom game menu scene.
   */
  public void openCustomGameMenu() {
    app.showRunCustomGamesScene();
  }
}
