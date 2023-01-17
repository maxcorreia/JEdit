import java.io.IOException;
import java.nio.CharBuffer;

/**
 * A corrupt Readable for testing purposes.
 */
public class Corrupt implements Readable {
  @Override
  public int read(CharBuffer charBuffer) throws IOException {
    throw new IOException();
  }
}
