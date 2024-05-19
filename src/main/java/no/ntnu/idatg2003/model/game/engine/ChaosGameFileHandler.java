package no.ntnu.idatg2003.model.game.engine;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.stream.Stream;
import no.ntnu.idatg2003.model.math.datatypes.Complex;
import no.ntnu.idatg2003.model.math.datatypes.Matrix2x2;
import no.ntnu.idatg2003.model.math.datatypes.Vector2D;
import no.ntnu.idatg2003.model.transformations.AffineTransform2D;
import no.ntnu.idatg2003.model.transformations.JuliaTransform;
import no.ntnu.idatg2003.model.transformations.Transform2D;
import no.ntnu.idatg2003.utility.logging.LoggerUtil;
import no.ntnu.idatg2003.utility.enums.TransformType;
import no.ntnu.idatg2003.utility.exceptions.CustomGameFileException;

/**
 * This class is responsible for handling the file input and output for the ChaosGame. It will read
 * the file and create the game. It will also write the game to a file.
 *
 * @author Sigurd Riseth, Theodor Sjetnan Utvik
 * @version 0.0.2
 */
public class ChaosGameFileHandler {

  /**
   * Private constructor to prevent instantiation.
   */
  private ChaosGameFileHandler() {}

  /**
   * Method to read a ChaosGameDescription from a file.
   *
   * <p>The file should contain the following
   * information: - The type of transformation (Julia or Affine2D) - The minimum
   * and maximum coordinates for the game - The transformations to be applied to the points in the
   * game. </p>
   * The file should be formatted as follows: <br>
   * <br>
   * <b>AffineTransform2D:</b>
   *
   * <p>Affine2D # Type <br>
   * minX, minY # Min coords <br>
   * maxX, maxY # Max coords <br>
   * a00, a01, a10, a11, b0, b1 #1. Transform<br>
   * a00, a01, a10, a11, b0, b1 #2. Transform<br>
   * ...<br>
   * a00, a01, a10, a11, b0, b1 #n. Transform<br>
   * <br>
   * <b>JuliaTransform:</b>
   *
   * <p>Julia # Type <br>
   * minX, minY # Min coords <br>
   * maxX, maxY # Max coords <br>
   * Re, Im # Transform<br>
   *
   * @param path the path to the file
   * @return the ChaosGameDescription
   */
  public static ChaosGameDescription readFromFile(String path) {
    ChaosGameDescription gameDescription = null;
    try (Scanner scanner = new Scanner(Files.newBufferedReader(Path.of(path)))) {
      scanner.useLocale(Locale.US);
      scanner.useDelimiter(
          ",|#(?<=#).+"); // Split on comma or # and everything following it

      // Read the transform type
      String typeString = scanner.next().trim();
      // Convert the string to TransformType enum
      TransformType type = TransformType.valueOf(typeString.toUpperCase());

      Vector2D minCoords = readVector2D(scanner);
      Vector2D maxCoords = readVector2D(scanner);

      gameDescription = createGameDescription(type, scanner, minCoords, maxCoords);

    } catch (IOException e) {
      LoggerUtil.logError("Failed to read the file: " + e.getMessage());
    } catch (IllegalArgumentException e) {
      LoggerUtil.logError("Error creating the game description: " + e.getMessage());
    }
    return gameDescription;
  }

  /**
   * Creates a ChaosGameDescription object based on the provided type and scanner.
   *
   * @param type The type of transformation to create.
   * @param scanner The scanner to read the transformation data from.
   * @param minCoords The minimum coordinates for the game.
   * @param maxCoords The maximum coordinates for the game.
   * @return A new ChaosGameDescription object.
   * @throws IllegalArgumentException if the transform type is unknown or the input is invalid.
   */
  private static ChaosGameDescription createGameDescription(
      TransformType type, Scanner scanner, Vector2D minCoords,
      Vector2D maxCoords) throws IllegalArgumentException {

    ChaosGameDescription gameDescription;
    try {
      gameDescription = switch (type) {
        case AFFINE2D -> {
          List<Transform2D> transform2Ds = readAffineTransforms(scanner);
          yield new ChaosGameDescription(minCoords, maxCoords, transform2Ds);
        }
        case JULIA -> {
          Complex complex = readJuliaTransform(scanner);
          yield ChaosGameDescriptionFactory.createJuliaSet(minCoords, maxCoords, complex);
        }
        // Should never happen. Included in case of future changes to the TransformType enum. (19.5.24)
        default -> throw new IllegalArgumentException("Unknown transform type: " + type);
      };
    } catch (IllegalArgumentException e) {
      LoggerUtil.logError("Invalid transform data for type: " + type + ". " + e.getMessage());
      throw e;
    }
    return gameDescription;
  }

