package no.ntnu.idatg2003.controller;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import no.ntnu.idatg2003.model.game.engine.ChaosCanvas;
import no.ntnu.idatg2003.model.game.engine.ChaosGame;
import no.ntnu.idatg2003.model.game.engine.ChaosGameDescription;
import no.ntnu.idatg2003.model.game.engine.ChaosGameDescriptionFactory;
import no.ntnu.idatg2003.model.game.engine.ChaosGameObserver;
import no.ntnu.idatg2003.model.transformations.Transform2D;
import no.ntnu.idatg2003.view.ChaosGameApp;
import no.ntnu.idatg2003.view.PresetGameView;

public class PresetGameController implements ChaosGameObserver {

  private ChaosGame game;
  private ChaosGameApp app;
  private PresetGameView view;

  private ObservableList<Transform2D> transformations = FXCollections.observableArrayList();


  public PresetGameController(ChaosGameApp app) {
    this.app = app;
    this.view = new PresetGameView(this);
    //initializeTransformations();
  }

  public ObservableList<Transform2D> getTransformations() {
    return transformations;
  }

  /*private void initializeTransformations() {
    loadTransformations(ChaosGameDescriptionFactory.createBarnsleyFern().getTransforms());
    loadTransformations(ChaosGameDescriptionFactory.createJuliaSet().getTransforms());
    loadTransformations(ChaosGameDescriptionFactory.createSierpinskiTriangle().getTransforms());
  } */

  private void loadTransformations(List<Transform2D> transforms, String type) {
    transformations.clear();
    transformations.addAll(transforms);
    view.updateTableItems(type);
  }



  public void createGame(String type) {
    ChaosGameDescription description;
    switch (type) {
      case "julia":
        description = ChaosGameDescriptionFactory.createJuliaSet();
        break;
      case "sierpinski":
        description = ChaosGameDescriptionFactory.createSierpinskiTriangle();
        break;
      case "barnsley":
        description = ChaosGameDescriptionFactory.createBarnsleyFern();
        break;
      default:
        throw new IllegalArgumentException("Unknown game type: " + type);
    }
    game = new ChaosGame(description, 800, 800);
    loadTransformations(description.getTransforms(), type);
    view.updateForGameType(type);
  }

  @Override
  public void update() {
    view.updateCanvas();
  }

  public ChaosCanvas getCanvas() {
    return game.getCanvas();
  }

  public Scene getScene() {
    return view.getScene();
  }

  public void runGame(int iterations) {
    view.clearCanvas();
    game.runSteps(iterations);
    view.updateCanvas();
  }

  public void openRunGameView() {
    app.showRunGameScene();
  }


  @Override
  public void updateProgress(int progress) {
    javafx.application.Platform.runLater(() -> view.updateProgressBar(progress));
  }

}
