package cs3500.image;

/**
 * Represents a color in the red-green-blue format.
 * This class was created to avoid dependence on the awt package, but provides
 * similar functionalities to awt.Color. Implementing such classes instead of this one could
 * lead to unintended consequences in further implementations of this code.
 */
public class RGBColor {
  private final int red;
  private final int green;
  private final int blue;

  /**
   * A constructor for an RGB color, represented by three ints.
   * None of the values can be negative.
   *
   * @param red   the red value
   * @param green the green value
   * @param blue  the blue value
   */
  public RGBColor(int red, int green, int blue) {
    if (red < 0 || green < 0 || blue < 0) {
      throw new IllegalArgumentException("Values cannot be negative.");
    }
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  /**
   * Returns this color's red value.
   *
   * @return the red value
   */
  public int getRed() {
    return this.red;
  }

  /**
   * Returns this color's green value.
   *
   * @return the green value
   */
  public int getGreen() {
    return this.green;
  }

  /**
   * Returns this color's blue value.
   *
   * @return the blue value
   */
  public int getBlue() {
    return this.blue;
  }

  /**
   * Checks for object, then field equality.
   * This method is primarily used for testing purposes.
   *
   * @param other object to compare this to
   * @return whether the objects are equal
   */
  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof RGBColor)) {
      return false;
    }
    return this.red == ((RGBColor) other).getRed()
            && this.green == ((RGBColor) other).getGreen()
            && this.blue == ((RGBColor) other).getBlue();
  }

  /**
   * Overrides and returns a hashCode for this color.
   *
   * @return an int to represent this object's hashCode.
   */
  @Override
  public int hashCode() {
    return Long.hashCode(this.red)
            + Long.hashCode(this.blue)
            + Long.hashCode(this.green);
  }

}
