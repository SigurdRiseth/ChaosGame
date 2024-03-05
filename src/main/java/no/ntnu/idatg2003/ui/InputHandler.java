package no.ntnu.idatg2003.ui;

import java.util.Scanner;

/**
 * This class is responsible for handling the input from the user. It will read integers, doubles
 * and strings from the console.
 *
 * @author Sigurd Riseth, Theodor Sjetnan Utvik
 * @since 05.03.2024
 * @version 0.0.1
 */
public class InputHandler {

  private Scanner scanner;

  /** Constructor for the InputHandler class. It initializes the scanner. */
  public InputHandler() {
    scanner = new Scanner(System.in);
  }

  /**
   * Method to read an integer from the console. If the input is not an integer, an exception will
   * be thrown.
   *
   * @throws IllegalArgumentException if the input is not an integer
   * @return the integer
   */
  public int readInt() {
    int input;
    try {
      input = scanner.nextInt();
    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid input");
    }
    return input;
  }

  /**
   * Method to read a string from the console.
   *
   * @return the string
   */
  public String readString() {
    return scanner.next();
  }

  /**
   * Method to read a double from the console. If the input is not a double, an exception will be
   * thrown.
   *
   * @throws IllegalArgumentException if the input is not a double
   * @return the double
   */
  public double readDouble() {
    double input;
    try {
      input = scanner.nextDouble();
    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid input");
    }
    return input;
  }
}
