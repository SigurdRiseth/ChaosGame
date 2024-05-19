package no.ntnu.idatg2003.utility;

public enum PresetTransforms {
  BARNSLEY_FERN("Barnsley Fern", TransformType.AFFINE2D),
  JULIA_SET("Julia Set", TransformType.JULIA),
  SIERPINSKI_TRIANGLE("Sierpinski Triangle", TransformType.AFFINE2D);

  private final String name;
  private final TransformType type;

  PresetTransforms(String name, TransformType type) {
    this.name = name;
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public TransformType getType() {
    return type;
  }
}
