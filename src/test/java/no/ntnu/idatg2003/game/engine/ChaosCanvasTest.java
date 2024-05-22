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
    chaosCanvas = new ChaosCanvas(new Vector2D(0,0), new Vector2D(10,10), 10, 10);
  }

  @AfterEach
  void tearDown() {
    chaosCanvas = null;
  }

  @Test
  void constructor() {
    assertNotNull(chaosCanvas, "The ChaosCanvas object should not be null after construction.");
  }

  @Test
  void constructorNullCoords() {
    Vector2D minCoords = new Vector2D(0,0);
    Vector2D maxCoords = new Vector2D(10,10);
    assertThrows(NullPointerException.class, () -> new ChaosCanvas(null, maxCoords, 10, 10),
        "The constructor should throw a NullPointerException when the minCoords parameter is null.");
    assertThrows(NullPointerException.class, () -> new ChaosCanvas(minCoords, null, 10, 10),
        "The constructor should throw a NullPointerException when the maxCoords parameter is null.");
  }

  @Test
  void constructorNegativeDimensions() {
    Vector2D minCoords = new Vector2D(0,0);
    Vector2D maxCoords = new Vector2D(10,10);
    assertThrows(IllegalArgumentException.class, () -> new ChaosCanvas(minCoords, maxCoords, -10, 10),
        "The constructor should throw an IllegalArgumentException when the width parameter is negative.");
    assertThrows(IllegalArgumentException.class, () -> new ChaosCanvas(minCoords, maxCoords, 10, -10),
        "The constructor should throw an IllegalArgumentException when the height parameter is negative.");
  }

  @Test
  void putPixel() {
    chaosCanvas.putPixel(new Vector2D(5,5));
    assertEquals(1, chaosCanvas.getCanvasArray()[5][5],
        "The pixel value at (5,5) should be 1 after putPixel is called.");
  }

  @Test
  void getPixel() {
    chaosCanvas.putPixel(new Vector2D(5,5));
    assertEquals(1, chaosCanvas.getPixel(new Vector2D(5,5)),
        "The pixel value at (5,5) should be 1 after putPixel is called.");
  }

  @Test
  void clear() {
    chaosCanvas.putPixel(new Vector2D(5,5)); // Set a pixel to ensure clear has an effect
    chaosCanvas.clear(); // Clear the canvas
    assertEquals(0, chaosCanvas.getCanvasArray()[5][5],
        "The pixel value at (5,5) should be reset to 0 after clear is called.");
  }

  @Test
  void putPixelValue() {
    chaosCanvas.putPixel(5, 5, 2);
    assertEquals(2, chaosCanvas.getCanvasArray()[5][5],
        "The pixel value at (5,5) should be 2 after putPixelValue is called.");
  }

  @Test
  void indexOutOfBounds() {
    assertThrows(IndexOutOfBoundsException.class, () -> chaosCanvas.putPixel(100000, 10, 1),
        "putPixel should throw an IndexOutOfBoundsException when the index is out of bounds.");
  }

  @Test
  void indexOutOfBounds2() {
    Vector2D vector = new Vector2D(100000, 10);
    assertThrows(IndexOutOfBoundsException.class, () -> chaosCanvas.putPixel(vector),
        "putPixel should throw an IndexOutOfBoundsException when the index is out of bounds.");
  }
}
