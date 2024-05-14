package no.ntnu.idatg2003.game.engine;

import static org.junit.jupiter.api.Assertions.*;

import no.ntnu.idatg2003.model.game.engine.ChaosCanvas;
import no.ntnu.idatg2003.model.math.datatypes.Vector2D;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ChaosCanvasTest {

  private ChaosCanvas chaosCanvas;

  @BeforeEach
  void setUp() {
    // Assuming the ChaosCanvas constructor takes (minCoords, maxCoords, width, height)
    chaosCanvas = new ChaosCanvas(new Vector2D(0,0), new Vector2D(10,10), 10, 10);
  }

  @AfterEach
  void tearDown() {
    chaosCanvas = null;
  }

  @Test
  void putPixel() {
    // Ensure putPixel is supposed to set the value at [5][5] to 1
    chaosCanvas.putPixel(new Vector2D(5,5));
    assertEquals(1, chaosCanvas.getCanvasArray()[4][4], // TODO: DETTE ER VEL FEIL, skulle det ikke være [5][5]?
        "The pixel value at (5,5) should be 1 after putPixel is called.");
  }

  @Test
  void clear() {
    // Assuming clear should reset all pixels to a default value, e.g., 0
    chaosCanvas.putPixel(new Vector2D(5,5)); // Set a pixel to ensure clear has an effect
    chaosCanvas.clear(); // Clear the canvas
    assertEquals(0, chaosCanvas.getCanvasArray()[5][5],
        "The pixel value at (5,5) should be reset to 0 after clear is called.");
  }
}
