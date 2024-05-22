package no.ntnu.idatg2003.utility.enums;

/**
 * Enum for the different types of transformations that can be applied to the points in the chaos
 * game.
 */
public enum TransformType {
  AFFINE2D("Affine2D"),
  JULIA("Julia");

  private final String typeString;

  /**
   * Constructor for the TransformType enum.
   *
   * @param typeString the string representation of the TransformType
   */
  TransformType(String typeString) {
    this.typeString = typeString;
  }

  /**
   * Method to get the TransformType from a string.
   *
   * @param typeString the string to get the TransformType from
   * @return the TransformType
   * @throws IllegalArgumentException if the string does not match any TransformType
   */
  public static TransformType fromString(String typeString) {
    for (TransformType type : TransformType.values()) {
      if (type.typeString.equalsIgnoreCase(typeString)) {
        return type;
      }
    }
    throw new IllegalArgumentException("Unknown transform type: " + typeString);
  }

  /**
   * Method to get the string representation of the TransformType.
   *
   * @return the string representation of the TransformType
   */
  public String getTypeString() {
    return typeString;
  }
}
