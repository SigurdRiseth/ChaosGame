package no.ntnu.idatg2003.model.game.engine;

import java.util.List;
import no.ntnu.idatg2003.model.math.datatypes.Complex;
import no.ntnu.idatg2003.model.math.datatypes.Matrix2x2;
import no.ntnu.idatg2003.model.math.datatypes.Vector2D;
import no.ntnu.idatg2003.model.transformations.Transform2D;
import no.ntnu.idatg2003.model.transformations.AffineTransform2D;
import no.ntnu.idatg2003.model.transformations.JuliaTransform;

public class ChaosGameDescriptionFactory {

  /**
   * Private constructor to prevent instantiation.
   */
  private ChaosGameDescriptionFactory() {}

  /**
   * Create the Barnsley fern description
   *
   * @return a <code>ChaosGameDescription</code> object
   */
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

  /**
   * Create the Sierpinski triangle description
   *
   * @return a <code>ChaosGameDescription</code> object
   */
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

  /**
   * Create a Julia set description with the default complex number
   *
   * @return a <code>ChaosGameDescription</code> object
   */
  public static ChaosGameDescription createJuliaSet() {
    List<Transform2D> transforms = List.of(
        new JuliaTransform(new Complex(0.285, 0.01), -1),
        new JuliaTransform(new Complex(0.285, 0.01), 1)
        //-0.4, 0.6
    );
    Vector2D min = new Vector2D(-2, -2);
    Vector2D max = new Vector2D(2, 2);
    return new ChaosGameDescription(min, max, transforms);
  }

  /**
   * Create a Julia set description with a given complex number
   *
   * @param complex the complex number to use
   * @return a <code>ChaosGameDescription</code> object
   */
  public static ChaosGameDescription createJuliaSet(Complex complex) {
    List<Transform2D> transforms = List.of(
        new JuliaTransform(complex, -1),
        new JuliaTransform(complex, 1)
    );
    Vector2D min = new Vector2D(-2, -2);
    Vector2D max = new Vector2D(2, 2);
    return new ChaosGameDescription(min, max, transforms);
  }
}