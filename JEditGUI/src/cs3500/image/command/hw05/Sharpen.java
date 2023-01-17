package cs3500.image.command.hw05;

import java.util.ArrayList;

import cs3500.image.model.hw05.ImageModelAlpha;

/**
 * A command to allow for sharpening of image models.
 */
public class Sharpen extends MatrixCommand {

  /**
   * Sharpens an image model's pixels.
   *
   * @param model the model to sharpen
   * @return the sharpened model
   */
  public static ImageModelAlpha execute(ImageModelAlpha model) {
    ArrayList<ArrayList<Double>> matrix = new ArrayList<>();
    matrix.add(new ArrayList<Double>());
    matrix.add(new ArrayList<Double>());
    matrix.add(new ArrayList<Double>());
    matrix.add(new ArrayList<Double>());
    matrix.add(new ArrayList<Double>());
    matrix.get(0).add(-0.125);
    matrix.get(0).add(-0.125);
    matrix.get(0).add(-0.125);
    matrix.get(0).add(-0.125);
    matrix.get(0).add(-0.125);
    matrix.get(1).add(-0.125);
    matrix.get(1).add(0.25);
    matrix.get(1).add(0.25);
    matrix.get(1).add(0.25);
    matrix.get(1).add(-0.125);
    matrix.get(2).add(-0.125);
    matrix.get(2).add(0.25);
    matrix.get(2).add(1.0);
    matrix.get(2).add(0.25);
    matrix.get(2).add(-0.125);
    matrix.get(3).add(-0.125);
    matrix.get(3).add(0.25);
    matrix.get(3).add(0.25);
    matrix.get(3).add(0.25);
    matrix.get(3).add(-0.125);
    matrix.get(4).add(-0.125);
    matrix.get(4).add(-0.125);
    matrix.get(4).add(-0.125);
    matrix.get(4).add(-0.125);
    matrix.get(4).add(-0.125);

    return getImageModelMatrix(model, matrix);
  }
  
}
