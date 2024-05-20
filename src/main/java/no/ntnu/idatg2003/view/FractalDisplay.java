package no.ntnu.idatg2003.view;

import javafx.application.Platform;
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
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import no.ntnu.idatg2003.controller.FractalDisplayController;
import no.ntnu.idatg2003.model.game.engine.ChaosCanvas;
import no.ntnu.idatg2003.model.transformations.AffineTransform2D;
import no.ntnu.idatg2003.model.transformations.JuliaTransform;
import no.ntnu.idatg2003.model.transformations.Transform2D;
import javafx.scene.control.ProgressBar;

import no.ntnu.idatg2003.model.game.engine.ChaosGameObserver;
import no.ntnu.idatg2003.utility.logging.LoggerUtil;
import no.ntnu.idatg2003.utility.enums.TransformType;

/**
 * The view for the PresetGame Page.
 * <p>
 *    This class is responsible for displaying the game view for the preset game.
 * </p>
 */
public class FractalDisplay implements ChaosGameObserver { //TODO: BUILDER PATTERN for å vise fraktalenes verdier

  private final FractalDisplayController controller;
  private final  Canvas canvas;
  private final TableView<Transform2D> transformTable;
  private final VBox juliaDetailsBox;
  private final Label realPartLabel;
  private final Label imaginaryPartLabel;
  private final ProgressBar progressBar;

  /**
   * Constructor for the PresetGameView class.
   * <p>
   *   Initializes the view with the given controller and initializes the canvas.
   * </p>
   *
   * @param controller The controller for the view.
   */
  public FractalDisplay(FractalDisplayController controller) {
    this.controller = controller;
    this.canvas = new Canvas(800, 800);
    this.transformTable = createTransformTable();
    this.realPartLabel = new Label();
    this.imaginaryPartLabel = new Label();
    this.juliaDetailsBox = createJuliaDetailsBox();

    transformTable.setVisible(false);
    juliaDetailsBox.setVisible(false);
    setupJuliaDetailsLayout();
    this.progressBar = new ProgressBar();
  }

  /**
   * Returns the scene for the view.
   *
   * @return The scene for the view.
   */
  public Scene getScene() {
    BorderPane content = createContent();
    content.setPadding(new Insets(15, 20, 15, 20));
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
    iterationsField.setMaxSize(100,10);

    Button runButton = new Button("Run");
    Label infoLabel = new Label("Values for this transformation:");
    //Checkmark label
    Label check = new Label("✓");
    check.setTextFill(Color.GREEN);
    check.setVisible(false);
    HBox progressBox = new HBox(progressBar, check);
    progressBar.setProgress(0);
    runButton.setOnAction(e -> {
      progressBar.setProgress(0);
      int iterationsValue = Integer.parseInt(iterationsField.getText());
      controller.runGame(iterationsValue);
      check.setVisible(true);
    });
    content.getChildren().addAll(backButton, iterationsText, iterationsField,
        progressBox, runButton, infoLabel, juliaDetailsBox, transformTable);
    content.setPadding(new Insets(10));
    return content;
  }

  /**
   * Updates the table items with the transformations from the controller.
   */
  public void updateTableItems() {
    transformTable.setItems(controller.getTransformations());
    transformTable.refresh();
  }

  /**
   * Creates the box for the Julia transformation details.
   *
   * @return <code>VBox</code> The box with the Julia transformation details.
   */
  private VBox createJuliaDetailsBox() {
    VBox box = new VBox(10);
    box.setPadding(new Insets(10));
    Label header = new Label("Julia Transformation Details");
    header.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-underline: true;");
    box.getChildren().add(header);

    GridPane grid = new GridPane();
    grid.setVgap(4);
    grid.setHgap(10);
    grid.add(new Label("Real Part:"), 0, 0);
    grid.add(realPartLabel, 1, 0);
    grid.add(new Label("Imaginary Part:"), 0, 1);
    grid.add(imaginaryPartLabel, 1, 1);

    box.getChildren().add(grid);
    return box;
  }
  /**
   * Initiates the table for the transformations from the controller.
   *
   * @return TableView<Transform2D> The table with the transformations.
   */

