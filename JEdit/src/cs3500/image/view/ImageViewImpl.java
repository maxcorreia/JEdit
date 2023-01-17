package cs3500.image.view;

import java.io.IOException;

/**
 * A view to render text messages to the console.
 */
public class ImageViewImpl implements ImageView {
  private final Appendable appendable;

  /**
   * Creates a new view object with an appendable.
   * The primary invariant is that the appendable is non-null.
   *
   * @param appendable The output to be passed to a controller
   */
  public ImageViewImpl(Appendable appendable) {
    if (appendable == null) {
      throw new IllegalArgumentException("None of the parameters can be null.");
    }
    this.appendable = appendable;
  }

  /**
   * Creates a new view object with a preset appendable.
   */
  public ImageViewImpl() {
    this.appendable = System.out;
  }

  /**
   * Render a specific message to the provided data destination.
   *
   * @param message the message to be transmitted
   * @throws IOException if transmission of the board to the provided data destination fails
   */
  @Override
  public void renderMessage(String message) throws IOException {
    try {
      appendable.append(message + System.lineSeparator());
    } catch (IOException e) {
      throw new IllegalStateException(e.getMessage());
    }
  }
}
