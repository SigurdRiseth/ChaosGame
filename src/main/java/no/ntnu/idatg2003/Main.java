package no.ntnu.idatg2003;

import no.ntnu.idatg2003.game_engine.ChaosGame;
import no.ntnu.idatg2003.game_engine.ChaosGameDescription;
import no.ntnu.idatg2003.game_engine.ChaosGameFileHandler;

import no.ntnu.idatg2003.ui.Ui;

public class Main {
  public static void main(String[] args) {



    ChaosGameDescription description1 = ChaosGameFileHandler.readFromFile("src/main/resources/Julia.csv");

    ChaosGame game1 = new ChaosGame(description1, 50*3, 50);

    game1.runSteps(100000);
    Ui.print(game1.getCanvas().getCanvasArray());


    /*

    JuliaTransform julia = new JuliaTransform(new Complex(0.3, 0.6), 1);

    List<Transform2D> transform2 = List.of(julia);

    ChaosGameDescription description2 = new ChaosGameDescription(
        new Vector2D(-1, -1), new Vector2D(1, 1), transform2);

    ChaosGame game2 = new ChaosGame(description2, 50, 50);

    game2.runSteps(100);

    Ui.print(game2.getCanvas().getCanvasArray());
     */

  }
}
