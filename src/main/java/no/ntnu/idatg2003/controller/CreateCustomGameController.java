package no.ntnu.idatg2003.controller;

import java.util.List;
import javafx.scene.Scene;
import no.ntnu.idatg2003.model.game.engine.ChaosGameDescription;
import no.ntnu.idatg2003.model.game.engine.ChaosGameDescriptionFactory;
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
 *
 * @version 0.0.1
 * @since 10.05.2024
 * @author Sigurd Riseth
 * @see CreateCustomGame
 * @see ChaosGameApp
 */
public class CreateCustomGameController implements ControllerInterface{
  private final ChaosGameApp app;
  private final CreateCustomGame createCustomGameView;

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
  @Override
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
    fileName = prepareFilePath(fileName);
    LoggerUtil.logInfo("Saving Julia set to file: " + fileName);

    ChaosGameDescription chaosGameDescription = createJuliaDescription();

    ChaosGameFileHandler.writeToFile(chaosGameDescription, fileName);
    LoggerUtil.logInfo("Julia set saved");
  }

  /**
   * Creates a ChaosGameDescription for an affine transformation from the input values.
   *
   * @return The ChaosGameDescription for the affine transformation.
   */
  private ChaosGameDescription createJuliaDescription() {
    Vector2D min = createCustomGameView.getCoords("julia", 0, 1);
    Vector2D max = createCustomGameView.getCoords("julia", 2, 3);
    Complex complexNumber = createCustomGameView.getComplexNumber();

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
    fileName = prepareFilePath(fileName);
    LoggerUtil.logInfo("Saving Affine transformation to file: " + fileName);
    ChaosGameDescription chaosGameDescription = createAffineDescription();

    ChaosGameFileHandler.writeToFile(chaosGameDescription, fileName);
    LoggerUtil.logInfo("Affine transformation saved");
  }

  /**
   * Creates a ChaosGameDescription for a Julia set from the input values.
   *
   * @return The ChaosGameDescription for the Julia set.
   */
  private ChaosGameDescription createAffineDescription() {
    Vector2D min = createCustomGameView.getCoords("affine", 0, 1);
    Vector2D max = createCustomGameView.getCoords("affine", 2, 3);

    List<Transform2D> transforms = createCustomGameView.getAffineTransforms();

    return new ChaosGameDescription(min, max, transforms);
  }

  /**
   * Returns a list of all custom games.
   *
   * @return A list of all custom games.
   */

  public List<String> getCustomGames() {
    throw new UnsupportedOperationException("Not implemented yet");
  }
}
