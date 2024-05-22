package no.ntnu.idatg2003.model.file.handling;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.NoSuchElementException;
import no.ntnu.idatg2003.model.game.engine.ChaosGameDescription;
import no.ntnu.idatg2003.model.transformations.Transform2D;
import no.ntnu.idatg2003.utility.enums.TransformType;
import no.ntnu.idatg2003.utility.logging.LoggerUtil;

/**
 * This class is responsible for writing a ChaosGameDescription to text files.
 */
public class ChaosGameTextFileWriter implements ChaosGameFileHandler.ChaosGameFileWriter {

  /**
   * Determines the type of transformation used in the given ChaosGameDescription.
   *
   * @param description the ChaosGameDescription to determine the transform type of
   * @return the TransformType enum representing the transform type
   * @throws IllegalArgumentException if the ChaosGameDescription is empty or if the transformation
   *                                  type is unknown
   */
  private static TransformType getTransformType(ChaosGameDescription description) {
    try {
      Transform2D firstTransform = description.getTransforms().getFirst();
      return firstTransform.getType();
    } catch (NoSuchElementException e) {
      LoggerUtil.logError("ChaosGameDescription is empty.");
      throw new IllegalArgumentException("ChaosGameDescription is empty");
    } catch (NullPointerException | IllegalArgumentException e) {
      LoggerUtil.logError("Unknown or unsupported transform type encountered.");
      throw new IllegalArgumentException("Unknown or unsupported transform type");
    }
  }

  /**
   * Writes the transform type to the provided BufferedWriter instance.
   *
   * @param writer the BufferedWriter instance to write the transform type to
   * @param type   the transform type to be written
   */
  private static void writeTransformType(BufferedWriter writer, TransformType type) {
    try {
      writer.write(type.getTypeString() + " # Type of fractal \n");
    } catch (IOException e) {
      LoggerUtil.logError("Failed to write the transform type: " + e.getMessage());
    }
  }

  /**
   * Writes the minimum and maximum coordinates to the provided BufferedWriter instance.
   *
   * @param writer      the BufferedWriter instance to write the coordinates to
   * @param description the ChaosGameDescription containing the minimum and maximum coordinates
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
   * Writes a ChaosGameDescription to a file.
   * <p>
   * This method writes the provided ChaosGameDescription to a file specified by the given path. The
   * file will be formatted according to the type of transformations in the ChaosGameDescription. If
   * the description contains AffineTransform2D transformations, they will be written with the
   * format:
   * </p>
   * <pre>{@code
   * AffineTransform2D:
   * minX, minY
   * maxX, maxY
   * a00, a01, a10, a11, b0, b1 #1. Transform
   * minX, minY
   * a00, a01, a10, a11, b0, b1 #2. Transform
   * ...
   * a00, a01, a10, a11, b0, b1 #n. Transform
   * }</pre>
   * <p>
   * If the description contains JuliaTransform, it will be written with the format:
   * </p>
   * <pre>{@code
   * JuliaTransform:
   * minX, minY
   * maxX, maxY
   * Re, Im #Transform
   * }</pre>
   *
   * @param description the ChaosGameDescription to write to the file
   * @param path        the path to the file
   * @throws IllegalArgumentException if the transform type is unknown
   */
  @Override
  public void writeToFile(ChaosGameDescription description, String path) {
    try (BufferedWriter writer = Files.newBufferedWriter(Path.of(path))) {
      TransformType type = getTransformType(description);

      writeTransformType(writer, type);
      writeMinMaxCoords(writer, description);
      for (Transform2D transform : description.getTransforms()) {
        writer.write("\n" + transform.toString() + " # transforms");
        LoggerUtil.logInfo("Transform written to file");
      }
    } catch (IOException e) {
      LoggerUtil.logError("Failed to create the file: " + e.getMessage());
    }
  }
}
