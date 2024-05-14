package no.ntnu.idatg2003.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import no.ntnu.idatg2003.controller.FractalDisplayController;
import no.ntnu.idatg2003.model.game.engine.ChaosCanvas;

/**
 * The view for the PresetGame Page.
 * <p>
 *    This class is responsible for displaying the game view for the preset game.
 * </p>
 */
public class FractalDisplay {

  private final FractalDisplayController controller;
  private Canvas canvas;

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
  }

  /**
   * Returns the scene for the view.
   *
   * @return The scene for the view.
   */
  public Scene getScene() {
    BorderPane content = createContent();
    content.setPadding(new Insets(15, 20, 15, 20));
    return new Scene(content, 800, 600);
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
        runButton, infoLabel, minMax);
    content.setPadding(new Insets(10));
    return content;
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
