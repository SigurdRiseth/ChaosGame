package no.ntnu.idatg2003.utility.enums;

/**
 * Enum for the preset transforms available in the Chaos Game application.
 */
public enum PresetTransforms {
  BARNSLEY_FERN("Barnsley Fern", TransformType.AFFINE2D),
  JULIA_SET("Julia Set", TransformType.JULIA),
  SIERPINSKI_TRIANGLE("Sierpinski Triangle", TransformType.AFFINE2D);

  private final String name;
  private final TransformType type;

  /**
   * Constructor for the PresetTransforms enum.
   *
   * @param name The name of the preset transform
   * @param type The type of the preset transform
   */
  PresetTransforms(String name, TransformType type) {
    this.name = name;
    this.type = type;
  }

  /**
   * Get the name of the preset transform.
   *
   * @return The name of the preset transform
   */
  public String getName() {
    return name;
  }

  /**
   * Get the type of the preset transform.
   *
   * @return The type of the preset transform
   */
  public TransformType getType() {
    return type;
  }
}
