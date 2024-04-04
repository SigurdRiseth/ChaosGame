package no.ntnu.idatg2003.game_engine;

import java.util.List;
import no.ntnu.idatg2003.math_datatypes.Complex;
import no.ntnu.idatg2003.math_datatypes.Matrix2x2;
import no.ntnu.idatg2003.math_datatypes.Vector2D;
import no.ntnu.idatg2003.transformations.AffineTransform2D;
import no.ntnu.idatg2003.transformations.JuliaTransform;
import no.ntnu.idatg2003.transformations.Transform2D;

public class ChaosGameDescriptionFactory {

  public static ChaosGameDescription createBarnsleyFern() {
    List<Transform2D> transforms = List.of(
        new AffineTransform2D(new Matrix2x2(0.0, 0.0, 0.0, 0.16), new Vector2D(0.0, 0.0)),
        new AffineTransform2D(new Matrix2x2(0.85, 0.04, -0.04, 0.85), new Vector2D(0.0, 1.6)),
        new AffineTransform2D(new Matrix2x2(0.2, -0.26, 0.23, 0.22), new Vector2D(0.0, 1.6)),
        new AffineTransform2D(new Matrix2x2(-0.15, 0.28, 0.26, 0.24), new Vector2D(0.0, 0.44))
    );
    Vector2D min = new Vector2D(-2.65, 0.0);
    Vector2D max = new Vector2D(2.65, 10);
    return new ChaosGameDescription(min, max, transforms);
  }

  public static ChaosGameDescription createSierpinskiTriangle() {
    List<Transform2D> transforms = List.of(
        new AffineTransform2D(new Matrix2x2(0.5, 0.0, 0.0, 0.5), new Vector2D(0.0, 0.0)),
        new AffineTransform2D(new Matrix2x2(0.5, 0.0, 0.0, 0.5), new Vector2D(0.25, 0.5)),
        new AffineTransform2D(new Matrix2x2(0.5, 0.0, 0.0, 0.5), new Vector2D(0.5, 0.0))
    );
    Vector2D min = new Vector2D(0.0, 0.0);
    Vector2D max = new Vector2D(1.0, 1.0);
    return new ChaosGameDescription(min, max, transforms);
  }

  public static ChaosGameDescription createJuliaSet() {
    List<Transform2D> transforms = List.of(
        new JuliaTransform(new Complex(-.74543, .11301), -1)
    );
    Vector2D min = new Vector2D(-1, -0.6);
    Vector2D max = new Vector2D(0, 0.6);
    return new ChaosGameDescription(min, max, transforms);
  }

}