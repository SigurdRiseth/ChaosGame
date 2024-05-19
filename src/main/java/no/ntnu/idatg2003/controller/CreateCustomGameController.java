package no.ntnu.idatg2003.controller;

import static no.ntnu.idatg2003.utility.enums.TransformType.AFFINE2D;
import static no.ntnu.idatg2003.utility.enums.TransformType.JULIA;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Scene;
import no.ntnu.idatg2003.model.game.engine.ChaosGameDescription;
import no.ntnu.idatg2003.model.game.engine.ChaosGameFileHandler;
import no.ntnu.idatg2003.model.math.datatypes.Complex;
import no.ntnu.idatg2003.model.math.datatypes.Matrix2x2;
import no.ntnu.idatg2003.model.math.datatypes.Vector2D;
import no.ntnu.idatg2003.model.transformations.AffineTransform2D;
import no.ntnu.idatg2003.model.transformations.JuliaTransform;
import no.ntnu.idatg2003.model.transformations.Transform2D;
import no.ntnu.idatg2003.utility.logging.LoggerUtil;
import no.ntnu.idatg2003.utility.enums.TransformType;
import no.ntnu.idatg2003.view.ChaosGameApp;
import no.ntnu.idatg2003.view.CreateCustomGame;

/**
 * The CreateCustomGameController class is the controller for the CreateCustomGame view.
 *
 * <p>
 *   The CreateCustomGameController class is responsible for handling user input from the
 *   CreateCustomGame view. It is responsible for saving the input values to a file.
 * </p>
 *
 * @version 0.0.1
 * @since 10.05.2024
 * @author Sigurd Riseth
 * @see CreateCustomGame
 * @see ChaosGameApp
 */
public class CreateCustomGameController implements ControllerInterface{
  private final ChaosGameApp app;
  private final CreateCustomGame view;

  /**
   * Constructor for the CreateCustomGameController class.
   *
   * @param app The ChaosGameApp to use.
   */
  public CreateCustomGameController(ChaosGameApp app) {
    this.app = app;
    this.view = new CreateCustomGame(this);
  }

  /**
   * Returns the scene for the view.
   *
   * @return The scene for the view.
   */
  @Override
  public Scene getScene() {
    return view.getScene();
  }

  /**
   * Returns to the front page.
   */
  public void returnToFrontPage() {
    app.showMainScene();
  }

  /**
   * Saves the input Julia-set to a given filename.
   *
   * <p>
   *   The file is saved under src/main/user.files/ and is saved as a CSV-file.
   *   Any spaces in the filename are replaced with underscores.
   * </p>
   *
   * @param fileName The name of the file to save to.
   */
  public void saveJuliaSet(String fileName) {
    if (fileName.isBlank()) {
      LoggerUtil.logError("Empty filename encountered when saving Julia set.");
      view.showInputError("Filename cannot be empty.");
    } else {
      fileName = prepareFilePath(fileName);
      LoggerUtil.logInfo("Saving Julia set to file: " + fileName);

      ChaosGameDescription chaosGameDescription = createJuliaDescription();

      if (chaosGameDescription != null) {
        ChaosGameFileHandler.writeToFile(chaosGameDescription, fileName);
        view.showInfoAlert("Save successful!", "Julia set saved to file: " + fileName);
        LoggerUtil.logInfo("Julia set saved");
      }
    }
  }

  /**
   * Creates a ChaosGameDescription for an affine transformation from the input values.
   *
   * @return The ChaosGameDescription for the affine transformation.
   */
  private ChaosGameDescription createJuliaDescription() {
    try {
      Vector2D min = getCoords(JULIA, 1);
      Vector2D max = getCoords(JULIA, 5);
      Complex complexNumber = getComplexNumber();
      return new ChaosGameDescription(min, max, List.of(new JuliaTransform(complexNumber, 1)));
    } catch (NumberFormatException e) {
      LoggerUtil.logError("Failed to read input values for Julia set.");
      view.showInputError("Failed to read input values for Julia set. "
          + "Please check the input values and try again.");
      return null;
    }
  }

  private Complex getComplexNumber() {
    double real = Double.parseDouble(view.getTextFromGrid(JULIA, 9));
    double imaginary = Double.parseDouble(view.getTextFromGrid(JULIA, 11));
    return new Complex(real, imaginary);
  }

