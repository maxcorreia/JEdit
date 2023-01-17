package cs3500.image.command.hw05;

import java.util.ArrayList;

import cs3500.image.model.hw05.ImageModelAlpha;

/**
 * A command class to support greyscale application to image models.
 */
public class Greyscale extends TransformCommand {

  /**
   * Applies greyscale on an image model.
   *
   * @param model the model to apply greyscale to
   * @return the model with the greyscale applied
   */
  public static ImageModelAlpha execute(ImageModelAlpha model) {
    ArrayList<ArrayList<Double>> matrix = new ArrayList<>();
    matrix.add(new ArrayList<Double>());
    matrix.add(new ArrayList<Double>());
    matrix.add(new ArrayList<Double>());
    matrix.get(0).add(0.2126);
    matrix.get(0).add(0.7152);
    matrix.get(0).add(0.0722);
    matrix.get(1).add(0.2126);
    matrix.get(1).add(0.7152);
    matrix.get(1).add(0.0722);
    matrix.get(2).add(0.2126);
    matrix.get(2).add(0.7152);
    matrix.get(2).add(0.0722);

    return getImageModelTransform(model, matrix);
  }
}
