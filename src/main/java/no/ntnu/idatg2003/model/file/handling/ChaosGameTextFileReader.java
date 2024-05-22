package no.ntnu.idatg2003.model.file.handling;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;
import no.ntnu.idatg2003.model.game.engine.ChaosGameDescription;
import no.ntnu.idatg2003.model.game.engine.ChaosGameDescriptionFactory;
import no.ntnu.idatg2003.model.math.datatypes.Complex;
import no.ntnu.idatg2003.model.math.datatypes.Matrix2x2;
import no.ntnu.idatg2003.model.math.datatypes.Vector2D;
import no.ntnu.idatg2003.model.transformations.AffineTransform2D;
import no.ntnu.idatg2003.model.transformations.Transform2D;
import no.ntnu.idatg2003.utility.enums.TransformType;
import no.ntnu.idatg2003.utility.logging.LoggerUtil;

/**
 * The ChaosGameTextFileReader class is responsible for reading a ChaosGameDescription from a text
 * file.
 *
 * <p>
 * The ChaosGameTextFileReader class is responsible for reading a ChaosGameDescription from a text
 * file. It reads the file line by line and parses the content into a ChaosGameDescription object.
 * </p>
 *
 * @version 1.0.0
 * @see ChaosGameDescription
 * @see ChaosGameFileHandler
 * @see ChaosGameDescriptionFactory
 * @since 14.04.2024
 */
public class ChaosGameTextFileReader implements ChaosGameFileHandler.ChaosGameFileReader {

  /**
   * Reads a ChaosGameDescription from a file.
   * <p>
   * This method reads a ChaosGameDescription from a file specified by the given path. The file
   * should contain valid data representing a ChaosGameDescription, including the transform type,
   * min and max coordinates, and transformation details.
   * </p>
   *
   * @param path the path to the file. It should be a valid file path pointing to a readable file
   *             containing a ChaosGameDescription.
   * @return the ChaosGameDescription read from the file.
   * @throws IllegalArgumentException if the file content is invalid or cannot be parsed into a
   *                                  ChaosGameDescription.
   * @throws IOException              if an I/O error occurs during reading the file.
   */
  @Override
  public ChaosGameDescription readFromFile(String path)
      throws IOException, IllegalArgumentException {
    ChaosGameDescription gameDescription = null;
    try (Scanner scanner = new Scanner(Files.newBufferedReader(Path.of(path)))) {
      scanner.useLocale(Locale.US); // Use US locale to ensure correct number formatting
      scanner.useDelimiter(",|#(?<=#).+"); // Split on comma or # and everything following it

      // Read the transform type
      String typeString = scanner.next().trim();
      // Convert the string to TransformType enum
      TransformType type = TransformType.fromString(typeString);

      // Read the min and max coordinates
      Vector2D minCoords = readVector2D(scanner);
      Vector2D maxCoords = readVector2D(scanner);

      // Create the game description
      gameDescription = createGameDescription(type, scanner, minCoords, maxCoords);
    }
    return gameDescription;
  }

