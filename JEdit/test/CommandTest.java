import org.junit.Test;

import java.io.IOException;
import java.util.NoSuchElementException;

import cs3500.image.command.BlueGrayScale;
import cs3500.image.command.Brighten;
import cs3500.image.command.GreenGrayScale;
import cs3500.image.command.HorizontalFlip;
import cs3500.image.command.IntensityGrayScale;
import cs3500.image.command.LumaGrayScale;
import cs3500.image.command.RedGrayScale;
import cs3500.image.command.ValueGrayScale;
import cs3500.image.command.VerticalFlip;
import cs3500.image.model.ImageModel;

import static cs3500.image.command.Load.load;
import static cs3500.image.command.Save.save;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Test class for the commands that can be applied to an image model.
 * It ensures that the given images in res/ are the same as the base
 * image modified by the corresponding commands.
 * It also tests for any illegitimate Model constructors passed through the commands.
 */
public class CommandTest {

  @Test(expected = NoSuchElementException.class)
  public void testLoadBlankPPM() {
    ImageModel dolphin = load("res/porpoise.ppm");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLoadNegativeDimensionsPPM() {
    ImageModel dolphin = load("res/negative-dimensions.ppm");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLoadNegativeMaxValuePPM() {
    ImageModel dolphin = load("res/negative-maximum.ppm");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLoadNegativePixelPPM() {
    ImageModel dolphin = load("res/negative-pixel.ppm");
  }

  @Test
  public void testEqualReadSameFile() {
    ImageModel dolphin1 = load("res/dolphin.ppm");
    ImageModel dolphin2 = load("res/dolphin.ppm");
    assertEquals(dolphin1, dolphin2);
  }

  @Test
  public void testRedGrayScaleEquals() {
    ImageModel dolphin1 = load("res/dolphin-red-grayscale.ppm");
    ImageModel dolphin2 = RedGrayScale.execute(load("res/dolphin.ppm"));
    assertEquals(dolphin1, dolphin2);
  }

  @Test
  public void testGreenGrayScaleEquals() {
    ImageModel dolphin1 = load("res/dolphin-green-grayscale.ppm");
    ImageModel dolphin2 = GreenGrayScale.execute(load("res/dolphin.ppm"));
    assertEquals(dolphin1, dolphin2);
  }

  @Test
  public void testBlueGrayScaleEquals() {
    ImageModel dolphin1 = load("res/dolphin-blue-grayscale.ppm");
    ImageModel dolphin2 = BlueGrayScale.execute(load("res/dolphin.ppm"));
    assertEquals(dolphin1, dolphin2);
  }

  @Test
  public void testIntensityGrayScaleEquals() {
    ImageModel dolphin1 = load("res/dolphin-intensity-grayscale.ppm");
    ImageModel dolphin2 = IntensityGrayScale.execute(load("res/dolphin.ppm"));
    assertEquals(dolphin1, dolphin2);
  }

  @Test
  public void testLumaGrayScaleEquals() {
    ImageModel dolphin1 = load("res/dolphin-luma-grayscale.ppm");
    ImageModel dolphin2 = LumaGrayScale.execute(load("res/dolphin.ppm"));
    assertEquals(dolphin1, dolphin2);
  }

  @Test
  public void testValueGrayScaleEquals() {
    ImageModel dolphin1 = load("res/dolphin-value-grayscale.ppm");
    ImageModel dolphin2 = ValueGrayScale.execute(load("res/dolphin.ppm"));
    assertEquals(dolphin1, dolphin2);
  }

  @Test
  public void testHorizontalFlipEquals() {
    ImageModel dolphin1 = load("res/dolphin-horizontal.ppm");
    ImageModel dolphin2 = HorizontalFlip.execute(load("res/dolphin.ppm"));
    assertEquals(dolphin1, dolphin2);
  }

  @Test
  public void testVerticalFlipEquals() {
    ImageModel dolphin1 = load("res/dolphin-vertical.ppm");
    ImageModel dolphin2 = VerticalFlip.execute(load("res/dolphin.ppm"));
    assertEquals(dolphin1, dolphin2);
  }

  @Test
  public void testBrightenEquals() {
    ImageModel dolphin1 = load("res/dolphin-brighter-by-50.ppm");
    ImageModel dolphin2 = Brighten.execute(load("res/dolphin.ppm"), 50);
    assertEquals(dolphin1, dolphin2);
  }

  @Test
  public void testVerticalHorizontalEquals() {
    ImageModel dolphin1 = load("res/dolphin-horizontal-vertical.ppm");
    ImageModel dolphin2 = HorizontalFlip.execute(load("res/dolphin.ppm"));
    ImageModel dolphin3 = VerticalFlip.execute(load("res/dolphin.ppm"));
    ImageModel dolphin4 = VerticalFlip.execute(dolphin2);
    ImageModel dolphin5 = HorizontalFlip.execute(dolphin3);
    assertEquals(dolphin4, dolphin5);
    assertEquals(dolphin1, dolphin4);
    assertEquals(dolphin1, dolphin5);
  }

  @Test
  public void testHorizontalFlipTwice() {
    ImageModel dolphin1 = load("res/dolphin.ppm");
    ImageModel dolphin2 = HorizontalFlip.execute(load("res/dolphin.ppm"));
    ImageModel dolphin3 = HorizontalFlip.execute(dolphin2);
    assertEquals(dolphin1, dolphin3);
  }

  @Test
  public void testVerticalFlipTwice() {
    ImageModel dolphin1 = load("res/dolphin.ppm");
    ImageModel dolphin2 = VerticalFlip.execute(load("res/dolphin.ppm"));
    ImageModel dolphin3 = VerticalFlip.execute(dolphin2);
    assertEquals(dolphin1, dolphin3);
  }

  @Test
  public void testSave() throws IOException {
    ImageModel dolphin1 = load("res/dolphin.ppm");
    save("res/new-dolphin.ppm", dolphin1);
    ImageModel dolphin2 = load("res/new-dolphin.ppm");
    assertEquals(dolphin1, dolphin2);
  }

  @Test
  public void testOverwriteFile() throws IOException {
    ImageModel dolphin1 = load("res/dolphin.ppm");
    ImageModel brightDol = Brighten.execute(dolphin1, 50);
    save("res/dolphin.ppm", brightDol);
    ImageModel dolphin2 = load("res/dolphin.ppm");
    assertNotEquals(dolphin1, dolphin2);
    save("res/dolphin.ppm", dolphin1);
  }
}
