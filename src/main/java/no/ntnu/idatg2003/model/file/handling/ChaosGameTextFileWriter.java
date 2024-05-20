package no.ntnu.idatg2003.model.file.handling;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import no.ntnu.idatg2003.model.game.engine.ChaosGameDescription;
import no.ntnu.idatg2003.model.transformations.AffineTransform2D;
import no.ntnu.idatg2003.model.transformations.JuliaTransform;
import no.ntnu.idatg2003.model.transformations.Transform2D;
import no.ntnu.idatg2003.utility.enums.TransformType;
import no.ntnu.idatg2003.utility.logging.LoggerUtil;

/**
 * This class is responsible for writing a ChaosGameDescription to text files.
 */
public class ChaosGameTextFileWriter implements ChaosGameFileHandler.ChaosGameFileWriter {

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
   * @param description the ChaosGameDescription to write to the file
   * @param path the path to the file
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

  /**
   * Determines the type of transformation used in the ChaosGameDescription.
   *
   * @param description The ChaosGameDescription to determine the transform type of.
   * @return The TransformType enum representing the transform type.
   */
  private static TransformType getTransformType(ChaosGameDescription description) {
    Transform2D transform = description.getTransforms().getFirst();
    TransformType type;
    if (transform instanceof AffineTransform2D) {
      type = TransformType.AFFINE2D;
    } else if (transform instanceof JuliaTransform) {
      type = TransformType.JULIA;
    } else {
      LoggerUtil.logError("Unknown transform type encountered.");
      throw new IllegalArgumentException("Unknown transform type");
    }
    return type;
  }

  /**
   * Writes the transform type to the provided BufferedWriter instance.
   *
   * @param writer The BufferedWriter to write the transform type to.
   * @param type The string representation of the transform type.
   */
  private static void writeTransformType(BufferedWriter writer, TransformType type) {
    try {
      writer.write(type.getTypeString() + " # Type of fractal \n");
    } catch (IOException e) {
      LoggerUtil.logError("Failed to write the transform type: " + e.getMessage());
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
}
