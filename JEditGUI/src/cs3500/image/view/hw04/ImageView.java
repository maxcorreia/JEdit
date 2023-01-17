package cs3500.image.view.hw04;

import java.io.IOException;

/**
 * This interface represents operations for a program view.
 */
public interface ImageView {
  /**
   * Render a specific message to the provided data destination.
   * @param message the message to be transmitted
   * @throws IOException if transmission of the board to the provided data destination fails
   */
  void renderMessage(String message) throws IOException;
}
