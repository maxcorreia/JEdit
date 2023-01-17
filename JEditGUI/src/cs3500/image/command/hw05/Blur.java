package cs3500.image.command.hw05;

import java.util.ArrayList;

import cs3500.image.model.hw05.ImageModelAlpha;

/**
 * A command class to allow for the blurring of image models.
 */
public class Blur extends MatrixCommand {

  /**
   * Blurs an image model's pixels.
   *
   * @param model the model to apply the blur to
   * @return the blurred model
   */
  public static ImageModelAlpha execute(ImageModelAlpha model) {
    ArrayList<ArrayList<Double>> matrix = new ArrayList<>();
    matrix.add(new ArrayList<Double>());
    matrix.add(new ArrayList<Double>());
    matrix.add(new ArrayList<Double>());
    matrix.get(0).add(0.0625);
    matrix.get(0).add(0.125);
    matrix.get(0).add(0.0625);
    matrix.get(1).add(0.125);
    matrix.get(1).add(0.25);
    matrix.get(1).add(0.125);
    matrix.get(2).add(0.0625);
    matrix.get(2).add(0.125);
    matrix.get(2).add(0.0625);

    return getImageModelMatrix(model, matrix);
  }

}
