package no.ntnu.idatg2003.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

  public PresetGameView(PresetGameController controller, String type) {
    this.controller = controller;
    initCanvas();
  }

  public Scene getScene() {
    BorderPane content = createContent();
    content.setPadding(new Insets(15, 20, 15, 20));  // Add padding around the layout
    return new Scene(content, 800, 600);
  }

  private BorderPane createContent() {
    BorderPane content = new BorderPane();

    VBox leftPanel = createIterationsContent();
    content.setLeft(leftPanel);
    BorderPane.setMargin(leftPanel, new Insets(0, 20, 0, 0));  // Add some margin to separate from the image

    StackPane canvasContainer = new StackPane();
    canvasContainer.getChildren().add(canvas);
    canvasContainer.setStyle("-fx-border-color: black; -fx-border-width: 2;");  // Add a border to the ImageView
    content.setRight(canvasContainer);

    return content;
  }

  private VBox createIterationsContent() {
    VBox iterations = new VBox(10);  // Add spacing between components
    Text iterationsText = new Text("Iterations: ");
    TextField iterationsField = new TextField();
    Button runButton = new Button("Run");
    runButton.setOnAction(e -> {
      int iterationsValue = Integer.parseInt(iterationsField.getText());
      controller.runGame(iterationsValue);
    });
    iterations.getChildren().addAll(iterationsText, iterationsField, runButton);
    iterations.setPadding(new Insets(10));  // Add padding inside the VBox
    return iterations;
  }

  private void initCanvas() {
    canvas = new ImageView();
    canvas.setPreserveRatio(true);
    canvas.setFitHeight(500);  // You might want to adjust the size based on your layout needs
  }

  public void updateCanvas() {
    WritableImage image = drawCanvasToImage(controller.getCanvas());
    canvas.setImage(image);
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
        Color color = (pixelValue == 1) ? Color.RED : Color.WHITE;
        pixelWriter.setColor(x, y, color);
      }
    }
    return writableImage;
  }

}
