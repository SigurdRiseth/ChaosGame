package no.ntnu.idatg2003;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import no.ntnu.idatg2003.game_engine.ChaosCanvas;
import no.ntnu.idatg2003.game_engine.ChaosGame;
import no.ntnu.idatg2003.game_engine.ChaosGameDescription;
import no.ntnu.idatg2003.game_engine.ChaosGameDescriptionFactory;
import no.ntnu.idatg2003.game_engine.ChaosGameObserver;
import java.io.IOException;
import java.io.InputStream;
import java.nio.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.application.*;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.*;



public class GUI extends Application implements ChaosGameObserver {

  private ChaosGame game;
  private ImageView imageView;
  private Stage primaryStage; // Assume this is initialized appropriately

  public static void appMain(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    this.primaryStage = primaryStage;
    BorderPane borderPane = new BorderPane();
    primaryStage.setTitle("Chaos Game");

    MenuBox menu = new MenuBox(
        new MenuItem("Sierpinski Triangle", () -> loadGame("Sierpinski")),
        new MenuItem("Barnsley Fern", () -> loadGame("Barnsley")),
        new MenuItem("Julia Set", () -> loadGame("Julia"))
    );


    try(InputStream is = Files.newInputStream(Paths.get("src/main/resources/CoolFractalArt.jpeg"))){
      ImageView img = new ImageView(new Image(is));
      img.setPreserveRatio(true);


      borderPane.getChildren().add(img);
    }
    catch(IOException e) {
      System.out.println("Couldn't load image");
    }

    Title title = new Title("C H A O S   G A M E");
    title.setPadding(new Insets(50,50,50,50));

    menu.setAlignment(Pos.CENTER);
    borderPane.setCenter(menu);
    borderPane.setTop(title);
    Scene scene = new Scene(borderPane, 1200, 900);
    scene.setFill(Color.BEIGE);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  private void loadGame(String gameType) {
    Scene gameScene = null;
    switch (gameType) {
      case "Sierpinski":
        gameScene = new Scene(createSierpinskiContent(), 800, 600);
        break;
      case "Barnsley":
        gameScene = new Scene(createBarnsleyContent(), 800, 600);
        break;
      case "Julia":
        gameScene = new Scene(createJuliaContent(), 800, 600);
        break;
      default:
        System.out.println("Game type not recognized");
        return;
    }
    primaryStage.setScene(gameScene);
    primaryStage.show();
  }

  private Parent createSierpinskiContent() {
    // Example: create a layout and add components specific to the Sierpinski game
    VBox layout = new VBox(10); // Just an example layout
    Label label = new Label("Sierpinski Triangle");
    layout.getChildren().add(label);
    return layout;
  }

  private Parent createJuliaContent() {
    // Example: create a layout and add components specific to the Sierpinski game
    VBox layout = new VBox(10); // Just an example layout
    Label label = new Label("Julia Set");
    layout.getChildren().add(label);
    return layout;
  }

  private Parent createBarnsleyContent() {
    // Example: create a layout and add components specific to the Sierpinski game
    VBox layout = new VBox(10); // Just an example layout
    Label label = new Label("Barnsley Fern");
    layout.getChildren().add(label);
    return layout;
  }



  private void extracted(Stage primaryStage) {
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

  private static class MenuBox extends VBox {
    public MenuBox(MenuItem...items) {
      getChildren().add(createSeperator());

      for(MenuItem item : items) {
        getChildren().addAll(item, createSeperator());
      }
    }

    private Line createSeperator() {
      Line sep = new Line();
      sep.setEndX(210);
      sep.setStroke(Color.DARKGREY);
      return sep;
    }

  }


  private static class MenuItem extends StackPane {
    public MenuItem(String name, Runnable action) {
      LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE,
          new Stop(0, Color.DARKBLUE),
          new Stop(0.1, Color.BLACK),
          new Stop(0.9, Color.BLACK),
          new Stop(1, Color.DARKBLUE));

      Rectangle bg = new Rectangle(350, 50);
      bg.setOpacity(0.4);

      Text text = new Text(name);
      text.setFill(Color.LIGHTGOLDENRODYELLOW);
      text.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 40));

      setAlignment(Pos.CENTER);
      getChildren().addAll(bg, text);
      setupEventHandling(gradient, bg, text, action);
    }

    private void setupEventHandling(LinearGradient gradient, Rectangle bg, Text text, Runnable action) {
      setOnMouseEntered(event -> {
        bg.setFill(gradient);
        text.setFill(Color.WHITE);
      });

      setOnMouseExited(event -> {
        bg.setFill(Color.BLACK);
        text.setFill(Color.LIGHTGOLDENRODYELLOW);
      });
      setOnMousePressed(event -> bg.setFill(Color.DARKBLUE));
      setOnMouseReleased(event -> {
        bg.setFill(gradient);
        action.run(); // Execute the given action
      });
    }
  }



  private static class Title extends StackPane{
    public Title(String name) {
      Rectangle bg = new Rectangle(700, 80);
      bg.setStroke(Color.WHITE);
      bg.setStrokeWidth(2);
      bg.setFill(null);

      Text text = new Text(name);
      text.setFill(Color.WHITE);
      text.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 75));

      setAlignment(Pos.CENTER);
      getChildren().addAll(bg,text);
    }
  }


}



