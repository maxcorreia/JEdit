package cs3500.image.command;

import java.util.ArrayList;

import cs3500.image.RGBColor;
import cs3500.image.model.ImageModel;
import cs3500.image.model.ImageModelImpl;

/**
 * A class to represent the conversion of an image to grayscale based on its average values.
 */
public class IntensityGrayScale implements Command {
  /**
   * Changes an ImageModel to value grayscale, writing it to a new item.
   * It takes the largest value of red, green, and blue per pixel,
   * and changes that value in the new image to the maximum for all
   * three RGB parameters.
   *
   * @param model the ImageModel to be modified
   */
  public static ImageModel execute(ImageModel model) {
    ArrayList<ArrayList<RGBColor>> image = new ArrayList<>();
    for (int row = 0; row < model.getHeight(); row++) {
      image.add(new ArrayList<RGBColor>());
      for (int col = 0; col < model.getWidth(); col++) {
        RGBColor current = model.getColorAt(row, col);
        int avg = (current.getRed() + current.getGreen() + current.getBlue()) / 3;
        image.get(row).add(new RGBColor(avg, avg, avg));
      }
    }
    return new ImageModelImpl(model.getWidth(), model.getHeight(), model.getMaxValue(), image);
  }
}
