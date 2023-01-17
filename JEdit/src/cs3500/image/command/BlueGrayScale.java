package cs3500.image.command;

import java.util.ArrayList;

import cs3500.image.RGBColor;
import cs3500.image.model.ImageModel;
import cs3500.image.model.ImageModelImpl;

/**
 * A class to represent the conversion of an image to grayscale based on its blue values.
 */
public class BlueGrayScale implements Command {
  /**
   * Changes an ImageModel to blue grayscale, writing it to a new item.
   * This is done by changing all red, green, and blue values to a pixel's blue value.
   *
   * @param model the ImageModel to be modified
   */
  public static ImageModel execute(ImageModel model) {
    ArrayList<ArrayList<RGBColor>> image = new ArrayList<>();
    for (int row = 0; row < model.getHeight(); row++) {
      image.add(new ArrayList<RGBColor>());
      for (int col = 0; col < model.getWidth(); col++) {
        RGBColor current = model.getColorAt(row, col);
        int blue = current.getBlue();
        image.get(row).add(new RGBColor(blue, blue, blue));
      }
    }
    return new ImageModelImpl(model.getWidth(), model.getHeight(), model.getMaxValue(), image);
  }
}
