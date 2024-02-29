package no.ntnu.idatg2003.game_engine;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import no.ntnu.idatg2003.math_datatypes.Complex;
import no.ntnu.idatg2003.math_datatypes.Matrix2x2;
import no.ntnu.idatg2003.math_datatypes.Vector2D;
import no.ntnu.idatg2003.transformations.AffineTransform2D;
import no.ntnu.idatg2003.transformations.JuliaTransform;
import no.ntnu.idatg2003.transformations.Transform2D;

/**
 * This class is responsible for handling the file input and output for the ChaosGame.
 * It will read the file and create the game. It will also write the game to a file.
 *
 * @version 0.0.1
 * @author Sigurd Riseth, Theodor Sjetnan Utvik
 */

public class ChaosGameFileHandler {

  public ChaosGameDescription readFromFile(String path) {
    ArrayList<Transform2D> transform2Ds = new ArrayList<>();
    ChaosGameDescription gameDescription = null;
    try (Scanner scanner = new Scanner(Path.of(path))) {
      scanner.useDelimiter("[,\n]");
      boolean firstIteration = true;
      String type = null;
      int sign = 1;
      Vector2D minCoords = null;
      Vector2D maxCoords = null;
      while (scanner.hasNext()) {
        if (firstIteration) {
          type = scanner.next();
          minCoords = new Vector2D(scanner.nextDouble(), scanner.nextDouble());
          maxCoords = new Vector2D(scanner.nextDouble(), scanner.nextDouble());
          firstIteration = false;
        }
        if (type.equals("JuliaTransform")) {
          transform2Ds.add((new JuliaTransform(new Complex(scanner.nextDouble(), scanner.nextDouble()), sign)));
        } else if (type.equals("AffineTransform2D")) {
          transform2Ds.add(new AffineTransform2D(
              new Matrix2x2(scanner.nextDouble(), scanner.nextDouble(), scanner.nextDouble(), scanner.nextDouble()),
              new Vector2D(scanner.nextDouble(), scanner.nextDouble())));
        }
      }
      gameDescription = new ChaosGameDescription(minCoords, maxCoords, transform2Ds);
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
    return gameDescription;
  }

  public static void writeToFile(ChaosGameDescription description, String path) {
    try (BufferedWriter writer = Files.newBufferedWriter(Path.of(path))) {
      Transform2D type = description.getTransforms().get(0);
      String stringType;
      if (type instanceof AffineTransform2D) {
        stringType = "AffineTransform2D";
      } else if (type instanceof JuliaTransform) {
        stringType = "JuliaTransform";
      } else {
        throw new IllegalArgumentException("Unknown transform type");
      }
      writer.write(stringType + "\n");
      writer.write(String.format("%f, %f%n", description.getMinCoords().getX0(), description.getMinCoords().getX1()));
      writer.write(String.format("%f, %f%n", description.getMaxCoords().getX0(), description.getMaxCoords().getX1()));
      writer.write(description.getMaxCoords().toString() + "\n");
      for (Transform2D transform : description.getTransforms()) {
        writer.write(transform.toString() + "\n");
      }
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }


}
