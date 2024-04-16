package no.ntnu.idatg2003.controller;

import javafx.scene.Scene;
import no.ntnu.idatg2003.view.ChaosGameApp;
import no.ntnu.idatg2003.view.FrontPage;

public class FrontPageController {

  private FrontPage frontPage;
  private ChaosGameApp app;

  public FrontPageController(ChaosGameApp app) {
    this.app = app;
    this.frontPage = new FrontPage(this);
  }

  public Scene getScene(){
    return frontPage.getScene();
  }

  public void openRunGameScene(){
    app.showRunGameScene();
  }

  public void exit() {
    app.exit();
  }
}
