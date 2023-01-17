package cs3500.image.model.hw04;

import cs3500.image.RGBColor;

/**
 * This interface represents operations that can be used to monitor the state of an image.
 * This should be able to represent all types of images that one may pass
 * through this program.
 */
public interface ImageModel {

  /**
   * Gets the width of an image.
   * @return the width as an int
   */
  int getWidth();

  /**
   * Gets the height of an image.
   * @return the height as an int
   */
  int getHeight();

  /**
   * Gets the maximum color value of an image.
   * In a PPM file, this value represents the range of values between the minimum and maximum.
   * For other file types, this may be set to a corresponding value (i.e. 1, 255).
   * @return the maximum value as an int
   */
  int getMaxValue();

  /**
   * Gets the color of a pixel at a certain position.
   * It uses the Color class to represent a pixel, which has different ways of representing a value.
   * @param row the row of the pixel
   * @param col the column of the pixel
   * @return the color value at that position
   */
  RGBColor getColorAt(int row, int col);

}
