package no.ntnu.idatg2003.controller;

import java.util.List;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.paint.Color;
import no.ntnu.idatg2003.model.file.handling.ChaosGameFileHandler;
import no.ntnu.idatg2003.model.file.handling.ChaosGameTextFileReader;
import no.ntnu.idatg2003.model.game.engine.ChaosCanvas;
import no.ntnu.idatg2003.model.game.engine.ChaosGame;
import no.ntnu.idatg2003.model.game.engine.ChaosGameDescription;
import no.ntnu.idatg2003.model.game.engine.ChaosGameDescriptionFactory;
import no.ntnu.idatg2003.model.game.engine.ChaosGameFileHandler;
import no.ntnu.idatg2003.model.transformations.AffineTransform2D;
import no.ntnu.idatg2003.model.transformations.Transform2D;
import no.ntnu.idatg2003.utility.enums.PresetTransforms;
import no.ntnu.idatg2003.view.ChaosGameApp;
import no.ntnu.idatg2003.view.FractalDisplay;

/**
 *
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
      default -> throw new IllegalArgumentException("Unknown game transformation: " + transformation);
    };
    game = new ChaosGame(description, 800, 800);
    loadTransformations(description.getTransforms());
    view.updateForGameType(transformation.getType());
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
    ChaosGameDescription description = ChaosGameFileHandler.readFromFile(new ChaosGameTextFileReader(),
        "src/main/user.files/" + fileName);
    game = new ChaosGame(description, 800, 800);
    observeGame();
  }

  /**
   * Creates a custom game from a given description.
   *
   * @param description The description of the custom game to create.
   */
  public void createCustomGame(ChaosGameDescription description) {
    game = new ChaosGame(description, 800, 800);
    observeGame();
  }

  public void updateCanvas() {
    view.updateCanvas(game.getCanvas().getCanvasArray());
  }

  public Color calculateColor(int hits) {
    int maxHits = 100;
    if (hits == 0) return Color.WHITE;

    double normalizedHits = Math.min(hits / (double) maxHits, 1.0);

    double red = normalizedHits;  // Increases with more hits
    double blue = 1 - normalizedHits;

    return Color.color(red, 0, blue); // Green is left out.
  }

  /**
   * Initiates the table for the transformations from the controller.
   *
   * @return TableView<Transform2D> The table with the transformations.
   */

  public TableView<Transform2D> createTransformTable() {
    TableView<Transform2D> table = new TableView<>();
    configureTransformTable(table);
    table.setItems(getTransformations());
    return table;
  }

  /**
   * Configures the table for the transformations. Creates columns for each part of the matrix
   * and the vector. Checks if the transformation is an AffineTransform2D, and if so, adds the values
   * to the table.
   *
   * @param table The table to configure.
   */
  public void configureTransformTable(TableView<Transform2D> table) {
    table.getColumns().clear();

    TableColumn<Transform2D, Number> a00Column = new TableColumn<>("a00"); //Sets column header
    a00Column.setCellValueFactory(cellData ->
        cellData.getValue() instanceof AffineTransform2D
            ? new ReadOnlyDoubleWrapper(((AffineTransform2D) cellData.getValue()).getMatrix().getA00()) //Reads data from the matrix
            : new ReadOnlyDoubleWrapper(Double.NaN));  // Handle non-applicable cases


    TableColumn<Transform2D, Number> a01Column = new TableColumn<>("a01");
    a01Column.setCellValueFactory(cellData ->
        cellData.getValue() instanceof AffineTransform2D
            ? new ReadOnlyDoubleWrapper(((AffineTransform2D) cellData.getValue()).getMatrix().getA01())
            : new ReadOnlyDoubleWrapper(Double.NaN));

    TableColumn<Transform2D, Number> a10Column = new TableColumn<>("a10");
    a10Column.setCellValueFactory(cellData ->
        cellData.getValue() instanceof AffineTransform2D
            ? new ReadOnlyDoubleWrapper(((AffineTransform2D) cellData.getValue()).getMatrix().getA10())
            : new ReadOnlyDoubleWrapper(Double.NaN));

    TableColumn<Transform2D, Number> a11Column = new TableColumn<>("a11");
    a11Column.setCellValueFactory(cellData ->
        cellData.getValue() instanceof AffineTransform2D
            ? new ReadOnlyDoubleWrapper(((AffineTransform2D) cellData.getValue()).getMatrix().getA11())
            : new ReadOnlyDoubleWrapper(Double.NaN));

    TableColumn<Transform2D, Number> b0Column = new TableColumn<>("b0");
    b0Column.setCellValueFactory(cellData ->
        cellData.getValue() instanceof AffineTransform2D
            ? new ReadOnlyDoubleWrapper(((AffineTransform2D) cellData.getValue()).getVector().getX0())
            : new ReadOnlyDoubleWrapper(Double.NaN));

    TableColumn<Transform2D, Number> b1Column = new TableColumn<>("b1");
    b1Column.setCellValueFactory(cellData ->
        cellData.getValue() instanceof AffineTransform2D
            ? new ReadOnlyDoubleWrapper(((AffineTransform2D) cellData.getValue()).getVector().getX1())
            : new ReadOnlyDoubleWrapper(Double.NaN));

    table.getColumns().addAll(a00Column, a01Column, a10Column, a11Column, b0Column, b1Column);

  }



}
