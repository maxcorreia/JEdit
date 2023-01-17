import org.junit.Test;

import cs3500.image.RGBColor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * This test class is meant to test that RGB colors are constructed and retrieved correctly.
 */
public class RGBColorTest {

  @Test(expected = IllegalArgumentException.class)
  public void negativeRedValue() {
    RGBColor invalidRedValue = new RGBColor(-1,5, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void negativeGreenValue() {
    RGBColor invalidGreenValue = new RGBColor(5,-1, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void negativeBlueValue() {
    RGBColor invalidBlueValue = new RGBColor(5,5, -1);
  }

  @Test
  public void RGBColorEquals() {
    RGBColor color1 = new RGBColor(1, 2, 3);
    RGBColor color2 = new RGBColor(1, 2, 3);
    assertEquals(color1, color2);
    RGBColor color3 = new RGBColor(5, 2, 3);
    RGBColor color4 = new RGBColor(1, 2, 3);
    assertNotEquals(color3, color4);
    RGBColor color5 = new RGBColor(1, 5, 3);
    RGBColor color6 = new RGBColor(1, 2, 3);
    assertNotEquals(color5, color6);
    RGBColor color7 = new RGBColor(1, 2, 5);
    RGBColor color8 = new RGBColor(1, 2, 3);
    assertNotEquals(color7, color8);
    RGBColor color9 = new RGBColor(1, 2, 3);
    RGBColor color10 = color9;
    assertEquals(color9, color10);
    RGBColor color11 = new RGBColor(1, 2, 3);
    RGBColor color12 = new RGBColor(1,2,3);
    assertEquals(color11, color12);
  }

  @Test
  public void testGetRGBColorFields() {
    RGBColor testRGBC = new RGBColor(1,2,3);
    int red = testRGBC.getRed();
    int green = testRGBC.getGreen();
    int blue = testRGBC.getBlue();
    assertEquals(1, red);
    assertEquals(2, green);
    assertEquals(3, blue);
  }
}