  private TableView<Transform2D> createTransformTable() {
    TableView<Transform2D> table = new TableView<>();
    setTransformTable(table);
    table.setItems(controller.getTransformations());
    return table;
  }

  private void setTransformTable(TableView<Transform2D> table) {
    TableColumn<Transform2D, Number> a00Column = new TableColumn<>("a00");
    TableColumn<Transform2D, Number> a01Column = new TableColumn<>("a01");
    TableColumn<Transform2D, Number> a10Column = new TableColumn<>("a10");
    TableColumn<Transform2D, Number> a11Column = new TableColumn<>("a11");
    TableColumn<Transform2D, Number> b0Column = new TableColumn<>("b0");
    TableColumn<Transform2D, Number> b1Column = new TableColumn<>("b1");

    // Add columns to table
    table.getColumns().addAll(a00Column, a01Column, a10Column, a11Column, b0Column, b1Column);

    // Pass the table and columns to the controller for configuration
    controller.configureTransformTable(a00Column, a01Column, a10Column, a11Column, b0Column, b1Column);

    //table.setItems(controller.getTransformations());
  }


  /**
   * Sets up the layout for the Julia transformation details.
   */
  private void setupJuliaDetailsLayout() {
    realPartLabel.setStyle("-fx-font-size: 14px;");
    imaginaryPartLabel.setStyle("-fx-font-size: 14px;");
  }

  /**
   * Updates the Julia details in the view details, and sets those values to the  labels assigned
   * to the real and imaginary parts.
   */
  private void updateJuliaDetails() {
    Transform2D latestJulia = controller.getTransformations().stream()
        .filter(t -> t instanceof JuliaTransform)
        .findFirst().orElse(null);

    if (latestJulia != null) {
      JuliaTransform julia = (JuliaTransform) latestJulia;
      realPartLabel.setText(String.format("%.2f", julia.getComplexConstant().getX0()));
      imaginaryPartLabel.setText(String.format("%.2f", julia.getComplexConstant().getX1()));
    }
  }

  /**
   * Updates the view for the game type. Checks if type is Julia, and sets the visibility of the
   * table and Julia details box accordingly. If not, the transform table is shown.
   *
   * @param type The type of the game
   */
  public void updateForGameType(TransformType type) {
    switch (type) {
      case JULIA:
        updateJuliaDetails();
        juliaDetailsBox.setVisible(true);
        break;
      case AFFINE2D:
        transformTable.refresh();
        transformTable.setVisible(true);
        break;
      default:
        LoggerUtil.logError("Invalid game type");
    }
  }

  /**
   * Draws the given ChaosCanvas on the canvas.
   *
   * @param canvasArray The ChaosCanvas to draw.
   */
  private void drawCanvas(int[][] canvasArray) {
    int width = canvasArray[0].length;
    int height = canvasArray.length;

    WritableImage image = new WritableImage(width, height);
    PixelWriter pixelWriter = image.getPixelWriter();

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        Color color = controller.calculateColor(canvasArray[y][x]);
        pixelWriter.setColor(x, y, color);
      }
    }

    GraphicsContext gc = canvas.getGraphicsContext2D();
    gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); // Clear existing content
    gc.drawImage(image, 0, 0);
  }



  /**
   * Updates the view.
   */
  @Override
  public void update() {
    controller.updateCanvas();
  }

  /**
   * Method to update the draw process of canvas array
   *
   * @param canvasArray the canvas array
   */
  public void updateCanvas(int[][] canvasArray) {
    Platform.runLater(() -> drawCanvas(canvasArray));
  }

  /**
   * Method to upgrade the progress bar
   *
   * @param progress progress of the transformations
   */
  public void updateProgressBar(int progress) {
    progressBar.setProgress(progress / 100.0);
  }

}
