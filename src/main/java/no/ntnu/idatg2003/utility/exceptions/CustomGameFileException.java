package no.ntnu.idatg2003.utility.exceptions;

/**
 * Custom exception for handling exceptions related to custom game files.
 */
public class CustomGameFileException extends RuntimeException {

  /**
   * Constructs a new CustomGameFileException with the specified detail message.
   * @param message the detail message
   * @param cause the cause
   */
  public CustomGameFileException(String message, Throwable cause) {
    super(message, cause);
  }
}