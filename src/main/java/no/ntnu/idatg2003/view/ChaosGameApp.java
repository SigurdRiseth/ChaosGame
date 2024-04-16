package no.ntnu.idatg2003.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import no.ntnu.idatg2003.controller.FrontPageController;
import no.ntnu.idatg2003.controller.PresetGameController;
import no.ntnu.idatg2003.controller.RunGameController;
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

    showMainScene();

    primaryStage.setTitle("Chaos Game");
    primaryStage.setMinHeight(600);
    primaryStage.setMinWidth(800);
    primaryStage.setScene(this.mainScene);
    primaryStage.show();
  }

  private void showMainScene() {
    FrontPageController controller = new FrontPageController(this);
    this.mainScene = controller.getScene();
  }

  public void showRunGameScene() {
    RunGameController controller = new RunGameController(this);
    this.mainScene = controller.getScene();
    primaryStage.setScene(this.mainScene);
  }

  public void showPresetsGameScene(String type) {
    PresetGameController controller = new PresetGameController(this, type); //TOOD: if custom ny meny med custom games?
    this.mainScene = controller.getScene();
    primaryStage.setScene(this.mainScene);
  }

  public void exit() {
    primaryStage.close();
  }

}
