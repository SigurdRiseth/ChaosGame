package no.ntnu.idatg2003.view;

import java.util.function.Consumer;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
import no.ntnu.idatg2003.utility.TransformType;

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
   * Retrieves the number of affine transformations from the affine grid.
   *
   * @return The number of affine transformations.
   */
  public int getTransformAmount() {
    int size = affineGrid.getChildren().size();
    return (size - 8) / 6;
  }

  /**
   * Retrieves the text from a given text field in the given grid.
   *
   * @param type The type of transformation to retrieve the text from.
   * @param i    The index of the text field.
   * @return The text from the text field.
   */
  public String getTextFromGrid(TransformType type, int i) {
    GridPane grid = type.equals(TransformType.AFFINE2D) ? affineGrid : juliaGrid;
    TextField textField = (TextField) grid.getChildren().get(i);
    return textField.getText();
  }

  /**
   * Shows an error message to the user.
   *
   * @param message The message to display.
   */
  public void showInputError(String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Input Error");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

  /**
   * Shows an information alert to the user.
   *
   * @param title The title of the message.
   * @param message The message to display.
   */
  public void showInfoAlert(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }
}