package no.ntnu.idatg2003.controller;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import no.ntnu.idatg2003.view.PresetGameView;
import no.ntnu.idatg2003.view.RunGameView;

public class RunGameController {

  private Scene runPresetGameScene;

  public void setPresetGameScene(Scene runPresetGameScene) {
    this.runPresetGameScene = runPresetGameScene;
  }

  public void openPresetTransformation(ActionEvent act){
    PresetGameView presetGameView = new PresetGameView();
    setPresetGameScene(presetGameView.getScene());
    Stage stage = (Stage)((Node) act.getSource()).getScene().getWindow();
    setScene(stage, this.runPresetGameScene);
  }

  public void setScene(Stage primaryStage, Scene newScene) {
    primaryStage.hide();
    primaryStage.setScene(newScene);
    primaryStage.show();
  }
}
