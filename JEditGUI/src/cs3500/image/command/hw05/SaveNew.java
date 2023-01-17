package cs3500.image.command.hw05;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import cs3500.image.command.hw04.Command;
import cs3500.image.model.hw05.ImageModelAlpha;

import static java.awt.image.BufferedImage.TYPE_INT_ARGB;
import static java.awt.image.BufferedImage.TYPE_INT_RGB;

/**
 * A command class to allow for saving images in multiple formats (PNG, JPG, BMP).
 */
public class SaveNew implements Command {

  /**
   * Saves an image in the non-transparent formats.
   * @param filename the filename to save the image to
   * @param format the format of the image
   * @param model the model to save to an image
   * @throws IOException if there is a write error
   */
  public static void save(String filename, String format, ImageModelAlpha model)
          throws IOException {
    BufferedImage bi = new BufferedImage(model.getWidth(),
            model.getHeight(), TYPE_INT_RGB);
    for (int row = 0; row < model.getHeight(); row++) {
      for (int col = 0; col < model.getWidth(); col++) {
        int color = model.getColorAt(row, col).getBlue()
                + 256 * model.getColorAt(row, col).getGreen()
                + 65536 * model.getColorAt(row, col).getRed();
        bi.setRGB(col, row, color);
      }
    }
    try {
      ImageIO.write(bi, format, new File(filename));
    } catch (IOException e) {
      throw new IOException(e);
    }
  }

  /**
   * Saves an image in the transparent formats.
   * @param filename the filename to save the image to
   * @param format the format of the image
   * @param model the model to save to an image
   * @throws IOException if there is a write error
   */
  public static void saveAlpha(String filename, String format, ImageModelAlpha model)
          throws IOException {
    BufferedImage bi = new BufferedImage(model.getWidth(),
            model.getHeight(), TYPE_INT_ARGB);
    for (int row = 0; row < model.getHeight(); row++) {
      for (int col = 0; col < model.getWidth(); col++) {
        int color = model.getColorAt(row, col).getBlue()
                + 256 * model.getColorAt(row, col).getGreen()
                + 65536 * model.getColorAt(row, col).getRed()
                + 16777216 * model.getAlphaAt(row, col);
        bi.setRGB(col, row, color);
      }
    }
    try {
      ImageIO.write(bi, format, new File(filename));
    } catch (IOException e) {
      throw new IOException(e);
    }
  }

}
