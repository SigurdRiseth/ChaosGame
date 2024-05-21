package no.ntnu.idatg2003.controller;

import java.util.List;
import java.util.function.Function;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.paint.Color;
import no.ntnu.idatg2003.model.file.handling.ChaosGameFileHandler;
import no.ntnu.idatg2003.model.file.handling.ChaosGameTextFileReader;
import no.ntnu.idatg2003.model.game.engine.ChaosGame;
import no.ntnu.idatg2003.model.game.engine.ChaosGameDescription;
import no.ntnu.idatg2003.model.game.engine.ChaosGameDescriptionFactory;
import no.ntnu.idatg2003.model.transformations.AffineTransform2D;
import no.ntnu.idatg2003.model.transformations.Transform2D;
import no.ntnu.idatg2003.utility.enums.PresetTransforms;
import no.ntnu.idatg2003.view.ChaosGameApp;
import no.ntnu.idatg2003.view.FractalDisplay;

/**
 * The FractalDisplayController class is the controller for the FractalDisplay view.
 *
 * <p>
 * The FractalDisplayController class is responsible for handling user input from the FractalDisplay
 * view. It is responsible for creating a game based on the user input and updating the view with
 * the game state.
 * </p>
 *
 * @author Sigurd Riseth, Theodor Sjetnan Utvik
 * @version 0.0.1
 * @see FractalDisplay
 * @see ChaosGameApp
 * @see ChaosGame
 * @since 10.05.2024
 */
public class FractalDisplayController implements ControllerInterface {

  private ChaosGame game;
  private ChaosGameApp app;
  private FractalDisplay view;

  private ObservableList<Transform2D> transformations = FXCollections.observableArrayList();


  public FractalDisplayController(ChaosGameApp app) {
    this.app = app;
    this.view = new FractalDisplay(this);
  }

  public ObservableList<Transform2D> getTransformations() {
    return transformations;
  }

  private void loadTransformations(List<Transform2D> transforms) {
    transformations.clear();
    transformations.addAll(transforms);
    view.updateTableItems();
  }


  public void createGame(PresetTransforms transformation) {
    ChaosGameDescription description = switch (transformation) {
      case JULIA_SET -> ChaosGameDescriptionFactory.createJuliaSet();
      case SIERPINSKI_TRIANGLE -> ChaosGameDescriptionFactory.createSierpinskiTriangle();
      case BARNSLEY_FERN -> ChaosGameDescriptionFactory.createBarnsleyFern();
      default ->
          throw new IllegalArgumentException("Unknown game transformation: " + transformation);
    };
    game = new ChaosGame(description, 800, 800);
    loadTransformations(description.getTransforms());
    view.updateForGameType(transformation.getType());
    observeGame();
  }

  /**
   * Registers the view as an observer of the game. This allows the view to be updated when the game
   * state changes.
   */
  private void observeGame() {
    game.registerObserver(view);
  }

  /**
   * Returns the scene of the view.
   *
   * @return The scene of the view.
   */
  @Override
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
    app.showRunGameMenu();
  }

  /**
   * Creates a custom game from a given file.
   *
   * @param fileName The name of the file to create the custom game from.
   */
  public void createCustomGame(String fileName) {
    ChaosGameDescription description = ChaosGameFileHandler.readFromFile(
        new ChaosGameTextFileReader(),
        "src/main/user.files/" + fileName);
    game = new ChaosGame(description, 800, 800);
    loadTransformations(description.getTransforms());
    view.updateForGameType(description.getTransforms().getFirst().getType());
    observeGame();
  }

  /**
   * Creates a custom game from a given description.
   *
   * @param description The description of the custom game to create.
   */
  public void createCustomGame(ChaosGameDescription description) {
    game = new ChaosGame(description, 800, 800);
    loadTransformations(description.getTransforms());
    view.updateForGameType(description.getTransforms().getFirst().getType());
    observeGame();
  }

  /**
   * Updates the canvas with the current state of the game.
   */
  public void updateCanvas() {
    view.updateCanvas(game.getCanvas().getCanvasArray());
  }

  /**
   * Calculates the color of a pixel based on the number of hits.
   *
   * @param hits The number of hits for the pixel.
   * @return The color of the pixel.
   */
  public Color calculateColor(int hits) {
    int maxHits = 100;
    if (hits == 0) {
      return Color.WHITE;
    }

    double normalizedHits = Math.min(hits / (double) maxHits, 1.0);

    double blue = 1 - normalizedHits;

    return Color.color(normalizedHits, 0, blue); // Green is left out.
  }

  /**
   * Configures the columns of the transformation table to display the appropriate values from the
   * Transform2D objects.
   *
   * @param a00Column column for the a00 values
   * @param a01Column column for the a01 values
   * @param a10Column column for the a10 values
   * @param a11Column column for the a11 values
   * @param b0Column  column for the b0 values
   * @param b1Column  column for the b1 values
   */
  public void configureTransformTable(
      TableColumn<Transform2D, Number> a00Column,
      TableColumn<Transform2D, Number> a01Column,
      TableColumn<Transform2D, Number> a10Column,
      TableColumn<Transform2D, Number> a11Column,
      TableColumn<Transform2D, Number> b0Column,
      TableColumn<Transform2D, Number> b1Column) {

    configureColumn(a00Column, transform -> transform.getMatrix().getA00());
    configureColumn(a01Column, transform -> transform.getMatrix().getA01());
    configureColumn(a10Column, transform -> transform.getMatrix().getA10());
    configureColumn(a11Column, transform -> transform.getMatrix().getA11());
    configureColumn(b0Column, transform -> transform.getVector().getX0());
    configureColumn(b1Column, transform -> transform.getVector().getX1());
  }

  /**
   * Helper method to configure a table column with the appropriate value extractor.
   *
   * @param column    the table column to configure
   * @param extractor the function to extract the value from the AffineTransform2D
   */
  private void configureColumn(TableColumn<Transform2D, Number> column,
      Function<AffineTransform2D, Number> extractor) {
    column.setCellValueFactory(cellData -> {
      if (cellData.getValue() instanceof AffineTransform2D transform) {
        return new ReadOnlyDoubleWrapper((Double) extractor.apply(transform));
      }
      return new ReadOnlyDoubleWrapper(Double.NaN);
    });
  }


}
