import org.junit.Test;

import java.io.StringReader;
import java.util.Scanner;

import cs3500.image.command.hw04.Load;
import cs3500.image.controller.hw05.ImageController2;
import cs3500.image.model.hw05.ImageGallery;
import cs3500.image.view.hw04.ImageViewImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Test class for the relationship between the new controller and view.
 */
public class Controller2Test {
  ImageGallery model;
  ImageViewImpl view;
  Readable in;
  Appendable out;
  ImageController2 controller;

  @Test(expected = IllegalStateException.class)
  public void testCorruptReadable() {
    in = new Corrupt();
    out = new StringBuilder();
    model = new ImageGallery();
    view = new ImageViewImpl();
    Scanner sc = new Scanner(in);
    assertFalse(sc.hasNext());
    controller = new ImageController2(model, view, in);
    controller.runProgram();
    assertEquals(out.toString(), "");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullReadable() {
    model = new ImageGallery();
    view = new ImageViewImpl();
    controller = new ImageController2(model, view, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullModel() {
    view = new ImageViewImpl();
    in = new StringReader("something");
    controller = new ImageController2(null, view, in);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullModelView() {
    in = new StringReader("something");
    controller = new ImageController2(null, null, in);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullModelReadable() {
    view = new ImageViewImpl();
    controller = new ImageController2(null, view, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullView() {
    model = new ImageGallery();
    in = new StringReader("something");
    controller = new ImageController2(model, null, in);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullViewAppendable() {
    view = new ImageViewImpl(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullReadableView() {
    model = new ImageGallery();
    controller = new ImageController2(model, null, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullAll() {
    controller = new ImageController2(null, null, null);
  }

  @Test
  public void testInstantQuitQ() {
    in = new StringReader("q");
    out = new StringBuilder();
    model = new ImageGallery();
    view = new ImageViewImpl(out);
    controller = new ImageController2(model, view, in);
    controller.runProgram();
    assertEquals(out.toString(), "Program quit." + System.lineSeparator());
  }

  @Test
  public void testInstantQuitQuitCaseAgnostic() {
    in = new StringReader("qUiT");
    out = new StringBuilder();
    model = new ImageGallery();
    view = new ImageViewImpl(out);
    controller = new ImageController2(model, view, in);
    controller.runProgram();
    assertEquals(out.toString(), "Program quit." + System.lineSeparator());
  }

  @Test(expected = IllegalStateException.class)
  public void testEmptyReader() {
    in = new StringReader("");
    out = new StringBuilder();
    model = new ImageGallery();
    view = new ImageViewImpl(out);
    controller = new ImageController2(model, view, in);
    controller.runProgram();
    assertEquals(out.toString(), "");
  }


  @Test(expected = IllegalStateException.class)
  public void testCommandNoQuit() {
    in = new StringReader("load res/dolphin.ppm dolphin");
    out = new StringBuilder();
    model = new ImageGallery();
    view = new ImageViewImpl(out);
    controller = new ImageController2(model, view, in);
    controller.runProgram();
    assertEquals(out.toString(), "Loaded res/dolphin.ppm to dolphin");
  }

  @Test
  public void testIllegalCommandQuit() {
    in = new StringReader("dolphin quit");
    out = new StringBuilder();
    model = new ImageGallery();
    view = new ImageViewImpl(out);
    controller = new ImageController2(model, view, in);
    controller.runProgram();
    assertEquals(out.toString(), "Illegal command!"
            + System.lineSeparator()
            + "Program quit." + System.lineSeparator());
  }

  @Test
  public void testLoadIllegalArgumentsQuit() {
    in = new StringReader("load dolphin.ppm dolphin quit");
    out = new StringBuilder();
    model = new ImageGallery();
    view = new ImageViewImpl(out);
    controller = new ImageController2(model, view, in);
    controller.runProgram();
    assertEquals(out.toString(), "File dolphin.ppm not found!"
            + System.lineSeparator()
            + "Program quit." + System.lineSeparator());
  }

  @Test
  public void testBrightenIllegalValueQuit() {
    in = new StringReader("load res/dolphin.ppm dolphin "
            + "brighten rew dolphin bright "
            + "quit");
    out = new StringBuilder();
    model = new ImageGallery();
    view = new ImageViewImpl(out);
    controller = new ImageController2(model, view, in);
    controller.runProgram();
    assertEquals(out.toString(), "Loaded res/dolphin.ppm to dolphin"
            + System.lineSeparator()
            + "Illegal value for brighten command."
            + System.lineSeparator()
            + "Illegal command!"
            + System.lineSeparator()
            + "Illegal command!"
            + System.lineSeparator()
            + "Illegal command!"
            + System.lineSeparator()
            + "Program quit." + System.lineSeparator());
  }

  @Test
  public void testBrightenIllegalImageQuit() {
    in = new StringReader("load res/dolphin.ppm dolphin "
            + "brighten 50 porpoise bright "
            + "quit");
    out = new StringBuilder();
    model = new ImageGallery();
    view = new ImageViewImpl(out);
    controller = new ImageController2(model, view, in);
    controller.runProgram();
    assertEquals(out.toString(), "Loaded res/dolphin.ppm to dolphin"
            + System.lineSeparator()
            + "Illegal arguments passed. Format is:"
            + System.lineSeparator()
            + "brighten [value] [image] [destination]"
            + System.lineSeparator()
            + "Program quit." + System.lineSeparator());
  }

  @Test
  public void testHorizontalFlipIllegalImageQuit() {
    in = new StringReader("load res/dolphin.ppm dolphin "
            + "horizontal-flip porpoise new "
            + "quit");
    out = new StringBuilder();
    model = new ImageGallery();
    view = new ImageViewImpl(out);
    controller = new ImageController2(model, view, in);
    controller.runProgram();
    assertEquals(out.toString(), "Loaded res/dolphin.ppm to dolphin"
            + System.lineSeparator()
            + "Illegal arguments passed. Format is:"
            + System.lineSeparator()
            + "horizontal-flip [image] [destination]"
            + System.lineSeparator()
            + "Program quit." + System.lineSeparator());
  }

  @Test
  public void testVerticalFlipIllegalImageQuit() {
    in = new StringReader("load res/dolphin.ppm dolphin "
            + "vertical-flip porpoise new "
            + "quit");
    out = new StringBuilder();
    model = new ImageGallery();
    view = new ImageViewImpl(out);
    controller = new ImageController2(model, view, in);
    controller.runProgram();
    assertEquals(out.toString(), "Loaded res/dolphin.ppm to dolphin"
            + System.lineSeparator()
            + "Illegal arguments passed. Format is:"
            + System.lineSeparator()
            + "vertical-flip [image] [destination]"
            + System.lineSeparator()
            + "Program quit." + System.lineSeparator());
  }

  @Test
  public void testRedGrayScaleIllegalImageQuit() {
    in = new StringReader("load res/dolphin.ppm dolphin "
            + "red-component porpoise new "
            + "quit");
    out = new StringBuilder();
    model = new ImageGallery();
    view = new ImageViewImpl(out);
    controller = new ImageController2(model, view, in);
    controller.runProgram();
    assertEquals(out.toString(), "Loaded res/dolphin.ppm to dolphin"
            + System.lineSeparator()
            + "Illegal arguments passed. Format is:"
            + System.lineSeparator()
            + "red-component [image] [destination]"
            + System.lineSeparator()
            + "Program quit." + System.lineSeparator());
  }

  @Test
  public void testGreenGrayScaleIllegalImageQuit() {
    in = new StringReader("load res/dolphin.ppm dolphin "
            + "green-component porpoise new "
            + "quit");
    out = new StringBuilder();
    model = new ImageGallery();
    view = new ImageViewImpl(out);
    controller = new ImageController2(model, view, in);
    controller.runProgram();
    assertEquals(out.toString(), "Loaded res/dolphin.ppm to dolphin"
            + System.lineSeparator()
            + "Illegal arguments passed. Format is:"
            + System.lineSeparator()
            + "green-component [image] [destination]"
            + System.lineSeparator()
            + "Program quit." + System.lineSeparator());
  }

  @Test
  public void testBlueGrayScaleIllegalImageQuit() {
    in = new StringReader("load res/dolphin.ppm dolphin "
            + "blue-component porpoise new "
            + "quit");
    out = new StringBuilder();
    model = new ImageGallery();
    view = new ImageViewImpl(out);
    controller = new ImageController2(model, view, in);
    controller.runProgram();
    assertEquals(out.toString(), "Loaded res/dolphin.ppm to dolphin"
            + System.lineSeparator()
            + "Illegal arguments passed. Format is:"
            + System.lineSeparator()
            + "blue-component [image] [destination]"
            + System.lineSeparator()
            + "Program quit." + System.lineSeparator());
  }

  @Test
  public void testLumaGrayScaleIllegalImageQuit() {
    in = new StringReader("load res/dolphin.ppm dolphin "
            + "luma-component porpoise new "
            + "quit");
    out = new StringBuilder();
    model = new ImageGallery();
    view = new ImageViewImpl(out);
    controller = new ImageController2(model, view, in);
    controller.runProgram();
    assertEquals(out.toString(), "Loaded res/dolphin.ppm to dolphin"
            + System.lineSeparator()
            + "Illegal arguments passed. Format is:"
            + System.lineSeparator()
            + "luma-component [image] [destination]"
            + System.lineSeparator()
            + "Program quit." + System.lineSeparator());
  }

  @Test
  public void testValueGrayScaleIllegalImageQuit() {
    in = new StringReader("load res/dolphin.ppm dolphin "
            + "value-component porpoise new "
            + "quit");
    out = new StringBuilder();
    model = new ImageGallery();
    view = new ImageViewImpl(out);
    controller = new ImageController2(model, view, in);
    controller.runProgram();
    assertEquals(out.toString(), "Loaded res/dolphin.ppm to dolphin"
            + System.lineSeparator()
            + "Illegal arguments passed. Format is:"
            + System.lineSeparator()
            + "value-component [image] [destination]"
            + System.lineSeparator()
            + "Program quit." + System.lineSeparator());
  }

  @Test
  public void testIntensityGrayScaleIllegalImageQuit() {
    in = new StringReader("load res/dolphin.ppm dolphin "
            + "intensity-component porpoise new "
            + "quit");
    out = new StringBuilder();
    model = new ImageGallery();
    view = new ImageViewImpl(out);
    controller = new ImageController2(model, view, in);
    controller.runProgram();
    assertEquals(out.toString(), "Loaded res/dolphin.ppm to dolphin"
            + System.lineSeparator()
            + "Illegal arguments passed. Format is:"
            + System.lineSeparator()
            + "intensity-component [image] [destination]"
            + System.lineSeparator()
            + "Program quit." + System.lineSeparator());
  }

  @Test
  public void testSaveIllegalImageQuit() {
    in = new StringReader("load res/dolphin.ppm dolphin "
            + "save res/porpoise.ppm porpoise "
            + "quit");
    out = new StringBuilder();
    model = new ImageGallery();
    view = new ImageViewImpl(out);
    controller = new ImageController2(model, view, in);
    controller.runProgram();
    assertEquals(out.toString(), "Loaded res/dolphin.ppm to dolphin"
            + System.lineSeparator()
            + "Illegal arguments passed. Format is:"
            + System.lineSeparator()
            + "save [filename] [image]"
            + System.lineSeparator()
            + "Program quit." + System.lineSeparator());
  }

  @Test
  public void testLoad() {
    in = new StringReader("load res/dolphin.ppm dolphin q");
    out = new StringBuilder();
    model = new ImageGallery();
    view = new ImageViewImpl(out);
    controller = new ImageController2(model, view, in);
    controller.runProgram();
    assertEquals(out.toString(), "Loaded res/dolphin.ppm to dolphin"
            + System.lineSeparator()
            + "Program quit." + System.lineSeparator());
  }

  @Test
  public void testBrighten() {
    in = new StringReader("load res/dolphin.ppm dolphin "
            + "brighten -30 dolphin dark-dolphin "
            + "q");
    out = new StringBuilder();
    model = new ImageGallery();
    view = new ImageViewImpl(out);
    controller = new ImageController2(model, view, in);
    controller.runProgram();
    assertEquals(out.toString(), "Loaded res/dolphin.ppm to dolphin"
            + System.lineSeparator()
            + "Brightened dolphin by -30; written to state dark-dolphin"
            + System.lineSeparator()
            + "Program quit." + System.lineSeparator());
  }

  @Test
  public void testHorizontalFlip() {
    in = new StringReader("load res/dolphin.ppm dolphin "
            + "horizontal-flip dolphin flip-dolphin "
            + "q");
    out = new StringBuilder();
    model = new ImageGallery();
    view = new ImageViewImpl(out);
    controller = new ImageController2(model, view, in);
    controller.runProgram();
    assertEquals(out.toString(), "Loaded res/dolphin.ppm to dolphin"
            + System.lineSeparator()
            + "Horizontally flipped dolphin; written to state flip-dolphin"
            + System.lineSeparator()
            + "Program quit." + System.lineSeparator());
  }

  @Test
  public void testVerticalFlip() {
    in = new StringReader("load res/dolphin.ppm dolphin "
            + "vertical-flip dolphin flip-dolphin "
            + "q");
    out = new StringBuilder();
    model = new ImageGallery();
    view = new ImageViewImpl(out);
    controller = new ImageController2(model, view, in);
    controller.runProgram();
    assertEquals(out.toString(), "Loaded res/dolphin.ppm to dolphin"
            + System.lineSeparator()
            + "Vertically flipped dolphin; written to state flip-dolphin"
            + System.lineSeparator()
            + "Program quit." + System.lineSeparator());
  }

  @Test
  public void testRedGrayScale() {
    in = new StringReader("load res/dolphin.ppm dolphin "
            + "red-component dolphin new-dolphin "
            + "q");
    out = new StringBuilder();
    model = new ImageGallery();
    view = new ImageViewImpl(out);
    controller = new ImageController2(model, view, in);
    controller.runProgram();
    assertEquals(out.toString(), "Loaded res/dolphin.ppm to dolphin"
            + System.lineSeparator()
            + "Red grayscale applied to dolphin; written to state new-dolphin"
            + System.lineSeparator()
            + "Program quit." + System.lineSeparator());
  }

  @Test
  public void testGreenGrayScale() {
    in = new StringReader("load res/dolphin.ppm dolphin "
            + "green-component dolphin new-dolphin "
            + "q");
    out = new StringBuilder();
    model = new ImageGallery();
    view = new ImageViewImpl(out);
    controller = new ImageController2(model, view, in);
    controller.runProgram();
    assertEquals(out.toString(), "Loaded res/dolphin.ppm to dolphin"
            + System.lineSeparator()
            + "Green grayscale applied to dolphin; written to state new-dolphin"
            + System.lineSeparator()
            + "Program quit." + System.lineSeparator());
  }

  @Test
  public void testBlueGrayScale() {
    in = new StringReader("load res/dolphin.ppm dolphin "
            + "blue-component dolphin new-dolphin "
            + "q");
    out = new StringBuilder();
    model = new ImageGallery();
    view = new ImageViewImpl(out);
    controller = new ImageController2(model, view, in);
    controller.runProgram();
    assertEquals(out.toString(), "Loaded res/dolphin.ppm to dolphin"
            + System.lineSeparator()
            + "Blue grayscale applied to dolphin; written to state new-dolphin"
            + System.lineSeparator()
            + "Program quit." + System.lineSeparator());
  }

  @Test
  public void testLumaGrayScale() {
    in = new StringReader("load res/dolphin.ppm dolphin "
            + "luma-component dolphin new-dolphin "
            + "q");
    out = new StringBuilder();
    model = new ImageGallery();
    view = new ImageViewImpl(out);
    controller = new ImageController2(model, view, in);
    controller.runProgram();
    assertEquals(out.toString(), "Loaded res/dolphin.ppm to dolphin"
            + System.lineSeparator()
            + "Luma grayscale applied to dolphin; written to state new-dolphin"
            + System.lineSeparator()
            + "Program quit." + System.lineSeparator());
  }

  @Test
  public void testValueGrayScale() {
    in = new StringReader("load res/dolphin.ppm dolphin "
            + "value-component dolphin new-dolphin "
            + "q");
    out = new StringBuilder();
    model = new ImageGallery();
    view = new ImageViewImpl(out);
    controller = new ImageController2(model, view, in);
    controller.runProgram();
    assertEquals(out.toString(), "Loaded res/dolphin.ppm to dolphin"
            + System.lineSeparator()
            + "Value grayscale applied to dolphin; written to state new-dolphin"
            + System.lineSeparator()
            + "Program quit." + System.lineSeparator());
  }

  @Test
  public void testIntensityGrayScale() {
    in = new StringReader("load res/dolphin.ppm dolphin "
            + "intensity-component dolphin new-dolphin "
            + "q");
    out = new StringBuilder();
    model = new ImageGallery();
    view = new ImageViewImpl(out);
    controller = new ImageController2(model, view, in);
    controller.runProgram();
    assertEquals(out.toString(), "Loaded res/dolphin.ppm to dolphin"
            + System.lineSeparator()
            + "Intensity grayscale applied to dolphin; written to state new-dolphin"
            + System.lineSeparator()
            + "Program quit." + System.lineSeparator());
  }

  @Test
  public void testSharpen() {
    in = new StringReader("load res/dolphin.ppm dolphin "
            + "sharpen dolphin new-dolphin "
            + "q");
    out = new StringBuilder();
    model = new ImageGallery();
    view = new ImageViewImpl(out);
    controller = new ImageController2(model, view, in);
    controller.runProgram();
    assertEquals(out.toString(), "Loaded res/dolphin.ppm to dolphin"
            + System.lineSeparator()
            + "Applied sharpen to dolphin; written to state new-dolphin"
            + System.lineSeparator()
            + "Program quit." + System.lineSeparator());
  }

  @Test
  public void testBlur() {
    in = new StringReader("load res/dolphin.ppm dolphin "
            + "blur dolphin new-dolphin "
            + "q");
    out = new StringBuilder();
    model = new ImageGallery();
    view = new ImageViewImpl(out);
    controller = new ImageController2(model, view, in);
    controller.runProgram();
    assertEquals(out.toString(), "Loaded res/dolphin.ppm to dolphin"
            + System.lineSeparator()
            + "Applied blur to dolphin; written to state new-dolphin"
            + System.lineSeparator()
            + "Program quit." + System.lineSeparator());
  }

  @Test
  public void testGreyscale() {
    in = new StringReader("load res/dolphin.ppm dolphin "
            + "greyscale dolphin new-dolphin "
            + "q");
    out = new StringBuilder();
    model = new ImageGallery();
    view = new ImageViewImpl(out);
    controller = new ImageController2(model, view, in);
    controller.runProgram();
    assertEquals(out.toString(), "Loaded res/dolphin.ppm to dolphin"
            + System.lineSeparator()
            + "Applied greyscale to dolphin; written to state new-dolphin"
            + System.lineSeparator()
            + "Program quit." + System.lineSeparator());
  }

  @Test
  public void testSepia() {
    in = new StringReader("load res/dolphin.ppm dolphin "
            + "sepia dolphin new-dolphin "
            + "q");
    out = new StringBuilder();
    model = new ImageGallery();
    view = new ImageViewImpl(out);
    controller = new ImageController2(model, view, in);
    controller.runProgram();
    assertEquals(out.toString(), "Loaded res/dolphin.ppm to dolphin"
            + System.lineSeparator()
            + "Applied sepia to dolphin; written to state new-dolphin"
            + System.lineSeparator()
            + "Program quit." + System.lineSeparator());
  }

  @Test
  public void testLoadPNG() {
    in = new StringReader("load res/dolphin.png dolphin "
            + "q");
    out = new StringBuilder();
    model = new ImageGallery();
    view = new ImageViewImpl(out);
    controller = new ImageController2(model, view, in);
    controller.runProgram();
    assertEquals(out.toString(), "Loaded res/dolphin.png to dolphin"
            + System.lineSeparator()
            + "Program quit." + System.lineSeparator());
  }

  @Test
  public void testLoadJPG() {
    in = new StringReader("load res/dolphin.jpg dolphin "
            + "q");
    out = new StringBuilder();
    model = new ImageGallery();
    view = new ImageViewImpl(out);
    controller = new ImageController2(model, view, in);
    controller.runProgram();
    assertEquals(out.toString(), "Loaded res/dolphin.jpg to dolphin"
            + System.lineSeparator()
            + "Program quit." + System.lineSeparator());
  }

  @Test
  public void testLoadBMP() {
    in = new StringReader("load res/dolphin.bmp dolphin "
            + "q");
    out = new StringBuilder();
    model = new ImageGallery();
    view = new ImageViewImpl(out);
    controller = new ImageController2(model, view, in);
    controller.runProgram();
    assertEquals(out.toString(), "Loaded res/dolphin.bmp to dolphin"
            + System.lineSeparator()
            + "Program quit." + System.lineSeparator());
  }

  @Test
  public void testLoadInvalid() {
    in = new StringReader("load res/dolphinppm dolphin "
            + "q");
    out = new StringBuilder();
    model = new ImageGallery();
    view = new ImageViewImpl(out);
    controller = new ImageController2(model, view, in);
    controller.runProgram();
    assertEquals(out.toString(), "Illegal file format: Legal formats are: ppm png jpg bmp"
            + System.lineSeparator()
            + "Program quit." + System.lineSeparator());
  }


  @Test
  public void testSaveSameImageCompare() {
    in = new StringReader("load res/dolphin.ppm dolphin "
            + "save res/new-dolphin.ppm dolphin "
            + "q");
    out = new StringBuilder();
    model = new ImageGallery();
    view = new ImageViewImpl(out);
    controller = new ImageController2(model, view, in);
    controller.runProgram();
    assertEquals(out.toString(), "Loaded res/dolphin.ppm to dolphin"
            + System.lineSeparator()
            + "State dolphin saved to res/new-dolphin.ppm"
            + System.lineSeparator()
            + "Program quit." + System.lineSeparator());
    assertEquals(Load.load("res/dolphin.ppm"), Load.load("res/new-dolphin.ppm"));
  }

  @Test
  public void testLoadNameTooSmall() {
    in = new StringReader("load ls "
            + "q");
    out = new StringBuilder();
    model = new ImageGallery();
    view = new ImageViewImpl(out);
    controller = new ImageController2(model, view, in);
    controller.runProgram();
    assertEquals(out.toString(), "The filename is too small!"
            + System.lineSeparator()
            + "Legal formats are: ppm png jpg bmp"
            + System.lineSeparator()
            + "Program quit." + System.lineSeparator());
    assertEquals(Load.load("res/dolphin.ppm"), Load.load("res/new-dolphin.ppm"));
  }

  @Test
  public void testSaveNameTooSmall() {
    in = new StringReader("load res/dolphin.ppm dolphin "
            + "save ls "
            + "q");
    out = new StringBuilder();
    model = new ImageGallery();
    view = new ImageViewImpl(out);
    controller = new ImageController2(model, view, in);
    controller.runProgram();
    assertEquals(out.toString(), "Loaded res/dolphin.ppm to dolphin"
            + System.lineSeparator()
            + "The filename is too small!"
            + System.lineSeparator()
            + "Legal formats are: ppm png jpg bmp"
            + System.lineSeparator()
            + "Program quit." + System.lineSeparator());
    assertEquals(Load.load("res/dolphin.ppm"), Load.load("res/new-dolphin.ppm"));
  }


}
