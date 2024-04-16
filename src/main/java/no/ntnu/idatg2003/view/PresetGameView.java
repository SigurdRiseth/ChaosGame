package no.ntnu.idatg2003.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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
  }

  public Scene getScene() {
    BorderPane content = createContent();
    return new Scene(content, 800, 600);
  }

  private BorderPane createContent() {
    BorderPane content = new BorderPane();
    HBox contentBox = new HBox();
    Text text = new Text("This is a Julia Set!");
    text.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
    initCanvas();
    VBox iterations = createIterationsContent();
    contentBox.getChildren().addAll(canvas, iterations);

    content.setTop(text);
    content.setCenter(contentBox);
    return content;
  }

  private VBox createIterationsContent() {
    VBox iterations = new VBox();
    Text iterationsText = new Text("Iterations: ");
    TextField iterationsField = new TextField();
    Button runButton = new Button("Run");
    runButton.setOnAction(e -> {
      int iterationsValue = Integer.parseInt(iterationsField.getText());
      controller.runGame(iterationsValue);
    });
    iterations.getChildren().addAll(iterationsText, iterationsField, runButton);
    return iterations;
  }

  private void initCanvas() {
    canvas = new ImageView();
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

    try {
      for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
          int pixelValue = canvasArray[y][x];
          Color color = (pixelValue == 1) ? Color.RED : Color.WHITE;
          pixelWriter.setColor(x, y, color);
        }
      }
    } catch (Exception e) {
      System.err.println("Failed to draw canvas to image: " + e.getMessage());
    }
    return writableImage;
  }
}
