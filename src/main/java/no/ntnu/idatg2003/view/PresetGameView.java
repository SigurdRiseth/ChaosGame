package no.ntnu.idatg2003.view;

import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import no.ntnu.idatg2003.controller.PresetGameController;
import no.ntnu.idatg2003.model.game.engine.ChaosCanvas;
import no.ntnu.idatg2003.model.transformations.AffineTransform2D;
import no.ntnu.idatg2003.model.transformations.Transform2D;

/**
 * The view for the PresetGame Page.
 * <p>
 *    This class is responsible for displaying the game view for the preset game.
 * </p>
 */
public class PresetGameView {

  private final PresetGameController controller;
  private Canvas canvas;
  private TableView<AffineTransform2D> transformTable;

  /**
   * Constructor for the PresetGameView class.
   * <p>
   *   Initializes the view with the given controller and initializes the canvas.
   * </p>
   *
   * @param controller The controller for the view.
   */
  public PresetGameView(PresetGameController controller) {
    this.controller = controller;
    this.canvas = new Canvas(800, 800);
    this.transformTable = createTransformTable();
    //updateTableItems();
  }

  /**
   * Returns the scene for the view.
   *
   * @return The scene for the view.
   */
  public Scene getScene() {
    BorderPane content = createContent();
    content.setPadding(new Insets(15, 20, 15, 20));
    content.setMinWidth(1350);
    return new Scene(content, 1400, 900);
  }

  /**
   * Creates the content for the view.
   *
   * @return BorderPane with the content.
   */
  private BorderPane createContent() {
    BorderPane content = new BorderPane();

    VBox leftPanel = createLeftPanel();
    content.setLeft(leftPanel);
    BorderPane.setMargin(leftPanel, new Insets(0, 20, 0, 0));

    StackPane canvasContainer = new StackPane();
    canvasContainer.getChildren().add(canvas);
    canvasContainer.setStyle("-fx-border-color: black; -fx-border-width: 2;");


    content.setRight(canvasContainer);

    return content;
  }

  /**
   * Creates the left panel for the view.
   * <p>
   *    This contains the return button, input field for iterations, run button, and general info.
   * </p>
   *
   * @return VBox with the left panel content.
   */
  private VBox createLeftPanel() {
    Button backButton = new Button("Return");
    backButton.setOnAction(e -> {
      canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
      controller.openRunGameView();
    });

    VBox content = new VBox(10);
    Text iterationsText = new Text("Iterations: ");
    TextField iterationsField = new TextField();
    Button runButton = new Button("Run");
    Label infoLabel = new Label("Values for this transformation:");
    HBox minMax = createMinMaxBox();
    runButton.setOnAction(e -> {
      int iterationsValue = Integer.parseInt(iterationsField.getText());
      controller.runGame(iterationsValue);
    });
    content.getChildren().addAll(backButton, iterationsText, iterationsField,
        runButton, infoLabel, minMax, createTransformTable());
    content.setPadding(new Insets(10));
    return content;
  }

  private TableView<AffineTransform2D> createTransformTable() {
    TableView<AffineTransform2D> table = new TableView<>();
    configureTransformTable(table);
    return table;
  }

  /*public void updateTableItems() {
    transformTable.setItems(FXCollections.observableArrayList(controller.loadTransformation()));
  } */


  private TableView<AffineTransform2D> configureTransformTable(TableView<AffineTransform2D> table) {
    // Column for matrix element a00
    TableColumn<AffineTransform2D, Number> a00Column = new TableColumn<>("a00");
    a00Column.setCellValueFactory(cellData ->
        new ReadOnlyDoubleWrapper(cellData.getValue().getMatrix().getA00()));

    // Column for matrix element a01
    TableColumn<AffineTransform2D, Number> a01Column = new TableColumn<>("a01");
    a01Column.setCellValueFactory(cellData ->
        new ReadOnlyDoubleWrapper(cellData.getValue().getMatrix().getA01()));

    // Additional columns for a10, a11, b0, b1
    TableColumn<AffineTransform2D, Number> a10Column = new TableColumn<>("a10");
    a10Column.setCellValueFactory(cellData ->
        new ReadOnlyDoubleWrapper(cellData.getValue().getMatrix().getA10()));

    TableColumn<AffineTransform2D, Number> a11Column = new TableColumn<>("a11");
    a11Column.setCellValueFactory(cellData ->
        new ReadOnlyDoubleWrapper(cellData.getValue().getMatrix().getA11()));

    TableColumn<AffineTransform2D, Number> b0Column = new TableColumn<>("b0");
    b0Column.setCellValueFactory(cellData ->
        new ReadOnlyDoubleWrapper(cellData.getValue().getVector().getX0()));

    TableColumn<AffineTransform2D, Number> b1Column = new TableColumn<>("b1");
    b1Column.setCellValueFactory(cellData ->
        new ReadOnlyDoubleWrapper(cellData.getValue().getVector().getX1()));

    table.getColumns().addAll(a00Column, a01Column, a10Column, a11Column, b0Column, b1Column);
    return table;
  }




  /**
   * Creates the HBox containing the min and max values for the transformation.
   *
   * @return HBox with the min and max values.
   */
  private HBox createMinMaxBox() {
    HBox minMax = new HBox(10);
    Label minLabel = new Label("Min:");
    Label maxLabel = new Label("Max:");
    TextField minField = new TextField("(-2.65, 0.0)");
    minField.setEditable(false);
    TextField maxField = new TextField("(2.65, 10)");
    maxField.setEditable(false);
    minMax.getChildren().addAll(minLabel, minField, maxLabel, maxField);
    return minMax;
  }



  /**
   * Updates the canvas with the current state of the game.
   */
  public void updateCanvas() {
    drawCanvas(controller.getCanvas());
  }

  /**
   * Draws the given ChaosCanvas on the canvas.
   *
   * @param chaosCanvas The ChaosCanvas to draw.
   */
  private void drawCanvas(ChaosCanvas chaosCanvas) {
    int[][] canvasArray = chaosCanvas.getCanvasArray();
    int width = canvasArray[0].length;
    int height = canvasArray.length;

    GraphicsContext gc = canvas.getGraphicsContext2D();
    gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); // Clear existing content

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        int pixelValue = canvasArray[y][x];
        Color color = (pixelValue == 1) ? Color.BLACK : Color.WHITE;
        gc.setFill(color);
        gc.fillRect(x, y, 1, 1);
      }
    }
  }

}
