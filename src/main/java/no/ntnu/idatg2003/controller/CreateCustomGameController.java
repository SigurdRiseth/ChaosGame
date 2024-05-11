package no.ntnu.idatg2003.controller;

import javafx.scene.Scene;
import no.ntnu.idatg2003.view.ChaosGameApp;
import no.ntnu.idatg2003.view.CreateCustomGame;

public class CreateCustomGameController {
  private ChaosGameApp app;
  private CreateCustomGame createCustomGameView;

  /**
   * Constructor for the CreateCustomGameController class.
   *
   * @param app The ChaosGameApp to use.
   */
  public CreateCustomGameController(ChaosGameApp app) {
    this.app = app;
    this.createCustomGameView = new CreateCustomGame(this);
  }

  /**
   * Returns the scene for the view.
   *
   * @return The scene for the view.
   */
  public Scene getScene() {
    return createCustomGameView.getScene();
  }

  /**
   * Returns to the front page.
   */
  public void returnToFrontPage() {
    app.showMainScene();
  }

}
