package no.ntnu.idatg2003.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import no.ntnu.idatg2003.controller.FrontPageController;

/**
 * Class for the front page view of the application. This class is responsible for creating the
 * front page scene and its content.
 *
 * @author Theodor Sjetnan Utvik, Sigurd Riseth
 * @version 0.0.2
 */

public class FrontPage {

  private FrontPageController controller;
  private Scene scene;

  public FrontPage(FrontPageController controller) {
    this.controller = controller;
    this.scene = new Scene(createContent(), 800, 600);
  }

  public Scene getScene() {
    return scene;
  }

  private BorderPane createContent() {
    BorderPane content = new BorderPane();

    FrontPage.Title title = new FrontPage.Title("C H A O S   G A M E");
    title.setPadding(new Insets(50, 50, 50, 50));
    content.setTop(title);

    Button runGameButton = new Button("Run a Game");
    Button createGameButton = new Button("Create a Custom Game");
    Button mandelbrotButton = new Button("Mandelbrot");
    Button exitButton = new Button("Exit");

    runGameButton.setOnAction(e -> {
      controller.openRunGameScene();
    });

    createGameButton.setOnAction(e -> {
      controller.openCreateCustomGameScene();
    });

    mandelbrotButton.setOnAction(e -> {
      controller.openMandelbrotScene();
    });

    exitButton.setOnAction(e -> {
      controller.exit();
    });

    VBox vBox = new VBox(10, runGameButton, createGameButton, mandelbrotButton, exitButton);
    vBox.setAlignment(javafx.geometry.Pos.CENTER);
    content.setCenter(vBox);
    content.setStyle("-fx-background-color: #3b1d5a;");
    return content;
  }

  private static class Title extends StackPane {

    public Title(String name) {
      Rectangle bg = new Rectangle(500, 70);
      bg.setStroke(javafx.scene.paint.Color.WHITE);
      bg.setStrokeWidth(2);
      bg.setFill(null);

      Text text = new Text(name);
      text.setFill(javafx.scene.paint.Color.WHITE);
      text.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 50));

      setAlignment(javafx.geometry.Pos.CENTER);
      getChildren().addAll(bg, text);
    }
  }
}



