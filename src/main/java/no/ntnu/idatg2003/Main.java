package no.ntnu.idatg2003;

import no.ntnu.idatg2003.game_engine.ChaosGame;
import no.ntnu.idatg2003.game_engine.ChaosGameDescription;
import no.ntnu.idatg2003.game_engine.ChaosGameFileHandler;

import no.ntnu.idatg2003.ui.Ui;

public class Main {
  public static void main(String[] args) {

    ChaosGameDescription description1 = ChaosGameFileHandler.readFromFile("src/main/resources/Sierpinski.csv");

    ChaosGame game1 = new ChaosGame(description1, 50*3, 50);

    game1.runSteps(100000);
    Ui.print(game1.getCanvas().getCanvasArray());

  }
}
