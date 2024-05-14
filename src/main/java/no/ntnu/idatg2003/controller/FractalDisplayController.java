package no.ntnu.idatg2003.controller;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import no.ntnu.idatg2003.model.game.engine.ChaosCanvas;
import no.ntnu.idatg2003.model.game.engine.ChaosGame;
import no.ntnu.idatg2003.model.game.engine.ChaosGameDescription;
import no.ntnu.idatg2003.model.game.engine.ChaosGameDescriptionFactory;
import no.ntnu.idatg2003.model.game.engine.ChaosGameFileHandler;
import no.ntnu.idatg2003.model.game.engine.ChaosGameObserver;
import no.ntnu.idatg2003.model.transformations.Transform2D;
import no.ntnu.idatg2003.view.ChaosGameApp;
import no.ntnu.idatg2003.view.FractalDisplay;

/**
 *
 */

public class FractalDisplayController {

  private ChaosGame game;
  private ChaosGameApp app;
  private FractalDisplay view;

  private ObservableList<Transform2D> transformations = FXCollections.observableArrayList();


  public FractalDisplayController(ChaosGameApp app) {
    this.app = app;
    this.view = new FractalDisplay(this);
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
    observeGame();
  }

  /**
   * Registers the view as an observer of the game.
   * This allows the view to be updated when the game state changes.
   */
  private void observeGame() {
    game.registerObserver(view);
  }

  /**
   * Returns the canvas of the game.
   *
   * @return The canvas of the game.
   */
  public ChaosCanvas getCanvas() {
    return game.getCanvas();
  }

  /**
   * Returns the scene of the view.
   *
   * @return The scene of the view.
   */
  public Scene getScene() {
    return view.getScene();
  }

  /**
   * Runs the game for a given number of iterations.
   *
   * @param iterations The number of iterations to run the game for.
   */
  public void runGame(int iterations) {
    view.clearCanvas();
    game.runSteps(iterations);
  }

  /**
   * Returns to the last page.
   */
  public void openRunGameView() {
    app.showRunGameScene();
  }

  public void updateProgress(int progress) {
    javafx.application.Platform.runLater(() -> view.updateProgressBar(progress));
  }

  /**
   * Creates a custom game from a given file.
   *
   * @param fileName The name of the file to create the custom game from.
   */
  public void createCustomGame(String fileName) {
    ChaosGameDescription description = ChaosGameFileHandler.readFromFile("src/main/user.files/" + fileName);
    game = new ChaosGame(description, 800, 800);
    observeGame();
  }

}
