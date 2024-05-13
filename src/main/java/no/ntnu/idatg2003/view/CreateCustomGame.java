package no.ntnu.idatg2003.view;

import java.util.ArrayList;
import java.util.List;
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
import no.ntnu.idatg2003.model.math.datatypes.Matrix2x2;
import no.ntnu.idatg2003.model.math.datatypes.Vector2D;
import no.ntnu.idatg2003.model.transformations.AffineTransform2D;
import no.ntnu.idatg2003.model.transformations.Transform2D;

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
    addAffineTransformsToGridPane();
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
    addTransformationButton.setOnAction(e -> addAffineTransformsToGridPane());

    // Titles
    Text juliaTitle = createTitle("Julia-Set Parameters");
    Text affineTitle = createTitle("Affine Transformation Parameters");

    // Add components to layout
    vbox.getChildren().addAll(
        juliaTitle, juliaGrid, juliaBox,
        affineTitle, affineGrid, addTransformationButton, affineBox);
    vbox.setAlignment(javafx.geometry.Pos.CENTER);

    // Set the content
    BorderPane content = new BorderPane();
    content.setTop(backButton);
    content.setCenter(vbox);
    content.setStyle("-fx-background-color: #3b1d5a;");

    return content;
  }

  private void addAffineTransformsToGridPane() {
    affineGrid.addRow(affineGrid.getRowCount(), createInputField("a00"),
        createInputField("a01"), createInputField("x"));
    affineGrid.addRow(affineGrid.getRowCount(), createInputField("a10"),
        createInputField("a11"), createInputField("y"));
  }

  /**
   * Creates and configures a TextField node for input with the specified prompt text.
   *
   * @param promptText The text to be displayed as a prompt inside the TextField.
   * @return The configured TextField node.
   */
  private Node createInputField(String promptText) {
    TextField textField = new TextField();
    textField.setPromptText(promptText);
    textField.setMaxWidth(50);
    limitTextFieldToDouble(textField);
    return textField;
  }

  /**
   * Limits the input of text fields within the specified grid to double values only.
   *
   * @param grid The GridPane containing the text fields to be limited.
   */
  private void limitInputsToDouble(GridPane grid) {
    for (Node node : grid.getChildren()) {
      if (node instanceof TextField textField) {
        limitTextFieldToDouble(textField);
      }
    }
  }

  /**
   * Limits the text input of a TextField to double values only.
   *
   * @param textField The TextField to limit to double values.
   */
  private static void limitTextFieldToDouble(TextField textField) {
    textField.textProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue.matches("-?\\d{0,7}([.]\\d{0,4})?")) {
        textField.setText(oldValue);
      }
    });
  }

  /**
   * Creates a save box with a text field and a button.
   *
   * @param saveText    The text to display on the button.
   * @param saveAction  The action to perform when the button is clicked.
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
    Text resultingTitle = new Text(title);
    resultingTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
    resultingTitle.setFill(Color.WHITE);
    return resultingTitle;
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
   * @param grid          The <code>GridPane</code> to add the values to.
   */
  private static void addMinMaxToGridPane(GridPane grid) {
    grid.addRow(0, new Label("Min X:"), new TextField());
    grid.addRow(1, new Label("Min Y:"), new TextField());
    grid.addRow(2, new Label("Max X:"), new TextField());
    grid.addRow(3, new Label("Max Y:"), new TextField());
  }

  /**
   * Retrieves the coordinates from the specified grid within the given row index range.
   *
   * <p>
   *   The <code>type</code> parameter specifies the which grid to retrieve the coordinates from.
   * </p>
   *
   * @param type          The type of grid ("julia" or "affine").
   * @param rowIndexStart The starting row index.
   * @param rowIndexEnd   The ending row index.
   * @return The coordinates as a Vector2D.
   */
  public Vector2D getCoords(String type, int rowIndexStart, int rowIndexEnd) {
    GridPane grid = type.equals("julia") ? juliaGrid : affineGrid;

    AtomicReference<Double> x0 = new AtomicReference<>(0.0);
    AtomicReference<Double> x1 = new AtomicReference<>(0.0);

    grid.getChildren().forEach(node -> {
      if (node instanceof TextField textField) {
        int rowIndex = GridPane.getRowIndex(node);
        int colIndex = GridPane.getColumnIndex(node);
        if (rowIndex >= rowIndexStart && rowIndex <= rowIndexEnd && colIndex == 1) {
          if (rowIndex % 2 == 0) {
            x0.set(Double.parseDouble(textField.getText()));
          } else {
            x1.set(Double.parseDouble(textField.getText()));
          }
        }
      }
    });

    return new Vector2D(x0.get(), x1.get());
  }

  /**
   * Retrieves the complex number from the julia grid.
   *
   * @return The complex number as a Complex object.
   */
  public Complex getComplexNumber() {
    AtomicReference<Double> real = new AtomicReference<>(0.0);
    AtomicReference<Double> imaginary = new AtomicReference<>(0.0);

    juliaGrid.getChildren().forEach(node -> {
      if (node instanceof TextField textField) {
        int rowIndex = GridPane.getRowIndex(node);
        int colIndex = GridPane.getColumnIndex(node);
        if (rowIndex == 4 && colIndex == 1) {
          real.set(Double.parseDouble(textField.getText()));
        } else if (rowIndex == 5 && colIndex == 1) {
          imaginary.set(Double.parseDouble(textField.getText()));
        }
      }
    });

    return new Complex(real.get(), imaginary.get());
  }


  /**
   * Retrieves a list of affine transformations from the affine grid.
   *
   * @return The list of affine transformations.
   */
  public List<Transform2D> getAffineTransforms() {
    List<Transform2D> transforms = new ArrayList<>();

    int amountOfTransforms = calculateAmountOfTransforms();

    for (int i = 0; i < amountOfTransforms; i++) {
      Matrix2x2 matrix = extractMatrixFromGrid(i);
      Vector2D vector = extractVectorFromGrid(i);
      transforms.add(new AffineTransform2D(matrix, vector));
    }

    return transforms;
  }

  /**
   * Calculates the amount of affine transformations based on the number of children in the affine grid.
   *
   * @return The number of affine transformations.
   */
  private int calculateAmountOfTransforms() {
    int size = affineGrid.getChildren().size();
    return (size - 8) / 6;
  }

  /**
   * Extracts the matrix components of an affine transformation from the affine grid.
   *
   * <p> The index counts from 0 and up starting at first transform. </p>
   *
   * @param index The index of the affine transformation.
   * @return The matrix components.
   */
  private Matrix2x2 extractMatrixFromGrid(int index) {
    int startIndex = 8 + index * 6;
    double a00 = getDoubleFromTextField(startIndex);
    double a01 = getDoubleFromTextField(startIndex + 1);
    double a10 = getDoubleFromTextField(startIndex + 3);
    double a11 = getDoubleFromTextField(startIndex + 4);
    return new Matrix2x2(a00, a01, a10, a11);
  }

  /**
   * Extracts the translation vector of an affine transformation from the affine grid.
   *
   * <p> The index counts from 0 and up starting at first transform. </p>
   *
   * @param index The index of the affine transformation.
   * @return The translation vector.
   */
  private Vector2D extractVectorFromGrid(int index) {
    int startIndex = 8 + index * 6;
    double x = getDoubleFromTextField(startIndex + 2);
    double y = getDoubleFromTextField(startIndex + 5);
    return new Vector2D(x, y);
  }

  /**
   * Retrieves the value of a TextField as a double.
   *
   * @param index The index of the TextField in the affine grid.
   * @return The double value of the TextField.
   */
  private double getDoubleFromTextField(int index) {
    TextField textField = (TextField) affineGrid.getChildren().get(index);
    return Double.parseDouble(textField.getText());
  }

}
