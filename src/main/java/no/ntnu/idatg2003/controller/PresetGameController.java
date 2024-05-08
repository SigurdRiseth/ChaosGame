package no.ntnu.idatg2003.controller;
import javafx.scene.Scene;
import no.ntnu.idatg2003.model.game.engine.ChaosCanvas;
import no.ntnu.idatg2003.model.game.engine.ChaosGame;
import no.ntnu.idatg2003.model.game.engine.ChaosGameDescription;
import no.ntnu.idatg2003.model.game.engine.ChaosGameDescriptionFactory;
import no.ntnu.idatg2003.model.game.engine.ChaosGameObserver;
import no.ntnu.idatg2003.view.ChaosGameApp;
import no.ntnu.idatg2003.view.PresetGameView;

public class PresetGameController implements ChaosGameObserver {

  private ChaosGame game;
  private ChaosGameApp app;
  private PresetGameView view;

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

}
