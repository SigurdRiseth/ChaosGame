package no.ntnu.idatg2003.controller;

import javafx.scene.Scene;
import no.ntnu.idatg2003.view.ChaosGameApp;
import no.ntnu.idatg2003.view.RunCustomGameMenu;

/**
 * The controller class controls the RunCustomGameMenu view. It is responsible for handling user
 * choices from the RunCustomGameMenu view.
 *
 * @author Sigurd Riseth
 * @version 0.0.1
 * @see RunCustomGameMenu
 * @see ChaosGameApp
 * @since 10.05.2024
 */
public class RunCustomGameMenuController implements ControllerInterface {

  private final ChaosGameApp app;
  private final RunCustomGameMenu runCustomGameMenuView;

  /**
   * Constructor for the RunCustomGameMenuController class.
   *
   * @param app The ChaosGameApp to use.
   */
  public RunCustomGameMenuController(ChaosGameApp app) {
    this.app = app;
    this.runCustomGameMenuView = new RunCustomGameMenu(this);
  }

  /**
   * Returns the to the run game menu.
   */
  public void returnToLastPage() {
    app.showRunGameMenu();
  }

  /**
   * Returns the scene for the view.
   *
   * @return The scene for the view.
   */
  @Override
  public Scene getScene() {
    return runCustomGameMenuView.getScene();
  }

  /**
   * Runs a custom game based on the given file path.
   *
   * @param file The file to run the custom game from.
   */
  public void runCustomGame(String file) {
    app.runCustomGame(file);
  }

  /**
   * Displays an error message to the user.
   */
  public void showErrorMessage() {
    runCustomGameMenuView.showErrorMessage(
        "Failed to run custom game. Please ensure values are correct and try again.");
  }
}
