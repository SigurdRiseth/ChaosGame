package no.ntnu.idatg2003.view;

import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import no.ntnu.idatg2003.controller.PresetGameController;
import no.ntnu.idatg2003.model.game.engine.ChaosCanvas;
import no.ntnu.idatg2003.model.transformations.AffineTransform2D;
import no.ntnu.idatg2003.model.transformations.JuliaTransform;
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
  private TableView<Transform2D> transformTable;
  private VBox juliaDetailsBox;


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
    //transformTable.setItems(controller.getTransformations());
    this.juliaDetailsBox = createJuliaDetailsBox();

    transformTable.setVisible(false);
    juliaDetailsBox.setVisible(false);

  }

  /**
   * Returns the scene for the view.
   *
   * @return The scene for the view.
   */
  public Scene getScene() {
    BorderPane content = createContent();
    content.setPadding(new Insets(15, 20, 15, 20));
    return new Scene(content, 1600, 900);
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
    runButton.setOnAction(e -> {
      int iterationsValue = Integer.parseInt(iterationsField.getText());
      controller.runGame(iterationsValue);
    });
    content.getChildren().addAll(backButton, iterationsText, iterationsField,
        runButton, infoLabel, juliaDetailsBox, transformTable);
    content.setPadding(new Insets(10));
    return content;
  }

  private TableView<Transform2D> createTransformTable() {
    TableView<Transform2D> table = new TableView<>();
    configureTransformTable(table);
    table.setItems(controller.getTransformations());
    return table;
  }



  private void configureTransformTable(TableView<Transform2D> table) {
    TableColumn<Transform2D, Number> a00Column = new TableColumn<>("a00");
    a00Column.setCellValueFactory(cellData ->
        cellData.getValue() instanceof AffineTransform2D ?
            new ReadOnlyDoubleWrapper(((AffineTransform2D) cellData.getValue()).getMatrix().getA00()) :
            new ReadOnlyDoubleWrapper(Double.NaN));  // Handle non-applicable cases


    TableColumn<Transform2D, Number> a01Column = new TableColumn<>("a01");
    a01Column.setCellValueFactory(cellData ->
        cellData.getValue() instanceof AffineTransform2D ?
            new ReadOnlyDoubleWrapper(((AffineTransform2D) cellData.getValue()).getMatrix().getA01()) :
            new ReadOnlyDoubleWrapper(Double.NaN));  // Handle non-applicable cases

    TableColumn<Transform2D, Number> a10Column = new TableColumn<>("a10");
    a10Column.setCellValueFactory(cellData ->
        cellData.getValue() instanceof AffineTransform2D ?
            new ReadOnlyDoubleWrapper(((AffineTransform2D) cellData.getValue()).getMatrix().getA10()) :
            new ReadOnlyDoubleWrapper(Double.NaN));  // Handle non-applicable cases

    TableColumn<Transform2D, Number> a11Column = new TableColumn<>("a11");
    a11Column.setCellValueFactory(cellData ->
        cellData.getValue() instanceof AffineTransform2D ?
            new ReadOnlyDoubleWrapper(((AffineTransform2D) cellData.getValue()).getMatrix().getA11()) :
            new ReadOnlyDoubleWrapper(Double.NaN));  // Handle non-applicable cases

    TableColumn<Transform2D, Number> b0Column = new TableColumn<>("b0");
    b0Column.setCellValueFactory(cellData ->
        cellData.getValue() instanceof AffineTransform2D ?
            new ReadOnlyDoubleWrapper(((AffineTransform2D) cellData.getValue()).getVector().getX0()) :
            new ReadOnlyDoubleWrapper(Double.NaN));  // Handle non-applicable cases

    TableColumn<Transform2D, Number> b1Column = new TableColumn<>("b1");
    b1Column.setCellValueFactory(cellData ->
        cellData.getValue() instanceof AffineTransform2D ?
            new ReadOnlyDoubleWrapper(((AffineTransform2D) cellData.getValue()).getVector().getX1()) :
            new ReadOnlyDoubleWrapper(Double.NaN));  // Handle non-applicable cases

    table.getColumns().addAll(a00Column, a01Column, a10Column, a11Column, b0Column, b1Column);

  }

  public void updateTableItems(String type) {
    transformTable.setItems(controller.getTransformations());
    transformTable.refresh();
  }

  private VBox createJuliaDetailsBox() {
    VBox box = new VBox();
    Label label = new Label("Julia Transformation Details:");
    box.getChildren().add(label);
    // Add more details as necessary
    return box;
  }

  private void updateJuliaDetails() {
    Transform2D latestJulia = controller.getTransformations().stream()
        .filter(t -> t instanceof JuliaTransform)
        .findFirst().orElse(null);

    if (latestJulia != null) {
      juliaDetailsBox.getChildren().clear();
      JuliaTransform julia = (JuliaTransform) latestJulia;
      Label details = new Label("Complex Constant: " + julia.getComplexConstant());
      juliaDetailsBox.getChildren().add(details);
    }
  }

  public void updateForGameType(String type) {
    boolean isJulia = "julia".equals(type);
    transformTable.setVisible(!isJulia);
    juliaDetailsBox.setVisible(isJulia);

    if (isJulia) {
      updateJuliaDetails();  // Update Julia details if the game type is Julia
    } else {
      transformTable.refresh();  // Refresh the table if it's any other game type
    }
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
