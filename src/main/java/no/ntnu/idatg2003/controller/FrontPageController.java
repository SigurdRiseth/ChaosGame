package no.ntnu.idatg2003.controller;

import javafx.scene.Scene;
import no.ntnu.idatg2003.view.ChaosGameApp;
import no.ntnu.idatg2003.view.FrontPage;

public class FrontPageController {

  private FrontPage frontPage;
  private ChaosGameApp app;

  /**
   * Constructor for the FrontPageController class.
   * <p>
   *   Initializes the controller with the given ChaosGameApp.
   * </p>
   *
   * @param app The ChaosGameApp to use.
   */
  public FrontPageController(ChaosGameApp app) {
    this.app = app;
    this.frontPage = new FrontPage(this);
  }

  /**
   * Returns the scene for the view.
   *
   * @return The scene for the view.
   */
  public Scene getScene(){
    return frontPage.getScene();
  }

  /**
   * Opens the RunGame scene.
   */
  public void openRunGameScene(){
    app.showRunGameScene();
  }

  /**
   * Opens the PresetGame scene.
   */
  public void openCreateCustomGameScene(){
    app.showCreateCustomGameScene();
  }

  /**
   * Exits the application.
   */
  public void exit() {
    app.exit();
  }
}