  /**
   * Creates a ChaosGameDescription based on the provided type and scanner.
   * <p>
   * This method interprets the transform type and reads the appropriate transform data from the
   * scanner. For {@code AFFINE2D}, it reads a list of affine transformations. For {@code JULIA}, it
   * reads a complex number representing the Julia set transformation.
   * </p>
   *
   * @param type      the type of transform to read. It determines whether to read affine
   *                  transformations or a Julia set transform.
   * @param scanner   the scanner to read the transform data from. The data format must match the
   *                  expected format for the specified transform type.
   * @param minCoords the minimum coordinates of the game. These define the lower bounds for the
   *                  game space.
   * @param maxCoords the maximum coordinates of the game. These define the upper bounds for the
   *                  game space.
   * @return the created ChaosGameDescription based on the provided parameters.
   * @throws IllegalArgumentException if the transform data is invalid or cannot be parsed, or if
   *                                  the transform type is unknown.
   */
  private ChaosGameDescription createGameDescription(TransformType type, Scanner scanner,
      Vector2D minCoords, Vector2D maxCoords) throws IllegalArgumentException {
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
        default ->
            throw new IllegalArgumentException("Unknown transform type: " + type.getTypeString());
      };
    } catch (IllegalArgumentException e) {
      LoggerUtil.logError("Invalid transform data for type: " + type + ". " + e.getMessage());
      throw e;
    }
    return gameDescription;
  }

  /**
   * Reads a list of affine transformations from the scanner.
   *
   * <p>
   * This method reads the affine transformations from the scanner until there are no more tokens
   * left.
   * </p>
   *
   * @param scanner the scanner to read the affine transformations from.
   * @return the list of affine transformations.
   */
  private List<Transform2D> readAffineTransforms(Scanner scanner) {
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
   * Reads a Vector2D from the provided Scanner.
   * <p>
   * This method expects the scanner to provide two double values sequentially. If the input does
   * not contain exactly two double values, an IllegalArgumentException is thrown.
   * </p>
   *
   * @param scanner the Scanner instance from which to read the Vector2D
   * @return the parsed Vector2D object constructed with the two read double values
   * @throws IllegalArgumentException if the input does not contain two valid double values or
   *                                  cannot be parsed
   */
  private Vector2D readVector2D(Scanner scanner) {
    try {
      var x0 = readDouble(scanner);
      var x1 = readDouble(scanner);
      return new Vector2D(x0, x1);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException(e);
    }
  }

  /**
   * Reads the next input from the provided Scanner, trims it, and attempts to parse it as a
   * double.
   * <p>
   * This method will log errors and throw an {@link IllegalArgumentException} if the input is empty
   * or cannot be parsed as a double.
   * </p>
   *
   * @param scanner the {@link Scanner} instance from which to read the input
   * @return the parsed double value
   * @throws IllegalArgumentException if the input is an empty string or cannot be parsed as a
   *                                  double
   */
  private double readDouble(Scanner scanner) {
    String input = scanner.next().trim();
    if (input.isEmpty()) {
      LoggerUtil.logError("Empty string encountered when expecting a double.");
      throw new IllegalArgumentException("Empty string encountered when expecting a double.");
    }
    try {
      return Double.parseDouble(input);
    } catch (NumberFormatException e) {
      LoggerUtil.logError("Invalid double format: " + input + ". Error: " + e.getMessage());
      throw new IllegalArgumentException("Invalid double format: " + input, e);
    }
  }

  /**
   * Reads a 2x2 matrix from the given scanner.
   * <p>
   * This method expects the scanner to provide four double values sequentially. If the input does
   * not contain exactly four double values, an IllegalArgumentException is thrown.
   * </p>
   *
   * @param scanner the Scanner to read the double values from
   * @return a Matrix2x2 object constructed with the four read double values
   * @throws IllegalArgumentException if the input does not contain four valid double values
   */
  private Matrix2x2 readMatrix2x2(Scanner scanner) throws IllegalArgumentException {
    try {
      var a00 = readDouble(scanner);
      var a01 = readDouble(scanner);
      var a10 = readDouble(scanner);
      var a11 = readDouble(scanner);
      return new Matrix2x2(a00, a01, a10, a11);
    } catch (NoSuchElementException | NumberFormatException e) {
      throw new IllegalArgumentException(
          "Invalid input for Matrix2x2. Expected four double values.", e);
    }
  }

  /**
   * Reads a Julia transform from the provided Scanner.
   * <p>
   * This method expects the scanner to provide two double values representing the real and
   * imaginary parts of a complex number. If the input does not contain exactly two double values,
   * an IllegalArgumentException is thrown.
   * </p>
   *
   * @param scanner the Scanner instance from which to read the Julia transform
   * @return the parsed Complex object representing the Julia transform
   * @throws IllegalArgumentException if the input does not contain two valid double values or
   *                                  cannot be parsed
   */
  private Complex readJuliaTransform(Scanner scanner) throws IllegalArgumentException {
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
   * Reads an affine transformation from the provided Scanner.
   * <p>
   * This method expects the scanner to provide six double values sequentially: four for the 2x2
   * matrix and two for the 2D vector. If the input does not contain the required values, an
   * IllegalArgumentException is thrown, and null is returned.
   * </p>
   *
   * @param scanner the Scanner instance from which to read the affine transformation
   * @return the parsed AffineTransform2D object, or null if the input is invalid
   * @throws IllegalArgumentException if the input does not contain the required values for a
   *                                  Matrix2x2 and a Vector2D
   */
  private Transform2D readAffineTransform2D(Scanner scanner) {
    try {
      return new AffineTransform2D(readMatrix2x2(scanner), readVector2D(scanner));
    } catch (IllegalArgumentException e) {
      LoggerUtil.logError("Failed to read Affine transform: " + e.getMessage());
      return null;
    }
  }
}