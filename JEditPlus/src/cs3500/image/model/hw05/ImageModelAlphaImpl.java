package cs3500.image.model.hw05;

import java.util.ArrayList;

import cs3500.image.RGBColor;
import cs3500.image.model.hw04.ImageModel;

/**
 * An implementation of a new Image model with support for alpha values.
 * This is done in lieu of implementing .png files, which carry alpha values in their
 * individual pixels. This item has an ImageModel to build off of, along with a 2D ArrayList
 * of Integers to represent alpha values, similar to how colors are stored in our previous
 * model implementation.
 */
public class ImageModelAlphaImpl implements ImageModelAlpha {
  private final ImageModel model;
  private final ArrayList<ArrayList<Integer>> alpha;

  /**
   * Constructs an ImageModelAlphaImpl.
   * The primary invariants are that neither of the constructing variables are null.
   *
   * @param model the model to construct around
   * @param alpha the 2D ArrayList of alpha values for each pixel
   */
  public ImageModelAlphaImpl(ImageModel model, ArrayList<ArrayList<Integer>> alpha) {
    if (model == null) {
      throw new IllegalArgumentException("The given model was null.");
    }
    if (alpha == null) {
      throw new IllegalArgumentException("The given alpha grid was null.");
    }
    this.model = model;
    this.alpha = alpha;
  }

  /**
   * Returns the width of the constructed model.
   *
   * @return the width of this model
   */
  public int getWidth() {
    return this.model.getWidth();
  }

  /**
   * Returns the height of the constructed model.
   *
   * @return the height of this model
   */
  public int getHeight() {
    return this.model.getHeight();
  }

  /**
   * Returns the maximum color value of the constructed model.
   *
   * @return the maximum color value of this model
   */
  public int getMaxValue() {
    return this.model.getMaxValue();
  }

  /**
   * Returns a color from the image in the constructed model.
   *
   * @param row the row of the pixel
   * @param col the column of the pixel
   * @return the color of the chosen pixel
   */
  public RGBColor getColorAt(int row, int col) {
    return this.model.getColorAt(row, col);
  }

  /**
   * Returns an alpha value from a given pixel.
   *
   * @param row the row of the pixel
   * @param col the column of the pixel
   * @return the alpha value of the chosen pixel
   */
  public int getAlphaAt(int row, int col) {
    return this.alpha.get(row).get(col);
  }

  /**
   * Returns this object's image. For comparison purposes only.
   *
   * @return this object's image
   */
  private ImageModel getImage() {
    return this.model;
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
    if (!(other instanceof ImageModelAlphaImpl)) {
      return false;
    }
    return this.getImage().equals(((ImageModelAlphaImpl) other).getImage())
            && this.compareAlpha((ImageModelAlphaImpl) other);
  }

  /**
   * Overrides and returns a hashCode for this image model.
   *
   * @return an int to represent this object's hashCode.
   */
  @Override
  public int hashCode() {
    return Long.hashCode(this.getWidth())
            + Long.hashCode(this.getHeight())
            + Long.hashCode(this.getMaxValue())
            + Long.hashCode(this.calcImageHash())
            + Long.hashCode(this.calcAlphaHash());
  }

  /**
   * Calculates the image's hashcode based on its colors.
   *
   * @return a hashcode for this object's image.
   */
  private long calcImageHash() {
    int hash = 0;
    for (int row = 0; row < this.getHeight(); row++) {
      for (int col = 0; col < this.getWidth(); col++) {
        hash += this.getColorAt(row, col).hashCode();
      }
    }
    return hash;
  }

  /**
   * Calculates the alpha array's hashcode based on its values.
   *
   * @return a hashcode for this object's image.
   */
  private long calcAlphaHash() {
    int hash = 0;
    for (int row = 0; row < this.getHeight(); row++) {
      for (int col = 0; col < this.getWidth(); col++) {
        hash += Long.hashCode(this.getAlphaAt(row, col));
      }
    }
    return hash;
  }

  /**
   * Compares the images of two models for the purpose of comparing them.
   *
   * @param other the ImageModelAlphaImpl to compare this to
   * @return whether the images are equal
   */
  private boolean compareImage(ImageModelAlphaImpl other) {
    for (int row = 0; row < this.getHeight(); row++) {
      for (int col = 0; col < this.getWidth(); col++) {
        if (!this.getColorAt(row, col).equals(other.getColorAt(row, col))) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Compares the alpha values of two models for the purpose of comparing them.
   *
   * @param other the ImageModelAlphaImpl to compare this to
   * @return whether the images are equal
   */
  private boolean compareAlpha(ImageModelAlphaImpl other) {
    for (int row = 0; row < this.getHeight(); row++) {
      for (int col = 0; col < this.getWidth(); col++) {
        if (this.getAlphaAt(row, col) != (other.getAlphaAt(row, col))) {
          return false;
        }
      }
    }
    return true;
  }

}
