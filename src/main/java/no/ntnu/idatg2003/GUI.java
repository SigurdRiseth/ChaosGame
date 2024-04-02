package no.ntnu.idatg2003;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import no.ntnu.idatg2003.game_engine.ChaosCanvas;
import no.ntnu.idatg2003.game_engine.ChaosGame;
import no.ntnu.idatg2003.game_engine.ChaosGameDescription;
import no.ntnu.idatg2003.game_engine.ChaosGameFileHandler;

public class GUI extends Application {

  public void appMain(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {

    ChaosGameDescription description =
        ChaosGameFileHandler.readFromFile("src/main/resources/Sierpinski.csv");

    ChaosGame game = new ChaosGame(description, 1000, 1000);
    game.runSteps(1000000);

    WritableImage image = drawCanvasToImage(game.getCanvas());

    ImageView imageView = new ImageView(image); // Create an ImageView and set the image

    BorderPane borderPane = new BorderPane();
    borderPane.setCenter(imageView); // Add the ImageView to the center of the BorderPane

    Scene scene = new Scene(borderPane, 800, 600);
    primaryStage.setScene(scene);
    primaryStage.setTitle("Chaos Game");
    primaryStage.show(); // Show the primaryStage with the scene containing the BorderPane
  }

  public WritableImage drawCanvasToImage(ChaosCanvas chaosCanvas) {
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

  @Override
  public void stop() {
    System.exit(0);
  }
}
