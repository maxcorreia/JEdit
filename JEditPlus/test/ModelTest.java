import org.junit.Test;

import java.util.ArrayList;

import cs3500.image.RGBColor;
import cs3500.image.model.hw04.ImageModel;
import cs3500.image.model.hw04.ImageModelImpl;

import static org.junit.Assert.assertEquals;

/**
 * Test class for the model implementation's valid and invalid constructors.
 */
public class ModelTest {

  @Test(expected = IllegalArgumentException.class)
  public void negativeWidth() {
    ImageModel model = new ImageModelImpl(-1, 1, 1, new ArrayList<>());
  }

  @Test(expected = IllegalArgumentException.class)
  public void negativeHeight() {
    ImageModel model = new ImageModelImpl(1, -1, 1, new ArrayList<>());
  }

  @Test(expected = IllegalArgumentException.class)
  public void negativeMax() {
    ImageModel model = new ImageModelImpl(1, 1, -1, new ArrayList<>());
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullImage() {
    ImageModel model = new ImageModelImpl(1, 1, 1, null);
  }

  @Test
  public void testGetImageModelFields() {
    ArrayList<RGBColor> row1 = new ArrayList<>();
    row1.add(new RGBColor(1, 2, 3));
    row1.add(new RGBColor(4, 5, 6));
    row1.add(new RGBColor(7, 8, 9));

    ArrayList<RGBColor> row2 = new ArrayList<>();
    row2.add(new RGBColor(10, 11, 12));
    row2.add(new RGBColor(13, 14, 15));
    row2.add(new RGBColor(16, 17, 18));

    ArrayList<RGBColor> row3 = new ArrayList<>();
    row3.add(new RGBColor(19, 20, 21));
    row3.add(new RGBColor(22, 23, 24));
    row3.add(new RGBColor(25, 26, 27));

    ArrayList<ArrayList<RGBColor>> image = new ArrayList<>();
    image.add(row1);
    image.add(row2);
    image.add(row3);

    ImageModel model = new ImageModelImpl(3, 3, 255, image);

    int width = model.getWidth();
    int height = model.getHeight();
    int maxValue = model.getMaxValue();
    RGBColor rgbColor = model.getColorAt(1, 2);

    assertEquals(3, width);
    assertEquals(3, height);
    assertEquals(255, maxValue);
    assertEquals(new RGBColor(16, 17, 18), rgbColor);
  }
}
