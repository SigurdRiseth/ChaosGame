package no.ntnu.idatg2003.model.file.handling;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import no.ntnu.idatg2003.model.game.engine.ChaosGameDescription;
import no.ntnu.idatg2003.utility.exceptions.CustomGameFileException;
import no.ntnu.idatg2003.utility.logging.LoggerUtil;

/**
 * This class is responsible for handling the file input and output for the ChaosGame. It delegates
 * the actual reading and writing to specific implementations of ChaosGameFileReader and
 * ChaosGameFileWriter interfaces.
 */
public class ChaosGameFileHandler {

  /**
   * Private constructor to prevent instantiation.
   */
  private ChaosGameFileHandler() {
  }

  /**
   * Reads a ChaosGameDescription from a file using the provided reader implementation.
   *
   * @param reader the reader implementation to use
   * @param path   the path to the file
   * @return the ChaosGameDescription
   */
  public static ChaosGameDescription readFromFile(ChaosGameFileReader reader, String path) {
    try {
      return reader.readFromFile(path);
    } catch (IOException | IllegalArgumentException e) {
      LoggerUtil.logError("Failed to read the file: " + e.getMessage());
      return null;
    }
  }

  /**
   * Writes a ChaosGameDescription to a file using the provided writer implementation.
   *
   * @param writer      the writer implementation to use
   * @param description the ChaosGameDescription to write
   * @param path        the path to the file
   */
  public static void writeToFile(ChaosGameFileWriter writer, ChaosGameDescription description,
      String path) {
    try {
      writer.writeToFile(description, path);
    } catch (IOException | IllegalArgumentException e) {
      LoggerUtil.logError("Failed to write the file: " + e.getMessage());
    }
  }

  /**
   * Returns the names of the custom game files in the user.files directory.
   *
   * @return a list of the custom game file names
   * @throws CustomGameFileException if an error occurs while listing the files
   */
  public static List<String> getCustomGameFileNames() {
    String directoryPath = "src/main/user.files";

    Path directory = Path.of(directoryPath);

    if (!Files.exists(directory) || !Files.isDirectory(directory)) {
      throw new IllegalStateException(
          "Directory does not exist or is not a directory: " + directoryPath);
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

  /**
   * Interface for reading a ChaosGameDescription from a file.
   */
  public interface ChaosGameFileReader {

    /**
     * Reads a ChaosGameDescription from a file.
     *
     * @param path the path to the file. It should be a valid file path pointing to a readable file
     *             containing a ChaosGameDescription.
     * @return the ChaosGameDescription read from the file
     * @throws IllegalArgumentException if the file content is invalid or cannot be parsed into a
     *                                  ChaosGameDescription
     * @throws IOException              if an I/O error occurs during reading the file
     */
    ChaosGameDescription readFromFile(String path) throws IllegalArgumentException, IOException;
  }

  /**
   * Interface for writing a ChaosGameDescription to a file.
   */
  public interface ChaosGameFileWriter {

    /**
     * Writes a ChaosGameDescription to a file.
     *
     * @param description the ChaosGameDescription to write. It should be a valid and fully
     *                    populated description.
     * @param path        the path to the file. It should be a valid file path where the description
     *                    can be written.
     * @throws IllegalArgumentException if the description is invalid or cannot be serialized into a
     *                                  valid file format
     * @throws IOException              if an I/O error occurs during writing to the file
     */
    void writeToFile(ChaosGameDescription description, String path)
        throws IllegalArgumentException, IOException;
  }
}
