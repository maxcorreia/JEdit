package cs3500.image.controller;

/**
 * An interface for an Image controller. It allows a user to start the image manipulator.
 */
public interface ImageController {
  /**
   * Runs through the manipulator program.
   * @throws IllegalStateException if the parameters passed to the controller are invalid
   */
  void runProgram() throws IllegalStateException;
}
