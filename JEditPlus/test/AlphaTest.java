import org.junit.Test;

import java.util.ArrayList;

import cs3500.image.model.hw04.ImageModelImpl;
import cs3500.image.model.hw05.ImageModelAlpha;
import cs3500.image.model.hw05.ImageModelAlphaImpl;

/**
 * Tester class for the ImageModelAlphaImpl constructors.
 */
public class AlphaTest {

  @Test(expected = IllegalArgumentException.class)
  public void negativeWidth() {
    ImageModelAlpha model = new ImageModelAlphaImpl(new ImageModelImpl( -1,
            1, 1, new ArrayList<>()), new ArrayList<>());
  }

  @Test(expected = IllegalArgumentException.class)
  public void negativeHeight() {
    ImageModelAlpha model = new ImageModelAlphaImpl(new ImageModelImpl(1,
            -1, 1, new ArrayList<>()), new ArrayList<>());
  }

  @Test(expected = IllegalArgumentException.class)
  public void negativeMax() {
    ImageModelAlpha model = new ImageModelAlphaImpl(new ImageModelImpl(1,
            1, -1, new ArrayList<>()), new ArrayList<>());
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullImage() {
    ImageModelAlpha model = new ImageModelAlphaImpl(new ImageModelImpl(1,
            1, 1, null), new ArrayList<>());
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullAlpha() {
    ImageModelAlpha model = new ImageModelAlphaImpl(new ImageModelImpl(1,
            1, 1, new ArrayList<>()), null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullModel() {
    ImageModelAlpha model = new ImageModelAlphaImpl(null, new ArrayList<>());
  }

}
