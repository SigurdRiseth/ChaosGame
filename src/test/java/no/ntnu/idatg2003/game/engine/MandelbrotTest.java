package no.ntnu.idatg2003.game.engine;

import static org.junit.jupiter.api.Assertions.assertThrows;

import no.ntnu.idatg2003.model.game.engine.Mandelbrot;
import org.junit.jupiter.api.Test;

class MandelbrotTest {

  @Test
  void zeroWidthAndHeightThrowsException() {
    assertThrows(IllegalArgumentException.class, () -> new Mandelbrot(0, 10));
    assertThrows(IllegalArgumentException.class, () -> new Mandelbrot(10, 0));
  }

  @Test
  void registerNullObserverThrowsException() {
    Mandelbrot mandelbrot = new Mandelbrot(10, 10);
    assertThrows(NullPointerException.class, () -> mandelbrot.registerObserver(null));
  }

  @Test
  void unregisterNullObserverThrowsException() {
    Mandelbrot mandelbrot = new Mandelbrot(10, 10);
    assertThrows(NullPointerException.class, () -> mandelbrot.removeObserver(null));
  }
}
