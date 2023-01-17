package cs3500.image.command.hw05;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import cs3500.image.RGBColor;
import cs3500.image.command.hw04.Command;
import cs3500.image.model.hw04.ImageModel;
import cs3500.image.model.hw04.ImageModelImpl;
import cs3500.image.model.hw05.ImageModelAlpha;
import cs3500.image.model.hw05.ImageModelAlphaImpl;

/**
 * A command class to support loading images of different file types and manage conversions.
 */
public class LoadNew implements Command {

  /**
   * Loads a new image to a model. Has support for multiple filetypes (PNG, BMP, JPG).
   */
  public static ImageModelAlpha load(String filename) throws IOException {
    BufferedImage raw;
    try {
      raw = ImageIO.read(new File(filename));
    } catch (Exception e) {
      throw new IOException("File " + filename + " not found!");
    }
    int width = raw.getWidth();
    int height = raw.getHeight();
    int maxValue = 255;
    ArrayList<ArrayList<Integer>> alpha = new ArrayList<>();
    ArrayList<ArrayList<RGBColor>> image = new ArrayList<>();
    for (int row = 0; row < height; row++) {
      alpha.add(new ArrayList<>());
      image.add(new ArrayList<>());
      for (int col = 0; col < width; col++) {
        Color color = new Color(raw.getRGB(col, row), true);
        image.get(row).add(new RGBColor(color.getRed(),
                color.getGreen(),
                color.getBlue()));
        alpha.get(row).add(color.getAlpha());
      }
    }
    return new ImageModelAlphaImpl(new ImageModelImpl(width, height, maxValue, image), alpha);
  }

  /**
   * Generates an opaque alpha array for formats that do not support transparency.
   * @param model the model to apply this feature to
   * @return a 2D ArrayList with maximum opacity values
   */
  public static ArrayList<ArrayList<Integer>> genOpaqueAlpha(ImageModel model) {
    ArrayList<ArrayList<Integer>> alpha = new ArrayList<>();
    int width = model.getWidth();
    int height = model.getHeight();
    int maxValue = model.getMaxValue();
    for (int row = 0; row < height; row++) {
      alpha.add(new ArrayList<>());
      for (int col = 0; col < width; col++) {
        alpha.get(row).add(maxValue);
      }
    }
    return alpha;
  }

  /**
   * Generates the alpha grid for an ImageModelAlpha. Used for conversions in older commands.
   * @param model the model to apply this change to
   * @return the alpha 2D ArrayList of the original model
   */
  public static ArrayList<ArrayList<Integer>> genAlpha(ImageModelAlpha model) {
    ArrayList<ArrayList<Integer>> alpha = new ArrayList<>();
    int width = model.getWidth();
    int height = model.getHeight();
    for (int row = 0; row < height; row++) {
      alpha.add(new ArrayList<>());
      for (int col = 0; col < width; col++) {
        alpha.get(row).add(model.getAlphaAt(row, col));
      }
    }
    return alpha;
  }

  /**
   * Generates a PPM image for older commands.
   * @param model the model to create this image for
   * @return a 2D Array of RGBColors to apply to a command
   */
  public static ArrayList<ArrayList<RGBColor>> genPPMImage(ImageModel model) {
    ArrayList<ArrayList<RGBColor>> color = new ArrayList<>();
    int width = model.getWidth();
    int height = model.getHeight();
    for (int row = 0; row < height; row++) {
      color.add(new ArrayList<>());
      for (int col = 0; col < width; col++) {
        color.get(row).add(model.getColorAt(row, col));
      }
    }
    return color;
  }

}
