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

public class ChaosGameTextFileReader implements ChaosGameFileHandler.ChaosGameFileReader {

  @Override
  public ChaosGameDescription readFromFile(String path) throws IOException, IllegalArgumentException {
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

  private ChaosGameDescription createGameDescription(TransformType type, Scanner scanner, Vector2D minCoords, Vector2D maxCoords) throws IllegalArgumentException {
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
        default -> throw new IllegalArgumentException("Unknown transform type: " + type.getTypeString());
      };
    } catch (IllegalArgumentException e) {
      LoggerUtil.logError("Invalid transform data for type: " + type + ". " + e.getMessage());
      throw e;
    }
    return gameDescription;
  }

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

  private Vector2D readVector2D(Scanner scanner) {
    try {
      var x0 = readDouble(scanner);
      var x1 = readDouble(scanner);
      return new Vector2D(x0, x1);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException(e);
    }
  }

  private double readDouble(Scanner scanner) throws IllegalArgumentException {
    String input = scanner.next().trim();
    if (input.isEmpty()) {
      throw new IllegalArgumentException("Empty string encountered when expecting a double.");
    }
    return Double.parseDouble(input);
  }

  private Matrix2x2 readMatrix2x2(Scanner scanner) throws IllegalArgumentException {
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

  private Transform2D readAffineTransform2D(Scanner scanner) {
    try {
      return new AffineTransform2D(readMatrix2x2(scanner), readVector2D(scanner));
    } catch (IllegalArgumentException e) {
      LoggerUtil.logError("Failed to read Affine transform: " + e.getMessage());
      return null;
    }
  }
}