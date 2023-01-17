package cs3500.image.command.hw04;

import java.util.ArrayList;

import cs3500.image.RGBColor;
import cs3500.image.model.hw04.ImageModel;
import cs3500.image.model.hw04.ImageModelImpl;

/**
 * A class to represent the conversion of an image to grayscale based on its average values.
 */
public class IntensityGreyScale implements Command {
  /**
   * Changes an ImageModel to intensity grayscale, writing it to a new item.
   * This value is determined by averaging the red, green, and blue
   * values, and setting all of these values in the new image to
   * this average value.
   *
   * @param model the ImageModel to be modified
   */
  public static ImageModel execute(ImageModel model) {
    ArrayList<ArrayList<RGBColor>> image = new ArrayList<>();
    for (int row = 0; row < model.getHeight(); row++) {
      image.add(new ArrayList<>());
      for (int col = 0; col < model.getWidth(); col++) {
        RGBColor current = model.getColorAt(row, col);
        int avg = (current.getRed() + current.getGreen() + current.getBlue()) / 3;
        image.get(row).add(new RGBColor(avg, avg, avg));
      }
    }
    return new ImageModelImpl(model.getWidth(), model.getHeight(), model.getMaxValue(), image);
  }
}
