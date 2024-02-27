package no.ntnu.idatg2003;

import java.util.List;
import no.ntnu.idatg2003.game_engine.ChaosGame;
import no.ntnu.idatg2003.game_engine.ChaosGameDescription;
import no.ntnu.idatg2003.math_datatypes.Matrix2x2;
import no.ntnu.idatg2003.math_datatypes.Vector2D;
import no.ntnu.idatg2003.transformations.AffineTransform2D;
import no.ntnu.idatg2003.transformations.JuliaTransform;
import no.ntnu.idatg2003.transformations.Transform2D;
import no.ntnu.idatg2003.math_datatypes.Complex;
import no.ntnu.idatg2003.ui.Ui;

public class Main {
  public static void main(String[] args) {
    System.out.println("Hello, World!");

    AffineTransform2D affine1 = new AffineTransform2D(new Matrix2x2(0.5, 0, 0, 0.5), new Vector2D(0, 0));
    AffineTransform2D affine2 = new AffineTransform2D(new Matrix2x2(0.5, 0, 0, 0.5), new Vector2D(0.5, 0));
    AffineTransform2D affine3 = new AffineTransform2D(new Matrix2x2(0.5, 0, 0, 0.5), new Vector2D(0.25, 0.5));

    List<Transform2D> transform = List.of(affine1, affine2, affine3);

    ChaosGameDescription description = new ChaosGameDescription(
        new Vector2D(0, 0), new Vector2D(10, 10), transform);

    ChaosGame game = new ChaosGame(description, 10, 10);

    game.runSteps(10);

    Ui.print(game.getCanvas().getCanvasArray());

  }
}
