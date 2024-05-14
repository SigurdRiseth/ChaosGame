package no.ntnu.idatg2003.utility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Utility class for logging. This class includes methods used to log messages for:
 * <ul>
 *   <li>Information</li>
 *   <li>Error</li>
 *   <li>Debug</li>
 *   <li>Warning</li>
 *   <li>Fatal</li>
 * </ul>
 *
 * @since 08.05.2024
 * @version 0.0.1
 * @author Theodor Sjetnan Utvik, Sigurd Riseth
 */
public class LoggerUtil {

  private static final Logger logger = LogManager.getLogger(LoggerUtil.class);

  /**
   * Private constructor to prevent instantiation of this class.
   */
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

  /**
   * Logs an error message.
   *
   * @param message The message to log.
   */
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

  /**
   * Logs a warning message.
   *
   * @param message The message to log.
   */
  public static void logWarning(String message) {
    logger.warn(message);
  }

  /**
   * Logs a fatal message.
   *
   * @param message The message to log.
   */
  public static void logFatal(String message) {
    logger.fatal(message);
  }
}
