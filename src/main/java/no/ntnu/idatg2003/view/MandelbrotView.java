package no.ntnu.idatg2003.view;

import java.util.Optional;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import no.ntnu.idatg2003.controller.MandelbrotController;
import no.ntnu.idatg2003.model.game.engine.ChaosCanvas;
import no.ntnu.idatg2003.model.game.engine.ChaosGameObserver;

public class MandelbrotView implements ChaosGameObserver {

  private static final double MIN_SCALE = 1.0;
  private static final double MAX_SCALE = 5.0;
  private final MandelbrotController mandelbrotController;
  private Canvas canvas;
  private double scale = 1.0;

  public MandelbrotView(MandelbrotController mandelbrotController) {
    this.mandelbrotController = mandelbrotController;
  }

  public Scene getScene() {
    BorderPane content = createContent();
    content.setPadding(new Insets(15, 20, 15, 20));
    return new Scene(content, 1400, 900);
  }

  private BorderPane createContent() {
    BorderPane content = new BorderPane();

    // Initialize the canvas and set its dimensions
    canvas = new Canvas(1000, 1000);
    StackPane canvasContainer = new StackPane(canvas);

    // Add zoom functionality
    canvasContainer.setOnScroll(this::handleZoom);

    // Set the mouse click event for the canvas
    canvas.setOnMouseClicked(e -> {
      double re = (e.getX() - 500) / 250;
      double im = -1 * ((e.getY() - 500) / 250);

      Alert alert = createJuliaAlert(re, im);

      Optional<ButtonType> result = alert.showAndWait();
      if (result.isPresent() && result.get() == ButtonType.OK) {
        mandelbrotController.drawJuliaSet(re, im);
      }
    });

    // Create the left panel with the necessary components
    VBox leftPanel = createLeftPanel();

    // Set the left panel and the scroll pane containing the canvas
    content.setLeft(leftPanel);
    content.setCenter(canvasContainer);

    return content;
  }

  private Alert createJuliaAlert(double re, double im) {
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setTitle("Confirmation");
    alert.setHeaderText("Draw Julia Set");
    alert.setContentText("Are you sure you want to draw the Julia Set at this location?"
        + "\n" + "(Re = " + re + ", Im = " + im + ")");
    alert.initOwner(canvas.getScene().getWindow());
    return alert;
  }

  private VBox createLeftPanel() {
    // Return button
    Button returnButton = new Button("Return to Front Page");
    returnButton.setOnAction(e -> mandelbrotController.returnToFrontPage());

    // Title
    Text title = new Text("Mandelbrot");
    title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
    title.setFill(Color.WHITE);

    // Information about Mandelbrot set
    Text info = new Text("The Mandelbrot set is a complex set of points in the complex plane. " +
        "It is visualized here in white. The set is defined by the behavior of the " +
        "iterative function z = z^2 + c, where z starts at zero and c is a complex parameter. " +
        "Points inside the Mandelbrot set do not escape to infinity, producing a fractal shape. " +
        "You can click on the Mandelbrot set to draw the corresponding Julia set.");
    info.setFont(Font.font("Arial", 14));
    info.setFill(Color.WHITE);
    info.setWrappingWidth(200);

    // Left panel VBox
    VBox leftPanel = new VBox(20, returnButton, title, info);
    leftPanel.setPadding(new Insets(20));
    leftPanel.setBackground(
        new Background(new BackgroundFill(Color.rgb(52, 73, 94), CornerRadii.EMPTY, Insets.EMPTY)));
    leftPanel.setMaxWidth(250);

    return leftPanel;
  }

  private void handleZoom(ScrollEvent event) {
    if (event.getDeltaY() == 0) {
      return;
    }

    double scaleFactor = (event.getDeltaY() > 0) ? 1.1 : 0.9;
    double newScale = scale * scaleFactor;

    if (newScale < MIN_SCALE || newScale > MAX_SCALE) {
      return;
    }

    scale = newScale;
    double f = (scaleFactor - 1);

    double dx = event.getX() - (canvas.getWidth() / 2);
    double dy = event.getY() - (canvas.getHeight() / 2);

    canvas.setScaleX(scale);
    canvas.setScaleY(scale);
    canvas.setTranslateX(canvas.getTranslateX() - f * dx);
    canvas.setTranslateY(canvas.getTranslateY() - f * dy);
  }

  public void drawMandelbrot(ChaosCanvas chaosCanvas) {
    int[][] canvasArray = chaosCanvas.getCanvasArray();
    int width = canvasArray[0].length;
    int height = canvasArray.length;

    // Create a writable image with the same dimensions as the canvas
    WritableImage writableImage = new WritableImage(width, height);
    PixelWriter pixelWriter = writableImage.getPixelWriter();

    // Iterate over the canvas array and write pixel data to the writable image
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int value = canvasArray[y][x];
        // Set pixel color based on the value
        Color color = Color.rgb(value, value, value);
        // Write pixel data to the writable image
        pixelWriter.setColor(x, y, color);
      }
    }

    // Draw the writable image onto the canvas
    GraphicsContext gc = this.canvas.getGraphicsContext2D();
    gc.drawImage(writableImage, 0, 0);
  }

  @Override
  public void update() {
    drawMandelbrot(mandelbrotController.getCanvas());
  }
}
