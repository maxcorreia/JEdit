package cs3500.image;

import java.io.FileReader;
import java.io.InputStreamReader;

import cs3500.image.controller.hw05.ImageController2;
import cs3500.image.controller.hw06.ImageControllerGUI;
import cs3500.image.model.hw05.ImageGallery;
import cs3500.image.view.hw04.ImageViewImpl;
import cs3500.image.view.hw06.ImageViewGUI;

/**
 * Runs an image manipulator program, either with a GUI or in text form depending on arguments.
 */
public class ImageProgramGUI {
  /**
   * The main method for the program.
   *
   * @param args the arguments passed to the program
   */
  public static void main(String[] args) {
    if (args.length > 0 && args[0].equals("-text")) {
      Readable rd = new InputStreamReader(System.in);
      Appendable ap = System.out;
      ImageController2 controller = new ImageController2(new ImageGallery(),
              new ImageViewImpl(ap), rd);
      controller.runProgram();
      return;
    }
    if (args.length == 2 && args[0].equals("-file")) {
      Readable rd = new InputStreamReader(System.in);
      try {
        rd = new FileReader(args[1]);
      } catch (Exception e) {
        System.out.println("File not found.");
        return;
      }
      Appendable ap = System.out;
      ImageController2 controller = new ImageController2(new ImageGallery(),
              new ImageViewImpl(ap), rd);
      controller.runProgram();
      return;
    }
    if (args.length > 0) {
      System.out.println("Illegal arguments were passed. Correct additional arguments are: "
              + "-file [filename], -text");
      return;
    }
    ImageViewGUI.setDefaultLookAndFeelDecorated(false);
    ImageViewGUI view = new ImageViewGUI("ImageManipulatorGUI");
    ImageControllerGUI controller = new ImageControllerGUI(new ImageGallery(), view);
    controller.runProgram();
  }
}
