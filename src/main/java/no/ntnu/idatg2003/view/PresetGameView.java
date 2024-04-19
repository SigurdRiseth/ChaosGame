package no.ntnu.idatg2003.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import no.ntnu.idatg2003.controller.PresetGameController;
import no.ntnu.idatg2003.model.game_engine.ChaosCanvas;

public class PresetGameView {

  private PresetGameController controller;
  private ImageView canvas;
  private Canvas canvas2;

  public PresetGameView(PresetGameController controller) {
    this.controller = controller;
    initCanvas();
  }

  public Scene getScene() {
    BorderPane content = createContent();
    content.setPadding(new Insets(15, 20, 15, 20));
    return new Scene(content, 800, 600);
  }

  private BorderPane createContent() {
    BorderPane content = new BorderPane();

    VBox leftPanel = createLeftPanel();
    content.setLeft(leftPanel);
    BorderPane.setMargin(leftPanel, new Insets(0, 20, 0, 0));

    StackPane canvasContainer = new StackPane();
    canvasContainer.getChildren().add(canvas2);
    canvasContainer.setStyle("-fx-border-color: black; -fx-border-width: 2;");
    content.setRight(canvasContainer);

    return content;
  }

  private VBox createLeftPanel() {
    Button backButton = new Button("Return");
    backButton.setOnAction(e -> {
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
    content.getChildren().addAll(backButton, iterationsText, iterationsField, runButton, infoLabel, minMax);
    content.setPadding(new Insets(10));
    return content;
  }

  private HBox createMinMaxBox() {
    HBox minMax = new HBox(10);
    Label minLabel = new Label("Min: ");
    Label maxLabel = new Label("Max: ");
    TextField minField = new TextField("(-2.65, 0.0)");
    minField.setEditable(false);
    TextField maxField = new TextField("(2.65, 10)");
    maxField.setEditable(false);
    minMax.getChildren().addAll(minLabel, minField, maxLabel, maxField);
    return minMax;
  }

  private void initCanvas() {
    canvas2 = new Canvas(800, 800);
    //canvas = new ImageView();
    //canvas.setPreserveRatio(true);
    //canvas.setFitHeight(500);
  }

  public void updateCanvas() {
    //WritableImage image = drawCanvasToImage(controller.getCanvas());
    //canvas.setImage(image);

    drawCanvas(controller.getCanvas());
  }

  private void drawCanvas(ChaosCanvas chaosCanvas) {
    int[][] canvasArray = chaosCanvas.getCanvasArray();
    int width = canvasArray[0].length;
    int height = canvasArray.length;

    GraphicsContext gc = canvas2.getGraphicsContext2D();
    gc.clearRect(0, 0, canvas2.getWidth(), canvas2.getHeight()); // Clear existing content

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        int pixelValue = canvasArray[y][x];
        Color color = (pixelValue == 1) ? Color.BLACK : Color.WHITE;
        gc.setFill(color);
        gc.fillRect(x, y, 1, 1);
      }
    }
  }

  private WritableImage drawCanvasToImage(ChaosCanvas chaosCanvas) {
    int[][] canvasArray = chaosCanvas.getCanvasArray();
    int width = canvasArray[0].length;
    int height = canvasArray.length;

    WritableImage writableImage = new WritableImage(width, height);
    PixelWriter pixelWriter = writableImage.getPixelWriter();

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int pixelValue = canvasArray[y][x];
        Color color = (pixelValue == 1) ? Color.BLACK : Color.WHITE;
        pixelWriter.setColor(x, y, color);
      }
    }
    return writableImage;
  }

}
