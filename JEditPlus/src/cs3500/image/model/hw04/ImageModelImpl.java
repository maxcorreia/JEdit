package cs3500.image.model.hw04;

import cs3500.image.RGBColor;
import java.util.ArrayList;

/**
 * An implementation for an image model.
 * The width, height, and maxValue parameters are stored as ints,
 * and the image data itself is stored in a two-dimensional
 * ArrayList of RGBColors, which carry three int values to represent
 * the red, green, and blue values of a pixel.
 */
public class ImageModelImpl implements ImageModel {

  private final int width;
  private final int height;

  private final int maxValue;
  private final ArrayList<ArrayList<RGBColor>> image;

  /**
   * A constructor for the Image model implementation.
   * The primary invariants are that none of the int values
   * (width, height, maxValue) are less than zero.
   * The image array must also be non-null.
   *
   * @param image a 2D ArrayList of RGBColors loaded in a rectangular array
   */
  public ImageModelImpl(int width, int height, int maxValue, ArrayList<ArrayList<RGBColor>> image) {
    if (height < 0 || width < 0 || maxValue < 0) {
      throw new IllegalArgumentException("An invalid file was passed to this class.");
    }
    if (image == null) {
      throw new IllegalArgumentException("Image cannot be null");
    }
    this.width = width;
    this.height = height;
    this.maxValue = maxValue;
    this.image = image;
  }

  /**
   * Gets the width of a PPM image.
   *
   * @return the width as an int
   */
  @Override
  public int getWidth() {
    return this.width;
  }

  /**
   * Gets the height of a PPM image.
   *
   * @return the height as an int
   */
  @Override
  public int getHeight() {
    return this.height;
  }

  /**
   * Gets the maximum RGBColor value of a PPM image.
   *
   * @return the height as an int
   */
  public int getMaxValue() {
    return this.maxValue;
  }

  /**
   * Gets the RGBColor of a pixel at a certain position.
   *
   * @param row the row of the pixel
   * @param col the column of the pixel
   * @return the RGBColor at that position
   */
  @Override
  public RGBColor getColorAt(int row, int col) {
    return this.image.get(row).get(col);
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
    if (!(other instanceof ImageModelImpl)) {
      return false;
    }
    return this.height == ((ImageModelImpl) other).getHeight()
            && this.width == ((ImageModelImpl) other).getWidth()
            && this.compareImage((ImageModelImpl) other);
  }

  /**
   * Overrides and returns a hashCode for this image model.
   * @return an int to represent this object's hashCode.
   */
  @Override
  public int hashCode() {
    return Long.hashCode(this.height)
            + Long.hashCode(this.width)
            + Long.hashCode(this.getMaxValue())
            + Long.hashCode(this.calcImageHash());
  }

  /**
   * Calculates the image's hashcode based on its colors.
   * @return a hashcode for this object's image.
   */
  private long calcImageHash() {
    int hash = 0;
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        hash += this.getColorAt(row, col).hashCode();
      }
    }
    return hash;
  }

  /**
   * Compares the images of two models for the purpose of comparing them.
   * @param other the ImageModelImpl to compare this to
   * @return whether the images are equal
   */
  private boolean compareImage(ImageModelImpl other) {
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        if (!this.getColorAt(row, col).equals(other.getColorAt(row, col))) {
          return false;
        }
      }
    }
    return true;
  }
}
