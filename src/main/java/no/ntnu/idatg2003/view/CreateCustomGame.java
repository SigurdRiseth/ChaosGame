package no.ntnu.idatg2003.view;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import no.ntnu.idatg2003.controller.CreateCustomGameController;

/**
 * The CreateCustomGame class is a view class that creates the view for the Create Custom Game
 * scene.
 * <p>
 *   The CreateCustomGame class creates the view for the Create Custom Game scene. The scene
 *   contains text fields for the user to input parameters for a Julia Set and an Affine
 *   Transformation. The user can also save the parameters with a name.
 * </p>
 *
 * @version 1.0
 * @see CreateCustomGameController
 * @author Sigurd Riseth
 * @since 11.05.2024
 */
public class CreateCustomGame {

  private final CreateCustomGameController controller;


  public CreateCustomGame(CreateCustomGameController controller) {
    this.controller = controller;
  }

  public Scene getScene() {
    BorderPane content = createContent();
    content.setPadding(new Insets(15, 20, 15, 20));

    // Make the content scrollable vertically
    ScrollPane scrollPane = new ScrollPane(content);
    scrollPane.setFitToWidth(true);
    scrollPane.setFitToHeight(true);

    return new Scene(scrollPane, 800, 600);
  }

  private BorderPane createContent() {
    BorderPane content = new BorderPane();

    // Back button
    Button backButton = new Button("Return");
    backButton.setOnAction(e -> {
      controller.returnToFrontPage();
    });

    // Create layout
    VBox vbox = new VBox(10);
    vbox.setPadding(new Insets(10));

    // Julia Set Parameters
    GridPane juliaGrid = new GridPane();
    juliaGrid.setVgap(5);
    juliaGrid.setHgap(5);
    juliaGrid.addRow(0, new Label("Min X:"), new TextField());
    juliaGrid.addRow(1, new Label("Min Y:"), new TextField());
    juliaGrid.addRow(2, new Label("Max X:"), new TextField());
    juliaGrid.addRow(3, new Label("Max Y:"), new TextField());
    juliaGrid.addRow(4, new Label("Real part of C:"), new TextField());
    juliaGrid.addRow(5, new Label("Imaginary part of C:"), new TextField());
    juliaGrid.setAlignment(javafx.geometry.Pos.CENTER);

    // Affine Transformation Parameters
    GridPane affineGrid = new GridPane();
    affineGrid.setVgap(5);
    affineGrid.setHgap(5);
    affineGrid.addRow(0, new Label("Min X:"), new TextField());
    affineGrid.addRow(1, new Label("Min Y:"), new TextField());
    affineGrid.addRow(2, new Label("Max X:"), new TextField());
    affineGrid.addRow(3, new Label("Max Y:"), new TextField());
    affineGrid.addRow(4, matrixField(), vectorField());
    affineGrid.setAlignment(javafx.geometry.Pos.CENTER);

    // Set text color to white
    for (Node node: juliaGrid.getChildren()) {
      if (node instanceof Label) {
        ((Label) node).setTextFill(Color.WHITE);
      }
    }
    for (Node node: affineGrid.getChildren()) {
      if (node instanceof Label) {
        ((Label) node).setTextFill(Color.WHITE);
      }
    }

    // Button to save parameters
    TextField juliaName = new TextField();
    juliaName.setPromptText("Insert name");
    Button saveJuliaButton = new Button("Save Julia-Set");
    HBox juliaBox = new HBox(10);

    juliaBox.getChildren().addAll(juliaName, saveJuliaButton);
    juliaBox.setAlignment(javafx.geometry.Pos.CENTER);

    TextField affineName = new TextField();
    affineName.setPromptText("Insert name");
    Button saveAffineButton = new Button("Save Affine-Transformation");
    HBox affineBox = new HBox(10);

    // Button to add a transformation
    Button addTransformationButton = new Button("Add Transformation");
    addTransformationButton.setOnAction(e -> {
      affineGrid.addRow(affineGrid.getRowCount(), matrixField(), vectorField());
    });

    affineBox.getChildren().addAll(affineName, saveAffineButton);
    affineBox.setAlignment(javafx.geometry.Pos.CENTER);

    // Titles
    Text juliaTitle = new Text("Julia Set Parameters");
    juliaTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
    juliaTitle.setFill(Color.WHITE);

    Text affineTitle = new Text("Affine Transformation Parameters");
    affineTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
    affineTitle.setFill(Color.WHITE);

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

  private Node vectorField() {
    TextField xField = new TextField();
    xField.setPromptText("x");

    TextField yField = new TextField();
    yField.setPromptText("y");

    xField.setMaxWidth(50);
    yField.setMaxWidth(50);

    VBox vbox = new VBox(5);
    vbox.getChildren().addAll(xField, yField);
    return vbox;
  }

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

  private static TextField matrixTextField(String promptText) {
    TextField textField = new TextField();
    textField.setPromptText(promptText);
    textField.setMaxWidth(50);
    return textField;
  }


}
