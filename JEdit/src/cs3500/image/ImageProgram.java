package cs3500.image;

import java.io.InputStreamReader;

import cs3500.image.controller.ImageController;
import cs3500.image.controller.ImageControllerImpl;
import cs3500.image.view.ImageViewImpl;

/**
 * Runs the image manipulation program.
 */
public final class ImageProgram {
  /**
   * Main method for the program.
   * @param args any arguments the program may take in
   */
  public static void main(String[] args) {
    Readable rd = new InputStreamReader(System.in);
    Appendable ap = System.out;
    ImageController controller = new ImageControllerImpl(new ImageViewImpl(ap), rd);
    controller.runProgram();
  }
}
