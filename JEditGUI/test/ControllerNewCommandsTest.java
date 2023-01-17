import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;

import cs3500.image.command.hw04.Load;
import cs3500.image.command.hw05.Blur;
import cs3500.image.command.hw05.Greyscale;
import cs3500.image.command.hw05.LoadNew;
import cs3500.image.command.hw05.Sepia;
import cs3500.image.command.hw05.Sharpen;
import cs3500.image.controller.hw04.ImageController;
import cs3500.image.controller.hw05.ImageController2;
import cs3500.image.model.hw05.ImageGallery;
import cs3500.image.model.hw05.ImageModelAlphaImpl;
import cs3500.image.view.hw04.ImageViewImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Tests the new commands added to our new controller, along with file support.
 */
public class ControllerNewCommandsTest {

  ImageGallery model;
  ImageViewImpl view;
  Readable in;
  Appendable out;
  ImageController controller;

  @Test
  public void testSepiaOnTransparentPNG() throws IOException {
    in = new StringReader("load res/dots.png dots "
            + "sepia dots new-dots "
            + "save res/dots-sepia.png new-dots q");
    out = new StringBuilder();
    model = new ImageGallery();
    view = new ImageViewImpl(out);
    controller = new ImageController2(model, view, in);
    controller.runProgram();
    assertEquals(LoadNew.load("res/dots-sepia.png"),
            Sepia.execute(LoadNew.load("res/dots.png")));
  }

  @Test
  public void testGreyscaleOnTransparentPNG() throws IOException {
    in = new StringReader("load res/dots.png dots "
            + "greyscale dots new-dots "
            + "save res/dots-greyscale.png new-dots q");
    out = new StringBuilder();
    model = new ImageGallery();
    view = new ImageViewImpl(out);
    controller = new ImageController2(model, view, in);
    controller.runProgram();
    assertEquals(LoadNew.load("res/dots-greyscale.png"),
            Greyscale.execute(LoadNew.load("res/dots.png")));
  }

  @Test
  public void testBlurOnTransparentPNG() throws IOException {
    in = new StringReader("load res/dots.png dots "
            + "blur dots new-dots "
            + "save res/dots-blur.png new-dots q");
    out = new StringBuilder();
    model = new ImageGallery();
    view = new ImageViewImpl(out);
    controller = new ImageController2(model, view, in);
    controller.runProgram();
    assertEquals(LoadNew.load("res/dots-blur.png"),
            Blur.execute(LoadNew.load("res/dots.png")));
  }

  @Test
  public void testSharpenOnTransparentPNG() throws IOException {
    in = new StringReader("load res/dots.png dots "
            + "sharpen dots new-dots "
            + "save res/dots-sharpen.png new-dots q");
    out = new StringBuilder();
    model = new ImageGallery();
    view = new ImageViewImpl(out);
    controller = new ImageController2(model, view, in);
    controller.runProgram();
    assertEquals(LoadNew.load("res/dots-sharpen.png"),
            Sharpen.execute(LoadNew.load("res/dots.png")));
  }

  @Test
  public void testPPMToPNG() throws IOException {
    in = new StringReader("load res/dolphin.ppm dolphin "
            + "save res/dolphin.png dolphin q");
    out = new StringBuilder();
    model = new ImageGallery();
    view = new ImageViewImpl(out);
    controller = new ImageController2(model, view, in);
    controller.runProgram();
    assertEquals(new ImageModelAlphaImpl(Load.load("res/dolphin.ppm"),
                    LoadNew.genOpaqueAlpha(Load.load("res/dolphin.ppm"))),
            LoadNew.load("res/dolphin.png"));
  }

  @Test
  public void testPPMToBMP() throws IOException {
    in = new StringReader("load res/dolphin.ppm dolphin "
            + "save res/dolphin.bmp dolphin q");
    out = new StringBuilder();
    model = new ImageGallery();
    view = new ImageViewImpl(out);
    controller = new ImageController2(model, view, in);
    controller.runProgram();
    assertEquals(new ImageModelAlphaImpl(Load.load("res/dolphin.ppm"),
                    LoadNew.genOpaqueAlpha(Load.load("res/dolphin.ppm"))),
            LoadNew.load("res/dolphin.bmp"));
  }

  // This test fails due to how JPG files are compressed
  @Test
  public void testPPMToJPG() throws IOException {
    in = new StringReader("load res/dolphin.ppm dolphin "
            + "save res/dolphin.jpg dolphin q");
    out = new StringBuilder();
    model = new ImageGallery();
    view = new ImageViewImpl(out);
    controller = new ImageController2(model, view, in);
    controller.runProgram();
    assertNotEquals(new ImageModelAlphaImpl(Load.load("res/dolphin.ppm"),
                    LoadNew.genOpaqueAlpha(Load.load("res/dolphin.ppm"))),
            LoadNew.load("res/dolphin.jpg"));
  }

