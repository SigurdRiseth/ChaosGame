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

public class CreateCustomGameController {
  private ChaosGameApp app;
  private CreateCustomGame createCustomGameView;

  /**
   * Constructor for the CreateCustomGameController class.
   *
   * @param app The ChaosGameApp to use.
   */
  public CreateCustomGameController(ChaosGameApp app) {
    this.app = app;
    this.createCustomGameView = new CreateCustomGame(this);
  }

  /**
   * Returns the scene for the view.
   *
   * @return The scene for the view.
   */
  public Scene getScene() {
    return createCustomGameView.getScene();
  }

  /**
   * Returns to the front page.
   */
  public void returnToFrontPage() {
    app.showMainScene();
  }

  /**
   * Saves the Julia set to a given filename.
   *
   * <p>
   *   The file is saved under src/main/user.files/ and is saved as a CSV-file.
   *   Any spaces in the filename are replaced with underscores.
   * </p>
   *
   * @param fileName The name of the file to save to.
   */
  public void saveJuliaSet(String fileName) {
    fileName = formatFileName(fileName);
    LoggerUtil.logInfo("Saving Julia set to file: " + fileName);
    Vector2D min = createCustomGameView.getCoords("julia", 0, 1);
    Vector2D max = createCustomGameView.getCoords("julia", 2, 3);
    Complex complexNumber = createCustomGameView.getComplexNumber();

    List<Transform2D> transforms = List.of(new JuliaTransform(complexNumber, 1));

    ChaosGameDescription chaosGameDescription = new ChaosGameDescription(min, max, transforms);

    ChaosGameFileHandler.writeToFile(chaosGameDescription, fileName);
    LoggerUtil.logInfo("Julia set saved");
  }

  /**
   * Formats the filename to be saved.
   *
   * @param fileName The filename to format.
   * @return The formatted filename.
   */
  private static String formatFileName(String fileName) {
    fileName = fileName.replaceAll("\\s", "_");
    fileName = "src/main/user.files/" + fileName + ".csv";
    return fileName;
  }

  public void saveAffineTransformation(String fileName) {
    fileName = formatFileName(fileName);
    LoggerUtil.logInfo("Saving Affine transformation to file: " + fileName);
    Vector2D min = createCustomGameView.getCoords("affine", 0, 1);
    Vector2D max = createCustomGameView.getCoords("affine", 2, 3);

    List<Transform2D> transforms = createCustomGameView.getAffineTransforms();

    ChaosGameDescription chaosGameDescription = new ChaosGameDescription(min, max, transforms);

    ChaosGameFileHandler.writeToFile(chaosGameDescription, fileName);
    LoggerUtil.logInfo("Affine transformation saved");
  }
}
