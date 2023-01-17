package cs3500.image;

import java.io.IOException;
import java.io.InputStreamReader;

import cs3500.image.controller.hw04.ImageController;
import cs3500.image.controller.hw04.ImageControllerImpl;
import cs3500.image.view.hw04.ImageViewImpl;

/**
 * Runs the image manipulation program.
 */
public final class ImageProgram {
  /**
   * Main method for the program.
   * @param args any arguments the program may take in
   */
  public static void main(String[] args) throws IOException {
    Readable rd = new InputStreamReader(System.in);
    Appendable ap = System.out;
    ImageController controller = new ImageControllerImpl(new ImageViewImpl(ap), rd);
    controller.runProgram();
  }
}
