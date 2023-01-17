package cs3500.image.command.hw05;

import java.util.ArrayList;

import cs3500.image.RGBColor;
import cs3500.image.command.hw04.Command;
import cs3500.image.model.hw04.ImageModel;
import cs3500.image.model.hw04.ImageModelImpl;
import cs3500.image.model.hw05.ImageModelAlpha;
import cs3500.image.model.hw05.ImageModelAlphaImpl;

/**
 * A class that allows for matrix mathematics to be applied to image models.
 */
public class MatrixCommand implements Command {

  /**
   * Applies a matrix to an image.
   * @param model the model to be changed
   * @param matrix the matrix to apply to the model
   * @return a new model with the applied changes
   */
  protected static ImageModelAlpha getImageModelMatrix(ImageModelAlpha model,
                                                       ArrayList<ArrayList<Double>> matrix) {
    ArrayList<ArrayList<Integer>> alpha = new ArrayList<>();
    ArrayList<ArrayList<RGBColor>> image = new ArrayList<>();
    for (int row = 0; row < model.getHeight(); row++) {
      alpha.add(new ArrayList<>());
      image.add(new ArrayList<>());
      for (int col = 0; col < model.getWidth(); col++) {
        RGBColor current = model.getColorAt(row, col);
        image.get(row).add(MatrixCommand.applyFilter(row, col, model, matrix));
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
   * Applies a filter to a model's colors.
   * @param row the row of the pixel
   * @param col the column of the pixel
   * @param model the model to apply this to
   * @param matrix the matrix to apply the math to the pixel
   * @return a new RGBColor with the changes applied
   */
  protected static RGBColor applyFilter(int row, int col,
                                        ImageModel model,
                                        ArrayList<ArrayList<Double>> matrix) {
    int halfSize = (matrix.size() - 1) / 2;
    int topStart;
    int leftStart;
    int bottomEnd;
    int rightEnd;

    if (row < halfSize) {
      topStart = halfSize - row;
    } else {
      topStart = 0;
    }

    if (col < halfSize) {
      leftStart = halfSize - col;
    } else {
      leftStart = 0;
    }

    if (row >= model.getHeight() - halfSize) {
      bottomEnd = halfSize + (model.getHeight() - row);
    } else {
      bottomEnd = matrix.size();
    }

    if (col >= model.getWidth() - halfSize) {
      rightEnd = halfSize + (model.getWidth() - col);
    } else {
      rightEnd = matrix.size();
    }

    double red = 0;
    double green = 0;
    double blue = 0;

    for (int r = topStart - halfSize; r < bottomEnd - halfSize; r++) {
      for (int c = leftStart - halfSize; c < rightEnd - halfSize; c++) {
        red += model.getColorAt(row + r, col + c).getRed()
                * matrix.get(r + halfSize).get(c + halfSize);
        green += model.getColorAt(row + r, col + c).getGreen()
                * matrix.get(r + halfSize).get(c + halfSize);
        blue += model.getColorAt(row + r, col + c).getBlue()
                * matrix.get(r + halfSize).get(c + halfSize);
      }
    }
    return new RGBColor(maxMin(model, (int) red),
            maxMin(model, (int) green),
            maxMin(model, (int) blue));
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
