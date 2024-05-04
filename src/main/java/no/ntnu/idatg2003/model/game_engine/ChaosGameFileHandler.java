package no.ntnu.idatg2003.model.game_engine;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import no.ntnu.idatg2003.model.math_datatypes.Complex;
import no.ntnu.idatg2003.model.math_datatypes.Matrix2x2;
import no.ntnu.idatg2003.model.math_datatypes.Vector2D;
import no.ntnu.idatg2003.model.transformations.AffineTransform2D;
import no.ntnu.idatg2003.model.transformations.JuliaTransform;
import no.ntnu.idatg2003.model.transformations.Transform2D;

/**
 * This class is responsible for handling the file input and output for the ChaosGame. It will read
 * the file and create the game. It will also write the game to a file.
 *
 * @author Sigurd Riseth, Theodor Sjetnan Utvik
 * @version 0.0.1
 */
public class ChaosGameFileHandler {

  /**
   * Method to read a ChaosGameDescription from a file. The file should contain the following
   * information: - The type of transformation (JuliaTransform or AffineTransform2D) - The minimum
   * and maximum coordinates for the game - The transformations to be applied to the points in the
   * game. The file should be formatted as follows: <br>
   * <br>
   * <b>AffineTransform2D:</b>
   *
   * <p>Affine2D <br>
   * minX, minY <br>
   * maxX, maxY <br>
   * a00, a01, a10, a11, b0, b1 #1. Transform<br>
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
   * @param path the path to the file
   * @return the ChaosGameDescription
   */
  public static ChaosGameDescription readFromFile(String path) {
    ArrayList<Transform2D> transform2Ds = new ArrayList<>();
    ChaosGameDescription gameDescription = null;
    try (Scanner scanner = new Scanner(Files.newBufferedReader(Path.of(path)))) {
      scanner.useLocale(Locale.US);
      scanner.useDelimiter(
          ",|#(?<=#).+"); // Split on comma or # and everything following it
      String type = scanner.next().trim();

      Vector2D minCoords = readVector2D(scanner);
      Vector2D maxCoords = readVector2D(scanner);

      while (scanner.hasNext()) {
        Transform2D transform = readTransform(scanner, type);
        if (transform != null) {
          transform2Ds.add(transform);
        }
      }
      gameDescription = new ChaosGameDescription(minCoords, maxCoords, transform2Ds);
    } catch (IOException e) {
      System.err.println("Failed to read the file: " + e.getMessage());
    }
    return gameDescription;
  }

  /**
   * Reads a specific transformation type based on the type identifier provided and constructs the
   * corresponding Transform2D object.
   *
   * @param scanner The Scanner to read the transformation data from.
   * @param type    The type of the transformation to be read.
   * @return A Transform2D object corresponding to the specified type.
   */
  private static Transform2D readTransform(Scanner scanner, String type) {
    try {
      return switch (type) {
        case "Julia" -> readJuliaTransform(scanner);
        case "Affine2D" -> readAffineTransform2D(scanner);
        default -> throw new IllegalArgumentException("Unsupported transform type: " + type);
      };
    } catch (IllegalArgumentException e) {
      System.err.println("Failed to create transform: " + e.getMessage());
      return null;
    }
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
        throw new IllegalArgumentException("Unknown transform type");
      }
      writeTransformType(writer, stringType);
      writeMinMaxCoords(writer, description);
      for (Transform2D transform : description.getTransforms()) {
        writer.write(transform.toString() + " # transforms \n");
      }
    } catch (IOException e) {
      System.out.println("Failed to create the file: " + e.getMessage());
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
              "%f, %f # Lower Left %n", description.getMinCoords().getX0(), description.getMinCoords().getX1()));
      writer.write(
          String.format(
              "%f, %f # Upper Right %n", description.getMaxCoords().getX0(), description.getMaxCoords().getX1()));
    } catch (IOException e) {
      System.err.println("Failed to write the min/max coordinates: " + e.getMessage());
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
      System.err.println("Failed to write the transform type: " + e.getMessage());
    }
  }

  /**
   * Parses a {@link Vector2D} from the given scanner.
   *
   * @param scanner The scanner to parse the vector from.
   * @return A new {@code Vector2D} instance based on the parsed values.
   */
  private static Vector2D readVector2D(Scanner scanner) {
    var x0 = Double.parseDouble(scanner.next().trim());
    var x1 = Double.parseDouble(scanner.next().trim());
    return new Vector2D(x0, x1);
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
   */
  private static Matrix2x2 readMatrix2x2(Scanner scanner) {
    var a00 = Double.parseDouble(scanner.next().trim());
    var a01 = Double.parseDouble(scanner.next().trim());
    var a10 = Double.parseDouble(scanner.next().trim());
    var a11 = Double.parseDouble(scanner.next().trim());
    return new Matrix2x2(a00, a01, a10, a11);
  }

  /**
   * Reads and constructs a JuliaTransform from the provided Scanner object.
   *
   * @param scanner The Scanner to read the JuliaTransform data from.
   * @return A new JuliaTransform object.
   */
  private static Transform2D readJuliaTransform(Scanner scanner) {
    var real = readDouble(scanner);
    var imaginary = readDouble(scanner);
    return new JuliaTransform(new Complex(real, imaginary), -1);
  }

  /**
   * Reads and constructs an AffineTransform2D from the provided Scanner object.
   *
   * @param scanner The Scanner to read the AffineTransform2D data from.
   * @return A new AffineTransform2D object.
   */
  private static Transform2D readAffineTransform2D(Scanner scanner) {
    return new AffineTransform2D(readMatrix2x2(scanner), readVector2D(scanner));
  }

}