package no.ntnu.idatg2003.game_engine;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;
import no.ntnu.idatg2003.math_datatypes.Complex;
import no.ntnu.idatg2003.math_datatypes.Matrix2x2;
import no.ntnu.idatg2003.math_datatypes.Vector2D;
import no.ntnu.idatg2003.transformations.AffineTransform2D;
import no.ntnu.idatg2003.transformations.JuliaTransform;
import no.ntnu.idatg2003.transformations.Transform2D;

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
   * game. The file should be formatted as follows:
   * <br> <br>
   * <b>AffineTransform2D:</b>
   * <p>Affine2D <br>
   * minX, minY <br> maxX, maxY <br> a00, a01, a10, a11, b0, b1 #1. Transform<br> a00, a01, a10,
   * a11, b0, b1 #2. Transform<br> ...<br> a00, a01, a10, a11, b0, b1 #n. Transform<br>
   * </p>
   * <br>
   * <b>JuliaTransform:</b>
   * <p>Julia <br>
   * minX, minY <br> maxX, maxY <br> Re, Im #Transform<br>
   *
   * @param path the path to the file
   * @return the ChaosGameDescription
   */
  public static ChaosGameDescription readFromFile(String path) {
    ArrayList<Transform2D> transform2Ds = new ArrayList<>();
    ChaosGameDescription gameDescription = null;
    try (Scanner scanner = new Scanner(Files.newBufferedReader(Path.of(path)))) {
      scanner.useDelimiter(
          ",|#(?<=[#]).{1,}"); // Split on comma, remove # and everything after it, and split on new lines
      int sign = 1;
      String type = scanner.next().trim();

      double minX = Double.parseDouble(scanner.next().trim());
      double minY = Double.parseDouble(scanner.next().trim());
      Vector2D minCoords = new Vector2D(minX, minY);

      double maxX = Double.parseDouble(scanner.next().trim());
      double maxY = Double.parseDouble(scanner.next().trim());
      Vector2D maxCoords = new Vector2D(maxX, maxY);

      while (scanner.hasNext()) {
        try {
          if (type.equals("Julia")) {
            double re = Double.parseDouble(scanner.next().trim());
            double im = Double.parseDouble(scanner.next().trim());
            transform2Ds.add(new JuliaTransform(new Complex(re, im), sign));
          } else if (type.equals("Affine2D")) {
            double a00 = Double.parseDouble(scanner.next().trim());
            double a01 = Double.parseDouble(scanner.next().trim());
            double a10 = Double.parseDouble(scanner.next().trim());
            double a11 = Double.parseDouble(scanner.next().trim());
            double b0 = Double.parseDouble(scanner.next().trim());
            double b1 = Double.parseDouble(scanner.next().trim());
            transform2Ds.add(new AffineTransform2D(new Matrix2x2(a00, a01, a10, a11),
                new Vector2D(b0, b1)));
          }
        } catch (Exception e) {
          System.out.println("Failed to create transform2D: " + e.getMessage());
        }
      }
      gameDescription = new ChaosGameDescription(minCoords, maxCoords, transform2Ds);
    } catch (IOException e) {
      System.out.println("Failed to read the file: " + e.getMessage());
    }
    return gameDescription;
  }

  /**
   * Method to write a ChaosGameDescription to a file. The file will be in the format:
   * <br> <br>
   * <b>AffineTransform2D:</b>
   * <p>Affine2D <br>
   * maxX, maxY <br> a00, a01, a10, a11, b0, b1 #1. Transform<br> minX, minY <br> a00, a01, a10,
   * a11, b0, b1 #2. Transform<br> ...<br> a00, a01, a10, a11, b0, b1 #n. Transform<br>
   * </p>
   * <br>
   * <b>JuliaTransform:</b>
   * <p>Julia <br>
   * minX, minY <br> maxX, maxY <br> Re, Im #Transform<br>
   * </p>
   *
   * @param description the ChaosGameDescription to write to the file
   * @param path        the path to the file
   */
  public static void writeToFile(ChaosGameDescription description, String path) {
    try (BufferedWriter writer = Files.newBufferedWriter(Path.of(path))) {
      Transform2D type = description.getTransforms().getFirst();
      String stringType;
      if (type instanceof AffineTransform2D) {
        stringType = "AffineTransform2D";
      } else if (type instanceof JuliaTransform) {
        stringType = "JuliaTransform";
      } else {
        throw new IllegalArgumentException("Unknown transform type");
      }
      writer.write(stringType + "\n");
      writer.write(String.format("%f, %f%n", description.getMinCoords().getX0(),
          description.getMinCoords().getX1()));
      writer.write(String.format("%f, %f%n", description.getMaxCoords().getX0(),
          description.getMaxCoords().getX1()));
      for (Transform2D transform : description.getTransforms()) {
        writer.write(transform.toString() + "\n");
      }
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }


}
