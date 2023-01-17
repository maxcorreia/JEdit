package cs3500.image.command.hw04;

import java.io.FileWriter;
import java.io.IOException;

import cs3500.image.model.hw04.ImageModel;

/**
 * This class represents saving a model's image to a file.
 * A model and filename must be given in order to save,
 * but there are no other restrictions.
 */
public class Save implements Command {
  /**
   * Executes a command to save files through the program.
   * The saved item is a copy of the given image.
   */
  public static void save(String filename, ImageModel model) throws IOException {
    String newline = System.lineSeparator();
    try {
      FileWriter writer = new FileWriter(filename, false);
      writer.write("P3" + newline);
      writer.write(model.getWidth() + " " + model.getHeight() + newline);
      writer.write(model.getMaxValue() + newline);
      for (int row = 0; row < model.getHeight(); row++) {
        for (int col = 0; col < model.getWidth(); col++) {
          writer.write(model.getColorAt(row, col).getRed() + newline);
          writer.write(model.getColorAt(row, col).getGreen() + newline);
          writer.write(model.getColorAt(row, col).getBlue() + newline);
        }
      }
      writer.close();
    } catch (IOException e) {
      throw new IOException(e);
    }
  }
}
