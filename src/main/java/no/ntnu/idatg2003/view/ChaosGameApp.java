package no.ntnu.idatg2003.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import no.ntnu.idatg2003.controller.FrontPageController;
import no.ntnu.idatg2003.model.game_engine.ChaosGame;

public class ChaosGameApp extends Application {

  private ChaosGame game;
  private Stage primaryStage;

  private Scene mainScene;

  public static void appMain(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    this.primaryStage = primaryStage;
    FrontPageView frontPage = new FrontPageView();
    //TODO: set the scene

    FrontPageController controller = new FrontPageController();

    primaryStage.setTitle("Chaos Game");
    primaryStage.setMinHeight(600);
    primaryStage.setMinWidth(1000);
    primaryStage.setScene(this.mainScene);
    primaryStage.show();
  }
}
