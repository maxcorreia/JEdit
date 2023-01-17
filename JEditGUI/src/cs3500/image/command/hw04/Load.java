package cs3500.image.command.hw04;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import cs3500.image.RGBColor;
import cs3500.image.model.hw04.ImageModel;
import cs3500.image.model.hw04.ImageModelImpl;

/**
 * This class represents loading an image to a model.
 * It directly interacts with the user's file system for these purposes,
 * and as such pre-existing files are required to fully use this program.
 */
public class Load implements Command {

  /**
   * Executes a command to load files through the program.
   */
  public static ImageModel load(String filename) throws IllegalStateException {
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      throw new IllegalStateException("File " + filename + " not found!");
    }
    StringBuilder builder = new StringBuilder();
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    sc = new Scanner(builder.toString());
    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      throw new IllegalStateException("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();
    ArrayList<ArrayList<RGBColor>> image = new ArrayList<>();
    for (int row = 0; row < height; row++) {
      image.add(new ArrayList<RGBColor>());
      for (int col = 0; col < width; col++) {
        image.get(row).add(new RGBColor(sc.nextInt(), sc.nextInt(), sc.nextInt()));
      }
    }

    return new ImageModelImpl(width, height, maxValue, image);
  }
}
