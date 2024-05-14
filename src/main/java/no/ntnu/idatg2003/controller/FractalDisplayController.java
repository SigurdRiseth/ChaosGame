package no.ntnu.idatg2003.controller;
import javafx.scene.Scene;
import no.ntnu.idatg2003.model.game.engine.ChaosCanvas;
import no.ntnu.idatg2003.model.game.engine.ChaosGame;
import no.ntnu.idatg2003.model.game.engine.ChaosGameDescription;
import no.ntnu.idatg2003.model.game.engine.ChaosGameDescriptionFactory;
import no.ntnu.idatg2003.model.game.engine.ChaosGameFileHandler;
import no.ntnu.idatg2003.view.ChaosGameApp;
import no.ntnu.idatg2003.view.FractalDisplay;

public class FractalDisplayController {

  private ChaosGame game;
  private ChaosGameApp app;
  private FractalDisplay view;

  public FractalDisplayController(ChaosGameApp app) {
    this.app = app;
    this.view = new FractalDisplay(this);
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
    game.runSteps(iterations);
  }

  /**
   * Returns to the last page.
   */
  public void openRunGameView() {
    app.showRunGameScene();
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
