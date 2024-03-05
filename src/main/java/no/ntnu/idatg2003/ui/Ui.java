package no.ntnu.idatg2003.ui;

import java.util.ArrayList;
import java.util.List;
import no.ntnu.idatg2003.game_engine.ChaosGame;
import no.ntnu.idatg2003.game_engine.ChaosGameDescription;
import no.ntnu.idatg2003.game_engine.ChaosGameFileHandler;
import no.ntnu.idatg2003.math_datatypes.Complex;
import no.ntnu.idatg2003.math_datatypes.Matrix2x2;
import no.ntnu.idatg2003.math_datatypes.Vector2D;
import no.ntnu.idatg2003.transformations.AffineTransform2D;
import no.ntnu.idatg2003.transformations.JuliaTransform;
import no.ntnu.idatg2003.transformations.Transform2D;

/**
 * This class is responsible for handling the user interface for the ChaosGame. It will prompt the
 * user to choose what they want to do, and then run the game or create a new game file.
 *
 * @author Sigurd Riseth, Theodor Sjetnan Utvik
 * @version 0.0.1
 * @see ChaosGame
 * @see ChaosGameDescription
 * @see ChaosGameFileHandler
 * @since 05.03.2024
 */
public class Ui {

  /**
   * Method to print the canvas to the console. The canvas is represented as a 2D array of integers.
   * If the value is 1, the point will be drawn. If the value is 0, the point will not be drawn.
   *
   * @param canvas the canvas to be printed
   */
  public static void print(int[][] canvas) {
    for (int i = 0; i < canvas.length; i++) {
      for (int j = 0; j < canvas[i].length; j++) {
        if (canvas[i][j] == 1) {
          System.out.print("X");
        } else {
          System.out.print(" ");
        }
      }
      System.out.println();
    }
  }

  /**
   * Method to start the UI. The user will be prompted to choose what they want to do. They can run
   * a game, create a new game file, or exit the program.
   */
  public void start() {
    System.out.println("Welcome to the Chaos Game!");
    boolean running = true;

    while (running) {
      System.out.println("What would you like to do?");
      System.out.println("1. Run a game");
      System.out.println("2. Create new game file");
      System.out.println("0. Exit");
      int input = new InputHandler().readInt();

      switch (input) {
        case 1:
          runGame();
          break;
        case 2:
          createGameFile();
          break;
        case 0:
          running = false;
          break;
        default:
          System.out.println("Invalid input");
          break;
      }
    }
  }

  /**
   * Method to create a new game file. The user will be prompted to enter the type of transformation
   * they want to create, and then the file name. The file will be saved in the resources folder.
   */
  private void
      createGameFile() { // TODO: Add input validation. Catch exceptions and allow users to return
    // to main menu.
    ChaosGameDescription description = null;
    System.out.println("What type of transformation do you want to create?");
    System.out.println("1. Affine2D");
    System.out.println("2. Julia");
    System.out.println("Press enter to return to main menu");
    System.out.println("Enter your choice: ");

    int input = new InputHandler().readInt();
    switch (input) {
      case 1:
        description = createAffine2D();
        break;
      case 2:
        description = createJulia();
        break;
      default:
        System.out.println("Invalid input");
        break;
    }

    System.out.println("Enter the file name: ");
    String fileName = new InputHandler().readString();
    String filePath = "src/main/resources/" + fileName + ".csv";

    if (description != null) {
      ChaosGameFileHandler.writeToFile(description, filePath);
    } else {
      System.out.println("Failed to create game file");
    }
  }

  /**
   * Method to create a new Julia transformation. The user will be prompted to enter the min and max
   * coordinates for the game, and the real and imaginary part of the complex constant.
   *
   * @return ChaosGameDescription for the Julia transformation
   */
  private ChaosGameDescription createJulia() {
    System.out.println("Enter the min x value: ");
    double minX = new InputHandler().readDouble();
    System.out.println("Enter the min y value: ");
    double minY = new InputHandler().readDouble();
    System.out.println("Enter the max x value: ");
    double maxX = new InputHandler().readDouble();
    System.out.println("Enter the max y value: ");
    double maxY = new InputHandler().readDouble();

    Vector2D minCoords = new Vector2D(minX, minY);
    Vector2D maxCoords = new Vector2D(maxX, maxY);

    System.out.println("Enter the real part of the complex constant: ");
    double re = new InputHandler().readDouble();
    System.out.println("Enter the imaginary part of the complex constant: ");
    double im = new InputHandler().readDouble();
    List<Transform2D> transform2Ds = new ArrayList<>();
    transform2Ds.add(new JuliaTransform(new Complex(re, im), 1));
    return new ChaosGameDescription(minCoords, maxCoords, transform2Ds);
  }

