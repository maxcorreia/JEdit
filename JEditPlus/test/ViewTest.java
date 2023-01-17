import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

import cs3500.image.view.hw04.ImageView;
import cs3500.image.view.hw04.ImageViewImpl;

/**
 * Test class for the view implementation.
 * The constructor should throw exceptions correctly and renderMessage() should
 * append a message to the output.
 */
public class ViewTest {

  @Test(expected = IllegalArgumentException.class)
  public void testNullAppendable() {
    ImageView testView = new ImageViewImpl(null);
  }

  @Test
  public void testRenderMessageAppendsStringCorrectly() throws IOException {
    StringBuilder out = new StringBuilder();
    ImageView testView = new ImageViewImpl(out);
    testView.renderMessage("This should be appended to the output string builder");
    assertEquals(out.toString(), "This should be appended to the output string builder"
            + System.lineSeparator());
  }
}
