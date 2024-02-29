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

    AffineTransform2D affine1 = new AffineTransform2D(new Matrix2x2(0.5, 0, 0, 0.5), new Vector2D(0, 0));
    AffineTransform2D affine2 = new AffineTransform2D(new Matrix2x2(0.5, 0, 0, 0.5), new Vector2D(5, 0));
    AffineTransform2D affine3 = new AffineTransform2D(new Matrix2x2(0.5, 0, 0, 0.5), new Vector2D(2.5, 5));

    List<Transform2D> transform1 = List.of(affine1, affine2, affine3);

    ChaosGameDescription description1 = new ChaosGameDescription(
        new Vector2D(0, 0), new Vector2D(10, 10), transform1);

    ChaosGame game1 = new ChaosGame(description1, 50*3, 50);

    game1.runSteps(100000);

    JuliaTransform julia = new JuliaTransform(new Complex(0.3, 0.6), 1);

    List<Transform2D> transform2 = List.of(julia);

    ChaosGameDescription description2 = new ChaosGameDescription(
        new Vector2D(-1, -1), new Vector2D(1, 1), transform2);

    ChaosGame game2 = new ChaosGame(description2, 50, 50);

    game2.runSteps(100);

    Ui.print(game1.getCanvas().getCanvasArray());
    Ui.print(game2.getCanvas().getCanvasArray());

  }
}
