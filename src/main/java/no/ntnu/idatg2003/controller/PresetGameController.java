package no.ntnu.idatg2003.controller;
import static no.ntnu.idatg2003.model.game.engine.ChaosGameFileHandler.readFromFile;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import no.ntnu.idatg2003.model.game.engine.ChaosCanvas;
import no.ntnu.idatg2003.model.game.engine.ChaosGame;
import no.ntnu.idatg2003.model.game.engine.ChaosGameDescription;
import no.ntnu.idatg2003.model.game.engine.ChaosGameDescriptionFactory;
import no.ntnu.idatg2003.model.game.engine.ChaosGameObserver;
import no.ntnu.idatg2003.model.transformations.AffineTransform2D;
import no.ntnu.idatg2003.view.ChaosGameApp;
import no.ntnu.idatg2003.view.PresetGameView;

public class PresetGameController implements ChaosGameObserver {

  private ChaosGame game;
  private ChaosGameApp app;
  private PresetGameView view;

  //private ObservableList<AffineTransform2D> affineTransforms = FXCollections.observableArrayList();

  private ObservableList<AffineTransform2D> affineTransforms = FXCollections.observableArrayList();

  /* public void loadTransformation() {
    List<AffineTransform2D> transforms = readFromFile("src/main/resources/csv/preset.games/barnsley-fern.csv");
    affineTransforms.setAll(transforms);
    view.updateTableItems();  // Invoke update method after loading new data
  } */

  public ObservableList<AffineTransform2D> getAffineTransforms() {
    return affineTransforms;
  }

  public PresetGameController(ChaosGameApp app) {
    this.app = app;
    this.view = new PresetGameView(this);
  }

  public void createGame(String type) {
    switch (type) {
      case "julia":
        ChaosGameDescription juliaDescription = ChaosGameDescriptionFactory.createJuliaSet();
        game = new ChaosGame(juliaDescription, 800, 800);
        break;
      case "sierpinski":
        ChaosGameDescription sierpinskiDescription = ChaosGameDescriptionFactory.createSierpinskiTriangle();
        game = new ChaosGame(sierpinskiDescription, 400, 400);
        break;
      case "barnsley":
        ChaosGameDescription barnsleyDescription = ChaosGameDescriptionFactory.createBarnsleyFern();
        game = new ChaosGame(barnsleyDescription, 400, 400);
        break;
      default:
        throw new IllegalArgumentException("Unknown game type: " + type);
    }
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
    game.runSteps(iterations);
    view.updateCanvas();
  }

  public void openRunGameView() {
    app.showRunGameScene();
  }

  // Logic to load the matrix and vector values, which will then be implemented in the already
  // existing tableview in the PresetGameView
  /* public ObservableList<AffineTransform2D> loadTransformation() {
    ChaosGameDescription description = readFromFile("src/main/resources/csv/preset.games/barnsley-fern.csv");
    affineTransforms.clear();
    for (Transform2D transform : description.getTransforms()) {
      if (transform instanceof AffineTransform2D) {
        affineTransforms.add((AffineTransform2D) transform);
      }
    }
    view.updateTableItems();
    return affineTransforms;
  } */


}
