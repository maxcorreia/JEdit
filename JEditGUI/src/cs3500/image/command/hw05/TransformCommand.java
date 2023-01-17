package cs3500.image.command.hw05;

import java.util.ArrayList;

import cs3500.image.RGBColor;
import cs3500.image.command.hw04.Command;
import cs3500.image.model.hw04.ImageModel;
import cs3500.image.model.hw04.ImageModelImpl;
import cs3500.image.model.hw05.ImageModelAlpha;
import cs3500.image.model.hw05.ImageModelAlphaImpl;

/**
 * A command class to support transforming pixel values using matrix multiplication.
 */
public class TransformCommand implements Command {
  /**
   * Applies an image transformation to an image model.
   *
   * @param model  the model to apply this change to
   * @param matrix the matrix to apply to the model
   * @return the model with the applied changes
   */
  protected static ImageModelAlpha getImageModelTransform(ImageModelAlpha model,
                                                          ArrayList<ArrayList<Double>> matrix) {
    for (ArrayList<Double> array : matrix) {
      if (array.size() != 3) {
        throw new IllegalArgumentException("Transform is invalid.");
      }
    }
    if (matrix.size() != 3 || matrix.get(0).size() != 3) {
      throw new IllegalArgumentException("Transform is invalid.");
    }
    ArrayList<ArrayList<Integer>> alpha = new ArrayList<>();
    ArrayList<ArrayList<RGBColor>> image = new ArrayList<>();
    for (int row = 0; row < model.getHeight(); row++) {
      alpha.add(new ArrayList<>());
      image.add(new ArrayList<>());
      for (int col = 0; col < model.getWidth(); col++) {
        RGBColor current = model.getColorAt(row, col);
        int newRed = (int) ((matrix.get(0).get(0) * current.getRed())
                + (matrix.get(0).get(1) * current.getGreen())
                + (matrix.get(0).get(2) * current.getBlue()));
        int newGreen = (int) ((matrix.get(1).get(0) * current.getRed())
                + (matrix.get(1).get(1) * current.getGreen())
                + (matrix.get(1).get(2) * current.getBlue()));
        int newBlue = (int) ((matrix.get(2).get(0) * current.getRed())
                + (matrix.get(2).get(1) * current.getGreen())
                + (matrix.get(2).get(2) * current.getBlue()));
        image.get(row).add(new RGBColor(maxMin(model, newRed),
                maxMin(model, newGreen),
                maxMin(model, newBlue)));
        alpha.get(row).add(model.getAlphaAt(row, col));
      }
    }
    return new ImageModelAlphaImpl(new ImageModelImpl(model.getWidth(),
            model.getHeight(),
            model.getMaxValue(),
            image),
            alpha);
  }

  /**
   * Sets the bounds of color values during brightening.
   *
   * @param model the model to extract the maximum value from
   * @param i     the color value
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
