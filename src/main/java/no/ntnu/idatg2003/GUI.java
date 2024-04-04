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
import no.ntnu.idatg2003.game_engine.ChaosGameDescriptionFactory;
import no.ntnu.idatg2003.game_engine.ChaosGameObserver;

public class GUI extends Application implements ChaosGameObserver {

  private ChaosGame game;
  private ImageView imageView;

  public static void appMain(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    ChaosGameDescription description = ChaosGameDescriptionFactory.createSierpinskiTriangle();
    game = new ChaosGame(description, 1000, 1000);
    game.registerObserver(this);
    imageView = new ImageView();
    BorderPane borderPane = new BorderPane();
    borderPane.setCenter(imageView);

    Scene scene = new Scene(borderPane, 800, 600);
    primaryStage.setScene(scene);
    primaryStage.setTitle("Chaos Game");
    primaryStage.show();
    game.runSteps(1000000);

    // Create image view
    updateImage();
  }

  @Override
  public void stop() {
    System.exit(0);
  }

  @Override
  public void update() {
    updateImage();
  }

  private void updateImage() {
    WritableImage image = drawCanvasToImage(game.getCanvas());
    imageView.setImage(image);
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