  /**
   * Formats the filename correct folder and csv.
   *
   * @param fileName The filename to format.
   * @return The formatted filename.
   */
  private static String prepareFilePath(String fileName) {
    fileName = fileName.replaceAll("\\s", "_");
    fileName = "src/main/user.files/" + fileName + ".csv";
    return fileName;
  }

  /**
   * Saves the input affine transformation to a file.
   *
   * <p>
   *   The file is saved under src/main/user.files/ and is saved as a CSV-file.
   *   Any spaces in the filename are replaced with underscores.
   * </p>
   *
   * @param fileName The name of the file to save to.
   */
  public void saveAffineTransformation(String fileName) {
    if (fileName.isBlank()) {
      LoggerUtil.logError("Empty filename encountered when saving affine transformation.");
      view.showInputError("Filename cannot be empty.");
      return;
    }

    fileName = prepareFilePath(fileName);
    LoggerUtil.logInfo("Saving Affine transformation to file: " + fileName);

    ChaosGameDescription chaosGameDescription = createAffineDescription();
    if (chaosGameDescription == null) {
      // Error occurred during creation, show error and return
      view.showInputError("Failed to create affine transformation description.");
      LoggerUtil.logError("Failed to create affine transformation description.");
      return;
    }

    try {
      ChaosGameFileHandler.writeToFile(chaosGameDescription, fileName);
      view.showInfoAlert("Save successful!", "Affine transformation saved to file: "
          + fileName);
      LoggerUtil.logInfo("Affine transformation saved");
    } catch (Exception e) {
      LoggerUtil.logError("Failed to save affine transformation to file.");
      view.showInputError("Failed to save affine transformation to file."
          + " Please check the input values and try again.");
    }
  }

  /**
   * Creates a ChaosGameDescription for a Julia set from the input values.
   *
   * @return The ChaosGameDescription for the Julia set.
   */
  private ChaosGameDescription createAffineDescription() {
    Vector2D min;
    Vector2D max;
    List<Transform2D> transforms;
    try {
      min = getCoords(AFFINE2D, 1);
      max = getCoords(AFFINE2D, 5);

      transforms = getAffineTransforms();
    } catch (NumberFormatException e) {
      LoggerUtil.logError("Failed to read input values for affine transformation.");
      return null;
    }

    return new ChaosGameDescription(min, max, transforms);
  }

  /**
   * Retrieves a list of affine transformations from the affine grid.
   *
   * @return The list of affine transformations.
   */
  public List<Transform2D> getAffineTransforms() {
    List<Transform2D> transforms = new ArrayList<>();

    int amountOfTransforms = calculateAmountOfAffineTransforms();

    for (int i = 0; i < amountOfTransforms; i++) {
      Matrix2x2 matrix = extractMatrixFromGrid(i);
      Vector2D vector = extractVectorFromGrid(i);
      transforms.add(new AffineTransform2D(matrix, vector));
    }

    return transforms;
  }

  private Vector2D extractVectorFromGrid(int transformNumber) {
    int startIndex = 8 + transformNumber * 6;
    double x0 = getDoubleFromTextField(AFFINE2D, startIndex + 2);
    double x1 = getDoubleFromTextField(AFFINE2D, startIndex + 5);
    return new Vector2D(x0, x1);
  }

  private Matrix2x2 extractMatrixFromGrid(int transformNumber) {
    int startIndex = 8 + transformNumber * 6;
    double a00 = getDoubleFromTextField(AFFINE2D, startIndex);
    double a01 = getDoubleFromTextField(AFFINE2D, startIndex + 1);
    double a10 = getDoubleFromTextField(AFFINE2D, startIndex + 3);
    double a11 = getDoubleFromTextField(AFFINE2D, startIndex + 4);
    return new Matrix2x2(a00, a01, a10, a11);
  }

  private double getDoubleFromTextField(TransformType type, int startIndex) {
    return Double.parseDouble(view.getTextFromGrid(type, startIndex));
  }

  /**
   * Returns the amount of affine transformations.
   *
   * @return The amount of affine transformations.
   */
  private int calculateAmountOfAffineTransforms() {
    return view.getTransformAmount();
  }

  private Vector2D getCoords(TransformType transformType, int i) {
    double x0 = getDoubleFromTextField(transformType, i);
    double x1 = getDoubleFromTextField(transformType, i + 2);
    return new Vector2D(x0, x1);
  }
}
