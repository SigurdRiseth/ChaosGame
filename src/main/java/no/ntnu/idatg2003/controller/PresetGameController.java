package no.ntnu.idatg2003.controller;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import no.ntnu.idatg2003.model.game_engine.ChaosCanvas;
import no.ntnu.idatg2003.model.game_engine.ChaosGame;
import no.ntnu.idatg2003.model.game_engine.ChaosGameDescription;
import no.ntnu.idatg2003.model.game_engine.ChaosGameDescriptionFactory;
import no.ntnu.idatg2003.model.game_engine.ChaosGameObserver;
import no.ntnu.idatg2003.view.PresetGameView;

public class PresetGameController implements ChaosGameObserver {

  private Scene presetGameScene;
  private ChaosGame game;

  public void openPresetGameScene(ActionEvent act){
    PresetGameView presetGameView = new PresetGameView();
    setPresetGameScene(presetGameView.getScene(this));
    ChaosGameDescription description = ChaosGameDescriptionFactory.createJuliaSet();
    game = new ChaosGame(description, 500, 500);
    game.registerObserver(this);

    Stage stage = (Stage)((Node) act.getSource()).getScene().getWindow();
    setScene(stage, this.presetGameScene);
  }

  public void setPresetGameScene(Scene presetGameScene) {
    this.presetGameScene = presetGameScene;
  }

  public void setScene(Stage primaryStage, Scene newScene) {
    primaryStage.hide();
    primaryStage.setScene(newScene);
    primaryStage.show();
  }

  @Override
  public void update() {
    //presetGameScene.updateCanvas(game.getCanvas());
  }

  public ChaosCanvas getCanvas() {
    return game.getCanvas();
  }
}
