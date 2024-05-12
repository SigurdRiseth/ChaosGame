package no.ntnu.idatg2003.view;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import no.ntnu.idatg2003.controller.CreateCustomGameController;
import no.ntnu.idatg2003.model.math.datatypes.Complex;
import no.ntnu.idatg2003.model.math.datatypes.Vector2D;

/**
 * The CreateCustomGame class is a view class that creates the view for the Create Custom Game
 * scene.
 * <p>
 * The CreateCustomGame class creates the view for the Create Custom Game scene. The scene contains
 * text fields for the user to input parameters for a Julia Set and an Affine Transformation. The
 * user can also save the parameters with a name.
 * </p>
 *
 * @author Sigurd Riseth
 * @version 1.0
 * @see CreateCustomGameController
 * @since 11.05.2024
 */
public class CreateCustomGame {

  private final CreateCustomGameController controller;
  private GridPane juliaGrid;
  private GridPane affineGrid;

  /**
   * Constructor for the CreateCustomGame class.
   *
   * @param controller The controller for the CreateCustomGame view.
   */
  public CreateCustomGame(CreateCustomGameController controller) {
    this.controller = controller;
  }

  /**
   * Returns the scene for the view.
   *
   * @return The scene for the view.
   */
  public Scene getScene() {
    BorderPane content = createContent();
    content.setPadding(new Insets(15, 20, 15, 20));

    // Make the content scrollable vertically
    ScrollPane scrollPane = new ScrollPane(content);
    scrollPane.setFitToWidth(true);
    scrollPane.setFitToHeight(true);

    return new Scene(scrollPane, 800, 600);
  }

  /**
   * Creates the content for the Create Custom Game scene.
   *
   * @return The content for the Create Custom Game scene.
   */
  private BorderPane createContent() {
    BorderPane content = new BorderPane();

    // Back button
    Button backButton = new Button("Return");
    backButton.setOnAction(e -> controller.returnToFrontPage());

    // Create layout
    VBox vbox = new VBox(10);
    vbox.setPadding(new Insets(10));

    // Julia Set Parameters
    juliaGrid = new GridPane();
    juliaGrid.setVgap(5);
    juliaGrid.setHgap(5);
    addMinMaxToGridPane(juliaGrid);
    juliaGrid.addRow(4, new Label("Real part of C:"), new TextField());
    juliaGrid.addRow(5, new Label("Imaginary part of C:"), new TextField());
    juliaGrid.setAlignment(javafx.geometry.Pos.CENTER);

    // Affine Transformation Parameters
    affineGrid = new GridPane();
    affineGrid.setVgap(5);
    affineGrid.setHgap(5);
    addMinMaxToGridPane(affineGrid);
    affineGrid.addRow(4, matrixField(), vectorField());
    affineGrid.setAlignment(javafx.geometry.Pos.CENTER);

    // Set text color to white
    changeNodeColor(juliaGrid);
    changeNodeColor(affineGrid);

    // Limit user inputs to Doubles
    limitInputsToDouble(juliaGrid);
    limitInputsToDouble(affineGrid);

    // Button to save parameters
    HBox juliaBox = createSaveBox("Save Julia-Set", controller::saveJuliaSet);

    HBox affineBox = createSaveBox("Save Affine-Transformation",
        controller::saveAffineTransformation);

    // Button to add a transformation
    Button addTransformationButton = new Button("Add Transformation");
    addTransformationButton.setOnAction(e -> affineGrid.addRow(
        affineGrid.getRowCount(), matrixField(), vectorField()));

    // Titles
    Text juliaTitle = createTitle("Julia-Set Parameters");
    Text affineTitle = createTitle("Affine Transformation Parameters");

    // Add components to layout
    vbox.getChildren().addAll(
        juliaTitle, juliaGrid, juliaBox,
        affineTitle, affineGrid, addTransformationButton, affineBox);
    vbox.setAlignment(javafx.geometry.Pos.CENTER);

    // Set the content
    content.setTop(backButton);
    content.setCenter(vbox);
    content.setStyle("-fx-background-color: #3b1d5a;");

    return content;
  }

  private void limitInputsToDouble(GridPane grid) {
    for (Node node : grid.getChildren()) {
      if (node instanceof TextField textField) {
        textFieldLimitToDouble(textField);
      }
    }
  }