  @Test
  public void testBMPtoPPM() throws IOException {
    in = new StringReader("load res/dots-new.bmp dots "
            + "save res/dots-new.ppm dots q");
    out = new StringBuilder();
    model = new ImageGallery();
    view = new ImageViewImpl(out);
    controller = new ImageController2(model, view, in);
    controller.runProgram();
    assertEquals(new ImageModelAlphaImpl(Load.load("res/dots-new.ppm"),
                    LoadNew.genOpaqueAlpha(Load.load("res/dots-new.ppm"))),
            (LoadNew.load("res/dots-new.bmp")));
  }

  @Test
  public void testBMPtoJPG() throws IOException {
    in = new StringReader("load res/dots-new.bmp dots "
            + "save res/dots-new.jpg dots q");
    out = new StringBuilder();
    model = new ImageGallery();
    view = new ImageViewImpl(out);
    controller = new ImageController2(model, view, in);
    controller.runProgram();
    assertNotEquals(LoadNew.load("res/dots-new.jpg"),
            (LoadNew.load("res/dots-new.bmp")));
  }

  @Test
  public void testBMPtoPNG() throws IOException {
    in = new StringReader("load res/dots-new.bmp dots "
            + "save res/dots-new.png dots q");
    out = new StringBuilder();
    model = new ImageGallery();
    view = new ImageViewImpl(out);
    controller = new ImageController2(model, view, in);
    controller.runProgram();
    assertEquals(LoadNew.load("res/dots-new.bmp"),
            LoadNew.load("res/dots-new.png"));
  }

  @Test
  public void testPNGToPPM() throws IOException {
    in = new StringReader("load res/dolphin.png dolphin "
            + "save res/dolphin.ppm dolphin q");
    out = new StringBuilder();
    model = new ImageGallery();
    view = new ImageViewImpl(out);
    controller = new ImageController2(model, view, in);
    controller.runProgram();
    assertEquals(new ImageModelAlphaImpl(Load.load("res/dolphin.ppm"),
                    LoadNew.genOpaqueAlpha(Load.load("res/dolphin.ppm"))),
            (LoadNew.load("res/dolphin.png")));
  }

  @Test
  public void testPNGToBMP() throws IOException {
    in = new StringReader("load res/dolphin.png dolphin "
            + "save res/dolphin-png.bmp dolphin q");
    out = new StringBuilder();
    model = new ImageGallery();
    view = new ImageViewImpl(out);
    controller = new ImageController2(model, view, in);
    controller.runProgram();
    assertEquals(LoadNew.load("res/dolphin.png"),
            LoadNew.load("res/dolphin-png.bmp"));
  }

  @Test
  public void testPNGToJPG() throws IOException {
    in = new StringReader("load res/dolphin.png dolphin "
            + "save res/dolphin-png.jpg dolphin q");
    out = new StringBuilder();
    model = new ImageGallery();
    view = new ImageViewImpl(out);
    controller = new ImageController2(model, view, in);
    controller.runProgram();
    assertNotEquals(LoadNew.load("res/dolphin.png"),
            LoadNew.load("res/dolphin-png.jpg"));
  }

  @Test
  public void testJPGToPNG() throws IOException {
    in = new StringReader("load res/dolphin.jpg dolphin "
            + "save res/dolphin-jpg.png dolphin q");
    out = new StringBuilder();
    model = new ImageGallery();
    view = new ImageViewImpl(out);
    controller = new ImageController2(model, view, in);
    controller.runProgram();
    assertEquals(LoadNew.load("res/dolphin.jpg"),
            LoadNew.load("res/dolphin-jpg.png"));
  }

  @Test
  public void testJPGToBMP() throws IOException {
    in = new StringReader("load res/dolphin.jpg dolphin "
            + "save res/dolphin-jpg.bmp dolphin q");
    out = new StringBuilder();
    model = new ImageGallery();
    view = new ImageViewImpl(out);
    controller = new ImageController2(model, view, in);
    controller.runProgram();
    assertEquals(LoadNew.load("res/dolphin.jpg"),
            LoadNew.load("res/dolphin-jpg.bmp"));
  }

  @Test
  public void testJPGToPPM() throws IOException {
    in = new StringReader("load res/dolphin.jpg dolphin "
            + "save res/dolphin-jpg.ppm dolphin q");
    out = new StringBuilder();
    model = new ImageGallery();
    view = new ImageViewImpl(out);
    controller = new ImageController2(model, view, in);
    controller.runProgram();
    assertEquals(LoadNew.load("res/dolphin.jpg"),
            new ImageModelAlphaImpl(Load.load("res/dolphin-jpg.ppm"),
                    LoadNew.genOpaqueAlpha(Load.load("res/dolphin-jpg.ppm"))));
  }

