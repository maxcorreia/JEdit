package cs3500.image.command.hw04;

import java.util.ArrayList;

import cs3500.image.RGBColor;
import cs3500.image.model.hw04.ImageModel;
import cs3500.image.model.hw04.ImageModelImpl;

/**
 * A class to represent the vertical flipping of all pixels in an image.
 * An ImageModel is taken in to produce a new ImageModel with all of its Color values
 * flipped across the y-axis.
 */
public class VerticalFlip implements Command {

  /**
   * Flips an ImageModel vertically.
   *
   * @param model the ImageModel to be modified
   */
  public static ImageModel execute(ImageModel model) {
    ArrayList<ArrayList<RGBColor>> image = new ArrayList<>();
    for (int row = 0; row < model.getHeight(); row++) {
      image.add(new ArrayList<RGBColor>());
      for (int col = 0; col < model.getWidth(); col++) {
        image.get(row).add(model.getColorAt(model.getHeight() - row - 1, col));
      }
    }
    return new ImageModelImpl(model.getWidth(), model.getHeight(), model.getMaxValue(), image);
  }
}
