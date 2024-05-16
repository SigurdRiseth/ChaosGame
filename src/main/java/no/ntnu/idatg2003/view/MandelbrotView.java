package no.ntnu.idatg2003.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import no.ntnu.idatg2003.controller.MandelbrotController;
import no.ntnu.idatg2003.model.game.engine.ChaosCanvas;
import javafx.scene.paint.Color;

public class MandelbrotView {

  private MandelbrotController mandelbrotController;
  private Canvas canvas;

  public MandelbrotView(MandelbrotController mandelbrotController) {
    this.mandelbrotController = mandelbrotController;
  }

  public Scene getScene() {
    BorderPane content = createContent();
    content.setPadding(new Insets(15, 20, 15, 20));
    return new Scene(content, 1400, 900);  }

  private BorderPane createContent() {
    BorderPane content = new BorderPane();
    canvas = new Canvas(1000, 1000);
    drawMandelbrot(mandelbrotController.getCanvas());
    VBox leftPanel = createLeftPanel();

    content.setLeft(leftPanel);
    content.setCenter(canvas);
    return content;
  }

  private VBox createLeftPanel() {
    // Return button
    Button returnButton = new Button("Return to Front Page");
    returnButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
    returnButton.setOnAction(e -> mandelbrotController.returnToFrontPage());

    // Title
    Text title = new Text("Mandelbrot");
    title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
    title.setFill(Color.WHITE);

    // Information about Mandelbrot set
    Text info = new Text("The Mandelbrot set is a complex set of points in the complex plane. " +
        "It is visualized here in red. The set is defined by the behavior of the " +
        "iterative function z = z^2 + c, where z starts at zero and c is a complex parameter. " +
        "Points inside the Mandelbrot set do not escape to infinity, producing a fractal shape. " +
        "You can click on the Mandelbrot set to draw the corresponding Julia set.");
    info.setFont(Font.font("Arial", 14));
    info.setFill(Color.WHITE);
    info.setWrappingWidth(200);

    // Left panel VBox
    VBox leftPanel = new VBox(20, returnButton, title, info);
    leftPanel.setAlignment(Pos.CENTER_LEFT);
    leftPanel.setPadding(new Insets(20));
    leftPanel.setBackground(new Background(new BackgroundFill(Color.rgb(52, 73, 94), CornerRadii.EMPTY, Insets.EMPTY)));
    leftPanel.setMaxWidth(250);

    return leftPanel;
  }

  public void drawMandelbrot(ChaosCanvas canvas) {
    int[][] canvasArray = canvas.getCanvasArray();
    int width = canvasArray[0].length;
    int height = canvasArray.length;

    GraphicsContext gc = this.canvas.getGraphicsContext2D();

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        int value = canvasArray[y][x];
        Color color = calulateColor(value);
        gc.setFill(color);
        gc.fillRect(x, y, 1, 1);
      }
    }
  }

  private Color calulateColor(int hits) {
    int maxHits = 100;
    if (hits == 0) return Color.WHITE;

    double normalizedHits = Math.min(hits / (double) maxHits, 1.0);

    double red = normalizedHits;  // Increases with more hits
    double blue = 1 - normalizedHits;

    return Color.color(red, 0, blue); // Green is left out.
  }

}