package cs3500.image.command.hw04;

import java.util.ArrayList;

import cs3500.image.RGBColor;
import cs3500.image.model.hw04.ImageModel;
import cs3500.image.model.hw04.ImageModelImpl;

/**
 * A class to represent the conversion of an image to grayscale based on its luma values.
 */
public class LumaGreyScale implements Command {
  /**
   * Changes an ImageModel to luma grayscale, writing it to a new item.
   * This value is determined by calculating the luma value through
   * multiplying each value by their weight, then casting the sum
   * of the products to an integer.
   *
   * @param model the ImageModel to be modified
   */
  public static ImageModel execute(ImageModel model) {
    ArrayList<ArrayList<RGBColor>> image = new ArrayList<>();
    for (int row = 0; row < model.getHeight(); row++) {
      image.add(new ArrayList<RGBColor>());
      for (int col = 0; col < model.getWidth(); col++) {
        RGBColor current = model.getColorAt(row, col);
        int luma = (int)((0.2126 * current.getRed())
                + (0.7152 * current.getGreen())
                + (0.0722 * current.getBlue()));
        image.get(row).add(new RGBColor(luma, luma, luma));
      }
    }
    return new ImageModelImpl(model.getWidth(), model.getHeight(), model.getMaxValue(), image);
  }
}
