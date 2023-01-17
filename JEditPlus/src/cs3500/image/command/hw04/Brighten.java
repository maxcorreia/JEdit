package cs3500.image.command.hw04;

import java.util.ArrayList;

import cs3500.image.RGBColor;
import cs3500.image.model.hw04.ImageModel;
import cs3500.image.model.hw04.ImageModelImpl;

/**
 * A class to represent the brightening an image based on a given value.
 */
public class Brighten implements Command {
  /**
   * Brightens an ImageModel, writing it to a new item.
   *
   * @param model the ImageModel to be modified
   */
  public static ImageModel execute(ImageModel model, int level) {
    ArrayList<ArrayList<RGBColor>> image = new ArrayList<>();
    for (int row = 0; row < model.getHeight(); row++) {
      image.add(new ArrayList<RGBColor>());
      for (int col = 0; col < model.getWidth(); col++) {
        RGBColor current = model.getColorAt(row, col);
        int red = maxMin(model, current.getRed() + level);
        int green = maxMin(model, current.getGreen() + level);
        int blue = maxMin(model, current.getBlue() + level);
        image.get(row).add(new RGBColor(red, green, blue));
      }
    }
    return new ImageModelImpl(model.getWidth(),
            model.getHeight(),
            model.getMaxValue(),
            image);
  }

  /**
   * Sets the bounds of color values during brightening.
   *
   * @param model the model to extract the maximum value from
   * @param i the color value
   * @return the maximized/minimized value
   */
  private static int maxMin(ImageModel model, int i) {
    if (i > model.getMaxValue()) {
      return model.getMaxValue();
    } else {
      return Math.max(i, 0);
    }
  }
}
