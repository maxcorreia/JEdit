package cs3500.image;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import cs3500.image.controller.hw05.ImageController2;
import cs3500.image.model.hw05.ImageGallery;
import cs3500.image.view.hw04.ImageViewImpl;

/**
 * Runs the image manipulation program, with updated functionality.
 * On top of the previous commands and input, it accepts scripts as input and adds new commands.
 */
public final class ImageProgram2 {
  /**
   * Main method for the program.
   *
   * @param args any arguments the program may take in
   */
  public static void main(String[] args) throws IOException {
    Readable rd = new InputStreamReader(System.in);
    if (args.length > 0) {
      try {
        rd = new FileReader(args[0]);
      } catch (Exception e) {
        System.out.println("File not found.");
      }
    }
    Appendable ap = System.out;
    ImageController2 controller = new ImageController2(new ImageGallery(),
            new ImageViewImpl(ap), rd);
    controller.runProgram();
  }
}