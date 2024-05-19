package no.ntnu.idatg2003.controller;

import java.util.List;
import javafx.scene.Scene;
import no.ntnu.idatg2003.model.game.engine.ChaosGameDescription;
import no.ntnu.idatg2003.model.game.engine.ChaosGameFileHandler;
import no.ntnu.idatg2003.model.math.datatypes.Complex;
import no.ntnu.idatg2003.model.math.datatypes.Vector2D;
import no.ntnu.idatg2003.model.transformations.JuliaTransform;
import no.ntnu.idatg2003.model.transformations.Transform2D;
import no.ntnu.idatg2003.utility.LoggerUtil;
import no.ntnu.idatg2003.view.ChaosGameApp;
import no.ntnu.idatg2003.view.CreateCustomGame;

/**
 * The CreateCustomGameController class is the controller for the CreateCustomGame view.
 *
 * <p>
 *   The CreateCustomGameController class is responsible for handling user input from the
 *   CreateCustomGame view. It is responsible for saving the input values to a file.
 * </p>
 */
public class CreateCustomGameController {
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
      view.showInputError();
    } else {
      fileName = prepareFilePath(fileName);
      LoggerUtil.logInfo("Saving Julia set to file: " + fileName);

      ChaosGameDescription chaosGameDescription = createJuliaDescription();

      ChaosGameFileHandler.writeToFile(chaosGameDescription, fileName);
      view.showSaveSuccess();
      LoggerUtil.logInfo("Julia set saved");
    }
  }

  /**
   * Creates a ChaosGameDescription for an affine transformation from the input values.
   *
   * @return The ChaosGameDescription for the affine transformation.
   */
  private ChaosGameDescription createJuliaDescription() {
    Vector2D min = null;
    Vector2D max = null;
    Complex complexNumber = null;
    try {
      min = view.getCoords("julia", 0, 1);
      max = view.getCoords("julia", 2, 3);
      complexNumber = view.getComplexNumber();
    } catch (NumberFormatException e) {
      LoggerUtil.logError("Failed to read input values for Julia set.");
      view.showInputError();
    }
    return new ChaosGameDescription(min, max, List.of(new JuliaTransform(complexNumber, 1)));
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
      view.showInputError();
    } else {
      fileName = prepareFilePath(fileName);
      LoggerUtil.logInfo("Saving Affine transformation to file: " + fileName);
      ChaosGameDescription chaosGameDescription = createAffineDescription();

      ChaosGameFileHandler.writeToFile(chaosGameDescription, fileName);
      view.showSaveSuccess();
      LoggerUtil.logInfo("Affine transformation saved");
    }
  }

  /**
   * Creates a ChaosGameDescription for a Julia set from the input values.
   *
   * @return The ChaosGameDescription for the Julia set.
   */
  private ChaosGameDescription createAffineDescription() {
    Vector2D min = null;
    Vector2D max = null;
    List<Transform2D> transforms = null;
    try {
      min = view.getCoords("affine", 0, 1);
      max = view.getCoords("affine", 2, 3);

      transforms = view.getAffineTransforms();
    } catch (NumberFormatException e) {
      LoggerUtil.logError("Failed to read input values for affine transformation.");
      view.showInputError();
    }

    return new ChaosGameDescription(min, max, transforms);
  }
}
