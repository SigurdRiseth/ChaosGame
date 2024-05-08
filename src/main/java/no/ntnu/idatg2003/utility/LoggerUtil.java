package no.ntnu.idatg2003.utility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Utility class for logging. This class includes methods used to log messages for:
 * <li>Information</li>
 * <li>Error</li>
 * <li>Debug</li>
 *
 * @since 08.05.2024
 * @version 0.0.1
 * @author Theodor Sjetnan Utvik, Sigurd Riseth
 */

public class LoggerUtil {

  private static final Logger logger = LogManager.getLogger(LoggerUtil.class);

  private LoggerUtil() {}

  /**
   * Logs an information message.
   *
   * @param message The message to log.
   */
  public static void logInfo(String message) {
    logger.info(message);
  }

  /**
   * Logs an error message.
   *
   * @param message The message to log.
   * @param throwable The throwable to log.
   */
  public static void logError(String message, Throwable throwable) {
    logger.error(message, throwable);
  }

  public static void logError(String message) {
    logger.error(message);
  }

  /**
   * Logs a debug message.
   *
   * @param message The message to log.
   */
  public static void logDebug(String message) {
    logger.debug(message);
  }


  public static void logWarning(String message) {
    logger.warn(message);
  }
}
