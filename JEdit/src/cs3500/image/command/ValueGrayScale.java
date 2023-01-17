package cs3500.image.command;

import java.util.ArrayList;

import cs3500.image.RGBColor;
import cs3500.image.model.ImageModel;
import cs3500.image.model.ImageModelImpl;

/**
 * A class to represent the conversion of an image to grayscale based on its maximum values.
 */
public class ValueGrayScale implements Command {
  /**
   * Changes an ImageModel to value grayscale, writing it to a new item.
   * This value is determined by averaging the red, green, and blue
   * values, and setting all of these values in the new image to
   * this average value.
   *
   * @param model the ImageModel to be modified
   */
  public static ImageModel execute(ImageModel model) {
    ArrayList<ArrayList<RGBColor>> image = new ArrayList<>();
    for (int row = 0; row < model.getHeight(); row++) {
      image.add(new ArrayList<RGBColor>());
      for (int col = 0; col < model.getWidth(); col++) {
        RGBColor current = model.getColorAt(row, col);
        int first = Math.max(current.getRed(), current.getGreen());
        int max = Math.max(first, current.getBlue());
        image.get(row).add(new RGBColor(max, max, max));
      }
    }
    return new ImageModelImpl(model.getWidth(), model.getHeight(), model.getMaxValue(), image);
  }
}
