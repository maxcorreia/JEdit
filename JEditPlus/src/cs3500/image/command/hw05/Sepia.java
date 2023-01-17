package cs3500.image.command.hw05;

import java.util.ArrayList;

import cs3500.image.model.hw05.ImageModelAlpha;

/**
 * A command to support applying a sepia filter to an image model.
 */
public class Sepia extends TransformCommand {

  /**
   * Applies a sepia filter to an image model.
   * @param model the model to apply the filter to
   * @return the model with the applied filter
   */
  public static ImageModelAlpha execute(ImageModelAlpha model) {
    ArrayList<ArrayList<Double>> matrix = new ArrayList<>();
    matrix.add(new ArrayList<Double>());
    matrix.add(new ArrayList<Double>());
    matrix.add(new ArrayList<Double>());
    matrix.get(0).add(0.393);
    matrix.get(0).add(0.769);
    matrix.get(0).add(0.189);
    matrix.get(1).add(0.349);
    matrix.get(1).add(0.686);
    matrix.get(1).add(0.168);
    matrix.get(2).add(0.272);
    matrix.get(2).add(0.534);
    matrix.get(2).add(0.131);


    return getImageModelTransform(model, matrix);
  }
}