  /**
   * Reads and constructs a list of AffineTransform2D objects from the provided Scanner object.
   *
   * @param scanner The Scanner to read the AffineTransform2D data from.
   * @return A list of AffineTransform2D objects.
   */
  private static List<Transform2D> readAffineTransforms(Scanner scanner) {
    List<Transform2D> transform2Ds = new ArrayList<>();
    while (scanner.hasNext()) {
      Transform2D transform = readAffineTransform2D(scanner);
      if (transform != null) {
        transform2Ds.add(transform);
      }
    }
    return transform2Ds;
  }

  /**
   * Method to write a ChaosGameDescription to a file. The file will be in the format: <br>
   * <br>
   * <b>AffineTransform2D:</b>
   *
   * <p>Affine2D <br>
   * maxX, maxY <br>
   * a00, a01, a10, a11, b0, b1 #1. Transform<br>
   * minX, minY <br>
   * a00, a01, a10, a11, b0, b1 #2. Transform<br>
   * ...<br>
   * a00, a01, a10, a11, b0, b1 #n. Transform<br>
   * <br>
   * <b>JuliaTransform:</b>
   *
   * <p>Julia <br>
   * minX, minY <br>
   * maxX, maxY <br>
   * Re, Im #Transform<br>
   *
   * @throws IllegalArgumentException if the transform type is unknown
   * @param description the ChaosGameDescription to write to the file
   * @param path the path to the file
   */
  public static void writeToFile(ChaosGameDescription description, String path) {
    try (BufferedWriter writer = Files.newBufferedWriter(Path.of(path))) {
      Transform2D type = description.getTransforms().getFirst();
      String stringType;
      if (type instanceof AffineTransform2D) {
        stringType = "Affine2D";
      } else if (type instanceof JuliaTransform) {
        stringType = "Julia";
      } else {
        LoggerUtil.logWarning("Unknown transform type");
        throw new IllegalArgumentException("Unknown transform type");
      }
      writeTransformType(writer, stringType);
      writeMinMaxCoords(writer, description);
      for (Transform2D transform : description.getTransforms()) {
        writer.write("\n" + transform.toString() + " # transforms");
        LoggerUtil.logInfo("Transform written to file");
      }
    } catch (IOException e) {
      LoggerUtil.logError("Failed to create the file: " + e.getMessage());
    }
  }

  /**
   * Writes the min and max coordinates to the provided BufferedWriter instance.
   *
   * @param writer The BufferedWriter to write the coordinates to.
   * @param description The ChaosGameDescription containing the min and max coordinates.
   */
  private static void writeMinMaxCoords(BufferedWriter writer, ChaosGameDescription description) {
    try {
      writer.write(
          String.format(
              "%s # Lower Left %n", description.getMinCoords().toString()));
      LoggerUtil.logInfo("Lower Left written to file");
      writer.write(
          String.format(
              "%s # Upper Right", description.getMaxCoords().toString()));
      LoggerUtil.logInfo("Upper Right written to file");
    } catch (IOException e) {
      LoggerUtil.logError("Failed to write the min/max coordinates: " + e.getMessage());
    }
  }

  /**
   * Writes the transform type to the provided BufferedWriter instance.
   *
   * @param writer The BufferedWriter to write the transform type to.
   * @param stringType The string representation of the transform type.
   */
  private static void writeTransformType(BufferedWriter writer, String stringType) {
    try {
      writer.write(stringType + " # Type of fractal \n");
    } catch (IOException e) {
      LoggerUtil.logError("Failed to write the transform type: " + e.getMessage());
    }
  }