  /**
   * Method to create a new Affine2D transformation. The user will be prompted to enter the min and
   * max coordinates for the game, and the number of transforms they want to create. They will then
   * be prompted to enter the values for each transform.
   *
   * @return ChaosGameDescription for the Affine2D transformation
   */
  private ChaosGameDescription createAffine2D() {
    System.out.println("Enter the min x value: ");
    double minX = new InputHandler().readDouble();
    System.out.println("Enter the min y value: ");
    double minY = new InputHandler().readDouble();
    System.out.println("Enter the max x value: ");
    double maxX = new InputHandler().readDouble();
    System.out.println("Enter the max y value: ");
    double maxY = new InputHandler().readDouble();

    Vector2D minCoords = new Vector2D(minX, minY);
    Vector2D maxCoords = new Vector2D(maxX, maxY);

    System.out.println("Enter the number of transforms: ");
    int numTransforms = new InputHandler().readInt();
    List<Transform2D> transform2Ds = new ArrayList<>();
    for (int i = 0; i < numTransforms; i++) {
      System.out.println("Enter the a00 value: ");
      double a00 = new InputHandler().readDouble();
      System.out.println("Enter the a01 value: ");
      double a01 = new InputHandler().readDouble();
      System.out.println("Enter the a10 value: ");
      double a10 = new InputHandler().readDouble();
      System.out.println("Enter the a11 value: ");
      double a11 = new InputHandler().readDouble();
      System.out.println("Enter the b0 value: ");
      double b0 = new InputHandler().readDouble();
      System.out.println("Enter the b1 value: ");
      double b1 = new InputHandler().readDouble();
      transform2Ds.add(
          new AffineTransform2D(new Matrix2x2(a00, a01, a10, a11), new Vector2D(b0, b1)));
    }

    return new ChaosGameDescription(minCoords, maxCoords, transform2Ds);
  }

  /**
   * Method to run a game. The user will be prompted to choose which game they want to run, and then
   * the game will be run.
   */
  public void runGame() {
    boolean running = true;

    while (running) {
      System.out.println("Which game do you want to run?");
      System.out.println("1. Sierpinski");
      System.out.println("2. Barnsley Fern");
      System.out.println("3. Julia");
      System.out.println("0. Return to main menu");
      int input = new InputHandler().readInt();

      switch (input) {
        case 1:
          runSierpinski();
          break;
        case 2:
          runBarnsleyFern();
          break;
        case 3:
          runJulia();
          break;
        case 0:
          running = false;
          break;
        default:
          System.out.println("Invalid input");
          break;
      }
    }
  }

  /** Method to run the Sierpinski game. */
  private void runSierpinski() {
    ChaosGameDescription description =
        ChaosGameFileHandler.readFromFile("src/main/resources/Sierpinski.csv");

    ChaosGame game = new ChaosGame(description, 50 * 3, 50);

    game.runSteps(100000);
    print(game.getCanvas().getCanvasArray());
  }

  /** Method to run the Barnsley Fern game. */
  private void runBarnsleyFern() {
    ChaosGameDescription description =
        ChaosGameFileHandler.readFromFile("src/main/resources/barnsley-fern.csv");

    ChaosGame game = new ChaosGame(description, 50 * 3, 50);

    game.runSteps(100000);
    print(game.getCanvas().getCanvasArray());
  }

  /** Method to run the Julia game. */
  private void runJulia() {
    ChaosGameDescription description =
        ChaosGameFileHandler.readFromFile("src/main/resources/Julia.csv");

    ChaosGame game = new ChaosGame(description, 50 * 3, 50);

    game.runSteps(100000);
    print(game.getCanvas().getCanvasArray());
  }
}