  private static void textFieldLimitToDouble(TextField textField) {
    textField.textProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue.matches("-?\\d{0,7}([.]\\d{0,4})?")) {
        textField.setText(oldValue);
      }
    });
  }

  /**
   * Creates a save box with a text field and a button.
   *
   * @param saveText The text to display on the button.
   * @param saveAction The action to perform when the button is clicked.
   * @return The save box.
   */
  private static HBox createSaveBox(String saveText, Consumer<String> saveAction) {
    TextField inputField = new TextField();
    inputField.setPromptText("Insert name");
    Button saveButton = new Button(saveText);
    HBox juliaBox = new HBox(10);
    saveButton.setOnAction(e -> saveAction.accept(inputField.getText()));

    juliaBox.getChildren().addAll(inputField, saveButton);
    juliaBox.setAlignment(javafx.geometry.Pos.CENTER);
    return juliaBox;
  }

  /**
   * Creates a title for a given text.
   *
   * @param title The text to create a title for.
   * @return The formatted title.
   */
  private static Text createTitle(String title) {
    Text juliaTitle = new Text(title);
    juliaTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
    juliaTitle.setFill(Color.WHITE);
    return juliaTitle;
  }

  /**
   * Changes the text color of the nodes in a given <code>GridPane</code> to white.
   *
   * @param grid The <code>GridPane</code> to change the text color of.
   */
  private static void changeNodeColor(GridPane grid) {
    for (Node node : grid.getChildren()) {
      if (node instanceof Label label) {
        label.setTextFill(Color.WHITE);
      }
    }
  }

  /**
   * Adds the min and max values to a given <code>GridPane</code>.
   *
   * @param grid The <code>GridPane</code> to add the values to.
   */
  private static void addMinMaxToGridPane(GridPane grid) {
    grid.addRow(0, new Label("Min X:"), new TextField());
    grid.addRow(1, new Label("Min Y:"), new TextField());
    grid.addRow(2, new Label("Max X:"), new TextField());
    grid.addRow(3, new Label("Max Y:"), new TextField());
  }

  /**
   * Creates a vector field for the affine transformation.
   *
   * @return The vector field.
   */
  private Node vectorField() {
    TextField xField = new TextField();
    xField.setPromptText("x");

    TextField yField = new TextField();
    yField.setPromptText("y");

    xField.setMaxWidth(50);
    yField.setMaxWidth(50);

    textFieldLimitToDouble(xField);
    textFieldLimitToDouble(yField);

    VBox vbox = new VBox(5);
    vbox.getChildren().addAll(xField, yField);
    return vbox;
  }

  /**
   * Creates a matrix field for the affine transformation.
   *
   * @return The matrix field.
   */
  private static GridPane matrixField() {
    GridPane gridPane = new GridPane();
    gridPane.setVgap(5);
    gridPane.setHgap(5);
    gridPane.addRow(0, matrixTextField("a00"));
    gridPane.addRow(1, matrixTextField("a01"));
    gridPane.addColumn(1, matrixTextField("a10"));
    gridPane.addColumn(1, matrixTextField("a11"));

    return gridPane;
  }

  /**
   * Creates a <code>TextField</code> for the matrix.
   *
   * @param promptText The text to display in the text field.
   * @return The <code>TextField</code> with the given prompt text.
   */
  private static TextField matrixTextField(String promptText) {
    TextField textField = new TextField();
    textField.setPromptText(promptText);
    textField.setMaxWidth(50);
    textFieldLimitToDouble(textField);
    return textField;
  }

  public Vector2D getMinCoords() {
    AtomicReference<Double> x0 = new AtomicReference<>((double) 0);
    AtomicReference<Double> x1 = new AtomicReference<>((double) 0);
    juliaGrid.getChildren().forEach(node -> {
      if (node instanceof TextField textField) {
        if (GridPane.getRowIndex(node) == 0 && GridPane.getColumnIndex(node) == 1) {
          x0.set(Double.parseDouble(textField.getText()));
        } else if (GridPane.getRowIndex(node) == 1 && GridPane.getColumnIndex(node) == 1) {
          x1.set(Double.parseDouble(textField.getText()));
        }
      }
    });
    return new Vector2D(x0.get(), x1.get());
  }

  public Vector2D getMaxCoords() {
    AtomicReference<Double> x0 = new AtomicReference<>((double) 0);
    AtomicReference<Double> x1 = new AtomicReference<>((double) 0);
    juliaGrid.getChildren().forEach(node -> {
      if (node instanceof TextField textField) {
        if (GridPane.getRowIndex(node) == 2 && GridPane.getColumnIndex(node) == 1) {
          x0.set(Double.parseDouble(textField.getText()));
        } else if (GridPane.getRowIndex(node) == 3 && GridPane.getColumnIndex(node) == 1) {
          x1.set(Double.parseDouble(textField.getText()));
        }
      }
    });
    return new Vector2D(x0.get(), x1.get());
  }

  public Complex getComplexNumber() {
    AtomicReference<Double> real = new AtomicReference<>((double) 0);
    AtomicReference<Double> imaginary = new AtomicReference<>((double) 0);
    juliaGrid.getChildren().forEach(node -> {
      if (node instanceof TextField textField) {
        if (GridPane.getRowIndex(node) == 4 && GridPane.getColumnIndex(node) == 1) {
          real.set(Double.parseDouble(textField.getText()));
        } else if (GridPane.getRowIndex(node) == 5 && GridPane.getColumnIndex(node) == 1) {
          imaginary.set(Double.parseDouble(textField.getText()));
        }
      }
    });
    return new Complex(real.get(), imaginary.get());
  }
}