  /**
   * Parses a {@link Vector2D} from the given scanner.
   *
   * @param scanner The scanner to parse the vector from.
   * @return A new {@code Vector2D} instance based on the parsed values.
   * @throws IllegalArgumentException if the input is invalid and parsing fails.
   */
  private static Vector2D readVector2D(Scanner scanner) {
    try {
      var x0 = readDouble(scanner);
      var x1 = readDouble(scanner);
      return new Vector2D(x0, x1);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException(e);
    }
  }

  /**
   * Reads a double value from the given {@link Scanner} instance, ensuring it is not an empty
   * string.
   *
   * @param scanner The scanner to read the double from.
   * @return The read double value.
   * @throws IllegalArgumentException if an empty string is encountered.
   */
  private static double readDouble(Scanner scanner) throws IllegalArgumentException {
    String input = scanner.next().trim();
    if (input.isEmpty()) {
      throw new IllegalArgumentException("Empty string encountered when expecting a double.");
    }
    return Double.parseDouble(input);
  }

  /**
   * Parses a Matrix2x2 object from the provided Scanner object. It expects to read four consecutive
   * double values.
   *
   * @param scanner The Scanner to parse the Matrix2x2 from.
   * @return A new Matrix2x2 object created from the parsed double values.
   * @throws IllegalArgumentException if the input is invalid or parsing fails.
   */
  private static Matrix2x2 readMatrix2x2(Scanner scanner) throws IllegalArgumentException {
    try {
      var a00 = readDouble(scanner);
      var a01 = readDouble(scanner);
      var a10 = readDouble(scanner);
      var a11 = readDouble(scanner);
      return new Matrix2x2(a00, a01, a10, a11);
    } catch (NoSuchElementException | NumberFormatException e) {
      throw new IllegalArgumentException("Invalid input for Matrix2x2. Expected four double values.", e);
    }
  }

  /**
   * Reads and constructs a JuliaTransform from the provided Scanner object.
   *
   * @param scanner The Scanner to read the JuliaTransform data from.
   * @return A new JuliaTransform object.
   * @throws IllegalArgumentException if the input is invalid or parsing fails.
   */
  private static Complex readJuliaTransform(Scanner scanner) throws IllegalArgumentException {
    try {
      double real = readDouble(scanner);
      double imaginary = readDouble(scanner);
      return new Complex(real, imaginary);
    } catch (IllegalArgumentException e) {
      LoggerUtil.logError("Failed to read Julia transform: " + e.getMessage());
      throw e;
    }
  }

  /**
   * Reads and constructs an AffineTransform2D from the provided Scanner object.
   *
   * @param scanner The Scanner to read the AffineTransform2D data from.
   * @return A new AffineTransform2D object.
   */
  private static Transform2D readAffineTransform2D(Scanner scanner) {
    try {
      return new AffineTransform2D(readMatrix2x2(scanner), readVector2D(scanner));
    } catch (IllegalArgumentException e) {
      LoggerUtil.logError("Failed to read Affine transform: " + e.getMessage());
      return null;
    }
  }

  /**
   * Retrieves the names of all custom game files located in the "src/main/user.files" directory.
   *
   * @return A list containing the names of all custom game files.
   * @throws IllegalStateException        if the directory does not exist or is not a directory.
   * @throws CustomGameFileException      if an error occurs while listing files in the directory.
   */
  public static List<String> getCustomGameFileNames() {
    String directoryPath = "src/main/user.files";

    Path directory = Path.of(directoryPath);

    if (!Files.exists(directory) || !Files.isDirectory(directory)) {
      throw new IllegalStateException("Directory does not exist or is not a directory: " + directoryPath);
    }

    try (Stream<Path> filesStream = Files.list(directory)) {
      return filesStream
          .filter(Files::isRegularFile)
          .map(Path::getFileName)
          .map(Path::toString)
          .toList();
    } catch (Exception e) {
      String errorMessage = "Failed to list files in directory: " + directoryPath;
      LoggerUtil.logError(errorMessage);
      throw new CustomGameFileException(errorMessage, e);
    }
  }

}