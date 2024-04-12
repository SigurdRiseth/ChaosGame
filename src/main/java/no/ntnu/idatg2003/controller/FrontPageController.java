package no.ntnu.idatg2003.controller;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import no.ntnu.idatg2003.view.RunGameView;

public class FrontPageController {

  private Scene runGameScene;

  public void setRunGameScene(Scene runGameScene) {
    this.runGameScene = runGameScene;
  }

  public void openRunGameScene(ActionEvent act){
    RunGameView runGameView = new RunGameView();
    setRunGameScene(runGameView.getScene());
    Stage stage = (Stage)((Node) act.getSource()).getScene().getWindow();
    setScene(stage, this.runGameScene);
  }

  public void setScene(Stage primaryStage, Scene newScene) {
    primaryStage.hide();
    primaryStage.setScene(newScene);
    primaryStage.show();
  }
}