  @Test
  public void testTransparentPNGToJPG() throws IOException {
    in = new StringReader("load res/dots.png dots "
            + "save res/dots-new.jpg dots q");
    out = new StringBuilder();
    model = new ImageGallery();
    view = new ImageViewImpl(out);
    controller = new ImageController2(model, view, in);
    controller.runProgram();
    assertNotEquals(LoadNew.load("res/dots-new.jpg"),
            (LoadNew.load("res/dots.png")));
  }

  @Test
  public void testTransparentPNGToBMP() throws IOException {
    in = new StringReader("load res/dots.png dots "
            + "save res/dots-new.bmp dots q");
    out = new StringBuilder();
    model = new ImageGallery();
    view = new ImageViewImpl(out);
    controller = new ImageController2(model, view, in);
    controller.runProgram();
    assertNotEquals(LoadNew.load("res/dots-new.bmp"),
            (LoadNew.load("res/dots.png")));
  }

  @Test
  public void testTransparentPNGtoPPM() throws IOException {
    in = new StringReader("load res/dots.png dots "
            + "save res/dots.ppm dots q");
    out = new StringBuilder();
    model = new ImageGallery();
    view = new ImageViewImpl(out);
    controller = new ImageController2(model, view, in);
    controller.runProgram();
    assertNotEquals(new ImageModelAlphaImpl(Load.load("res/dots.ppm"),
                    LoadNew.genOpaqueAlpha(Load.load("res/dots.ppm"))),
            (LoadNew.load("res/dots.png")));
  }


  @Test
  public void testSepiaOnPPM() throws IOException {
    in = new StringReader("load res/dolphin.ppm dolphin "
            + "sepia dolphin dol-sep "
            + "save res/dolphin-sepia.ppm dol-sep q");
    out = new StringBuilder();
    model = new ImageGallery();
    view = new ImageViewImpl(out);
    controller = new ImageController2(model, view, in);
    controller.runProgram();
    assertEquals(new ImageModelAlphaImpl(Load.load("res/dolphin-sepia.ppm"),
                    LoadNew.genOpaqueAlpha(Load.load("res/dolphin-sepia.ppm"))),
            Sepia.execute(new ImageModelAlphaImpl(Load.load("res/dolphin.ppm"),
                    LoadNew.genOpaqueAlpha(Load.load("res/dolphin.ppm")))));
  }

  @Test
  public void testGreyscaleOnPPM() throws IOException {
    in = new StringReader("load res/dolphin.ppm dolphin "
            + "greyscale dolphin dol-grey "
            + "save res/dolphin-greyscale.ppm dol-grey q");
    out = new StringBuilder();
    model = new ImageGallery();
    view = new ImageViewImpl(out);
    controller = new ImageController2(model, view, in);
    controller.runProgram();
    assertEquals(new ImageModelAlphaImpl(Load.load("res/dolphin-greyscale.ppm"),
                    LoadNew.genOpaqueAlpha(Load.load("res/dolphin-greyscale.ppm"))),
            Greyscale.execute(new ImageModelAlphaImpl(Load.load("res/dolphin.ppm"),
                    LoadNew.genOpaqueAlpha(Load.load("res/dolphin.ppm")))));
  }

  @Test
  public void testBlurOnPPM() throws IOException {
    in = new StringReader("load res/dolphin.ppm dolphin "
            + "blur dolphin dol-blur "
            + "save res/dolphin-blur.ppm dol-blur q");
    out = new StringBuilder();
    model = new ImageGallery();
    view = new ImageViewImpl(out);
    controller = new ImageController2(model, view, in);
    controller.runProgram();
    assertEquals(new ImageModelAlphaImpl(Load.load("res/dolphin-blur.ppm"),
                    LoadNew.genOpaqueAlpha(Load.load("res/dolphin-blur.ppm"))),
            Blur.execute(new ImageModelAlphaImpl(Load.load("res/dolphin.ppm"),
                    LoadNew.genOpaqueAlpha(Load.load("res/dolphin.ppm")))));
  }

  @Test
  public void testSharpenOnPPM() throws IOException {
    in = new StringReader("load res/dolphin.ppm dolphin "
            + "sharpen dolphin dol-sharp "
            + "save res/dolphin-sharpen.ppm dol-sharp q");
    out = new StringBuilder();
    model = new ImageGallery();
    view = new ImageViewImpl(out);
    controller = new ImageController2(model, view, in);
    controller.runProgram();
    assertEquals(new ImageModelAlphaImpl(Load.load("res/dolphin-sharpen.ppm"),
                    LoadNew.genOpaqueAlpha(Load.load("res/dolphin-sharpen.ppm"))),
            Sharpen.execute(new ImageModelAlphaImpl(Load.load("res/dolphin.ppm"),
                    LoadNew.genOpaqueAlpha(Load.load("res/dolphin.ppm")))));
  }

}
