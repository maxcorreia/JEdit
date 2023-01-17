package cs3500.image.controller.hw05;

import java.io.IOException;
import java.util.Scanner;

import cs3500.image.command.hw04.BlueGreyScale;
import cs3500.image.command.hw04.Brighten;
import cs3500.image.command.hw04.GreenGreyScale;
import cs3500.image.command.hw04.HorizontalFlip;
import cs3500.image.command.hw04.IntensityGreyScale;
import cs3500.image.command.hw04.Load;
import cs3500.image.command.hw04.LumaGreyScale;
import cs3500.image.command.hw04.RedGreyScale;
import cs3500.image.command.hw04.Save;
import cs3500.image.command.hw04.ValueGreyScale;
import cs3500.image.command.hw04.VerticalFlip;
import cs3500.image.command.hw05.Blur;
import cs3500.image.command.hw05.Greyscale;
import cs3500.image.command.hw05.LoadNew;
import cs3500.image.command.hw05.SaveNew;
import cs3500.image.command.hw05.Sepia;
import cs3500.image.command.hw05.Sharpen;
import cs3500.image.controller.hw04.ImageController;
import cs3500.image.model.hw04.ImageModel;
import cs3500.image.model.hw04.ImageModelImpl;
import cs3500.image.model.hw05.ImageGallery;
import cs3500.image.model.hw05.ImageModelAlpha;
import cs3500.image.model.hw05.ImageModelAlphaImpl;
import cs3500.image.view.hw04.ImageView;

import static cs3500.image.command.hw05.LoadNew.genAlpha;

/**
 * An implementation of a controller for a text-based image manipulator.
 * New commands have been added, namely blur, sharpen, greyscale and sepia commands.
 * Multiple file types are now compatible with this controller,
 * with .png, .bmp, and .jpg files now being supported for loading and saving.
 */
public class ImageController2 implements ImageController {

  private final ImageView view;
  private final Readable readable;
  private final ImageGallery gallery;

  /**
   * Constructor for our new controller implementation.
   * The primary invariants are that the ImageGallery, ImageView
   * and the Readable it takes in are non-null.
   *
   * @param view the view to renderImage user input into
   */
  public ImageController2(ImageGallery gallery, ImageView view, Readable readable) {
    if ((gallery == null) || (view == null) || (readable == null)) {
      throw new IllegalArgumentException("None of the parameters can be null.");
    }
    this.gallery = gallery;
    this.view = view;
    this.readable = readable;
  }

  /**
   * Runs through the manipulator program.
   * The program waits for user input and works with the corresponding command.
   * The program terminates when "quit" or "q" is given to the controller.
   *
   * @throws IllegalStateException if the parameters passed to the controller are invalid
   */

  public void runProgram() throws IllegalStateException {
    Scanner sc = new Scanner(readable);
    boolean quit = false;
    while (!quit) {
      if (!sc.hasNext()) {
        throw new IllegalStateException("Scanner is empty.");
      }
      String userInstruction = sc.next();
      if (userInstruction.equalsIgnoreCase("quit")
              || userInstruction.equalsIgnoreCase("q")) {
        transmitMessage("Program quit.");
        quit = true;
      } else {
        processCommand(userInstruction, sc);
      }
    }
  }

  /**
   * Processes each of the commands that the user passes in.
   *
   * @param userInstruction The first String in the scanner to read user input
   * @param sc              The scanner to read from
   */
  private void processCommand(String userInstruction, Scanner sc) {
    String filename;
    String modelName;
    String modelNameTo;
    String filetype;

    ImageModelAlpha model;
    ImageModel convert;

    switch (userInstruction) {
      // Loads an image (ppm, png, bmp, jpg) to an ImageModelAlphaImpl
      case "load":
        filename = sc.next();
        try {
          filetype = filename.substring(filename.length() - 4);
        }
        catch (StringIndexOutOfBoundsException e) {
          transmitMessage("The filename is too small!");
          transmitMessage("Legal formats are: ppm png jpg bmp");
          return;
        }
        modelName = sc.next();
        if (".ppm".equals(filetype)) {
          try {
            ImageModel file = Load.load(filename);
            gallery.put(modelName,
                    new ImageModelAlphaImpl(new ImageModelImpl(file.getWidth(),
                            file.getHeight(),
                            file.getMaxValue(),
                            LoadNew.genPPMImage(file)),
                            LoadNew.genOpaqueAlpha(file)));
            transmitMessage("Loaded " + filename + " to " + modelName);
          } catch (Exception e) {
            transmitMessage(e.getMessage());
          }
          return;
        }
        if (!(filetype.equals(".png") || filetype.equals(".bmp") || filetype.equals(".jpg"))) {
          transmitMessage("Illegal file format: Legal formats are: ppm png jpg bmp");
          return;
        }
        try {
          gallery.put(modelName, LoadNew.load(filename));
        } catch (IOException e) {
          transmitMessage(e.getMessage());
        }
        transmitMessage("Loaded " + filename + " to " + modelName);
        return;
      // Brightens an image by a given value, then adds a new state to the Hashmap
      case "brighten":
        int val;
        try {
          val = sc.nextInt();
        } catch (Exception e) {
          transmitMessage("Illegal value for brighten command.");
          return;
        }
        modelName = sc.next();
        modelNameTo = sc.next();
        try {
          model = gallery.get(modelName);
          convert = new ImageModelImpl(model.getWidth(),
                  model.getHeight(),
                  model.getMaxValue(),
                  LoadNew.genPPMImage(model));
          gallery.put(modelNameTo,
                  new ImageModelAlphaImpl(Brighten.execute(convert, val), genAlpha(model)));
          transmitMessage("Brightened " + modelName + " by " + val + "; written to state "
                  + modelNameTo);
        } catch (Exception e) {
          transmitMessage("Illegal arguments passed. Format is:"
                  + System.lineSeparator()
                  + "brighten [value] [image] [destination]");
        }
        return;
      // Horizontally flips an image, then adds a new state to the Hashmap
      case "horizontal-flip":
        modelName = sc.next();
        modelNameTo = sc.next();
        try {
          model = gallery.get(modelName);
          convert = new ImageModelImpl(model.getWidth(),
                  model.getHeight(),
                  model.getMaxValue(),
                  LoadNew.genPPMImage(model));
          gallery.put(modelNameTo,
                  new ImageModelAlphaImpl(HorizontalFlip.execute(convert), genAlpha(model)));
          transmitMessage("Horizontally flipped " + modelName + "; written to state "
                  + modelNameTo);
        } catch (Exception e) {
          transmitMessage("Illegal arguments passed. Format is:"
                  + System.lineSeparator()
                  + "horizontal-flip [image] [destination]");
        }
        return;
      // Vertically flips an image, then adds a new state to the Hashmap
      case "vertical-flip":
        modelName = sc.next();
        modelNameTo = sc.next();
        try {
          model = gallery.get(modelName);
          convert = new ImageModelImpl(model.getWidth(),
                  model.getHeight(),
                  model.getMaxValue(),
                  LoadNew.genPPMImage(model));
          gallery.put(modelNameTo,
                  new ImageModelAlphaImpl(VerticalFlip.execute(convert), genAlpha(model)));
          transmitMessage("Vertically flipped " + modelName + "; written to state "
                  + modelNameTo);
        } catch (Exception e) {
          transmitMessage("Illegal arguments passed. Format is:"
                  + System.lineSeparator()
                  + "vertical-flip [image] [destination]");
        }
        return;
      // Creates a gray image with the image's red values, then adds a new state to the Hashmap
      case "red-component":
        modelName = sc.next();
        modelNameTo = sc.next();
        try {
          model = gallery.get(modelName);
          convert = new ImageModelImpl(model.getWidth(),
                  model.getHeight(),
                  model.getMaxValue(),
                  LoadNew.genPPMImage(model));
          gallery.put(modelNameTo,
                  new ImageModelAlphaImpl(RedGreyScale.execute(convert), genAlpha(model)));
          transmitMessage("Red grayscale applied to " + modelName + "; written to state "
                  + modelNameTo);
        } catch (Exception e) {
          transmitMessage("Illegal arguments passed. Format is:"
                  + System.lineSeparator()
                  + "red-component [image] [destination]");
        }
        return;
      // Creates a gray image with the image's green values, then adds a new state to the Hashmap
      case "green-component":
        modelName = sc.next();
        modelNameTo = sc.next();
        try {
          model = gallery.get(modelName);
          convert = new ImageModelImpl(model.getWidth(),
                  model.getHeight(),
                  model.getMaxValue(),
                  LoadNew.genPPMImage(model));
          gallery.put(modelNameTo,
                  new ImageModelAlphaImpl(GreenGreyScale.execute(convert), genAlpha(model)));
          transmitMessage("Green grayscale applied to " + modelName + "; written to state "
                  + modelNameTo);
        } catch (Exception e) {
          transmitMessage("Illegal arguments passed. Format is:"
                  + System.lineSeparator()
                  + "green-component [image] [destination]");
        }
        return;
      // Creates a gray image with the image's blue values, then adds a new state to the Hashmap
      case "blue-component":
        modelName = sc.next();
        modelNameTo = sc.next();
        try {
          model = gallery.get(modelName);
          convert = new ImageModelImpl(model.getWidth(),
                  model.getHeight(),
                  model.getMaxValue(),
                  LoadNew.genPPMImage(model));
          gallery.put(modelNameTo,
                  new ImageModelAlphaImpl(BlueGreyScale.execute(convert), genAlpha(model)));
          transmitMessage("Blue grayscale applied to " + modelName + "; written to state "
                  + modelNameTo);
        } catch (Exception e) {
          transmitMessage("Illegal arguments passed. Format is:"
                  + System.lineSeparator()
                  + "blue-component [image] [destination]");
        }
        return;
      // Creates an image with the image's luma values, then adds a new state to the Hashmap
      case "luma-component":
        modelName = sc.next();
        modelNameTo = sc.next();
        try {
          model = gallery.get(modelName);
          convert = new ImageModelImpl(model.getWidth(),
                  model.getHeight(),
                  model.getMaxValue(),
                  LoadNew.genPPMImage(model));
          gallery.put(modelNameTo,
                  new ImageModelAlphaImpl(LumaGreyScale.execute(convert), genAlpha(model)));
          transmitMessage("Luma grayscale applied to " + modelName + "; written to state "
                  + modelNameTo);
        } catch (Exception e) {
          transmitMessage("Illegal arguments passed. Format is:"
                  + System.lineSeparator()
                  + "luma-component [image] [destination]");
        }
        return;
      // Creates an image with the image's largest color values, adding a new state to the Hashmap
      case "value-component":
        modelName = sc.next();
        modelNameTo = sc.next();
        try {
          model = gallery.get(modelName);
          convert = new ImageModelImpl(model.getWidth(),
                  model.getHeight(),
                  model.getMaxValue(),
                  LoadNew.genPPMImage(model));
          gallery.put(modelNameTo,
                  new ImageModelAlphaImpl(ValueGreyScale.execute(convert), genAlpha(model)));
          transmitMessage("Value grayscale applied to " + modelName + "; written to state "
                  + modelNameTo);
        } catch (Exception e) {
          transmitMessage("Illegal arguments passed. Format is:"
                  + System.lineSeparator()
                  + "value-component [image] [destination]");
        }
        return;
      // Creates an image with the image's average color values, adding a new state to the Hashmap
      case "intensity-component":
        modelName = sc.next();
        modelNameTo = sc.next();
        try {
          model = gallery.get(modelName);
          convert = new ImageModelImpl(model.getWidth(),
                  model.getHeight(),
                  model.getMaxValue(),
                  LoadNew.genPPMImage(model));
          gallery.put(modelNameTo,
                  new ImageModelAlphaImpl(IntensityGreyScale.execute(convert), genAlpha(model)));
          transmitMessage("Intensity grayscale applied to " + modelName + "; written to state "
                  + modelNameTo);
        } catch (Exception e) {
          transmitMessage("Illegal arguments passed. Format is:"
                  + System.lineSeparator()
                  + "intensity-component [image] [destination]");
        }
        return;
      // Creates a gray image with the image's luma values, then adds a new state to the Hashmap
      case "greyscale":
        modelName = sc.next();
        modelNameTo = sc.next();
        try {
          gallery.put(modelNameTo, Greyscale.execute(gallery.get(modelName)));
          transmitMessage("Applied greyscale to " + modelName + "; written to state "
                  + modelNameTo);
        } catch (Exception e) {
          transmitMessage("Illegal arguments passed. Format is:"
                  + System.lineSeparator()
                  + "greyscale [image] [destination]");
        }
        return;
      // Creates a sepia image with the image's color values, then adds a new state to the Hashmap
      case "sepia":
        modelName = sc.next();
        modelNameTo = sc.next();
        try {
          gallery.put(modelNameTo, Sepia.execute(gallery.get(modelName)));
          transmitMessage("Applied sepia to " + modelName + "; written to state "
                  + modelNameTo);
        } catch (Exception e) {
          transmitMessage("Illegal arguments passed. Format is:"
                  + System.lineSeparator()
                  + "sepia [image] [destination]");
        }
        return;
      // Blurs an image based on its color values, then adds a new state to the Hashmap
      case "blur":
        modelName = sc.next();
        modelNameTo = sc.next();
        try {
          gallery.put(modelNameTo, Blur.execute(gallery.get(modelName)));
          transmitMessage("Applied blur to " + modelName + "; written to state "
                  + modelNameTo);
        } catch (Exception e) {
          transmitMessage("Illegal arguments passed. Format is:"
                  + System.lineSeparator()
                  + "blur [image] [destination]");
        }
        return;
      // Sharpens an image based on its color values, then adds a new state to the Hashmap
      case "sharpen":
        modelName = sc.next();
        modelNameTo = sc.next();
        try {
          gallery.put(modelNameTo, Sharpen.execute(gallery.get(modelName)));
          transmitMessage("Applied sharpen to " + modelName + "; written to state "
                  + modelNameTo);
        } catch (Exception e) {
          transmitMessage("Illegal arguments passed. Format is:"
                  + System.lineSeparator()
                  + "sharpen [image] [destination]");
        }
        return;
      // Saves an image state to a file (ppm, png, bmp, jpg)
      case "save":
        filename = sc.next();
        try {
          filetype = filename.substring(filename.length() - 4);
        }
        catch (StringIndexOutOfBoundsException e) {
          transmitMessage("The filename is too small!");
          transmitMessage("Legal formats are: ppm png jpg bmp");
          return;
        }
        modelName = sc.next();
        switch (filetype) {
          case ".ppm":
            try {
              model = gallery.get(modelName);
              convert = new ImageModelImpl(model.getWidth(),
                      model.getHeight(),
                      model.getMaxValue(),
                      LoadNew.genPPMImage(model));
              Save.save(filename, convert);
              transmitMessage("State " + modelName + " saved to "
                      + filename);
            } catch (Exception e) {
              transmitMessage("Illegal arguments passed. Format is:"
                      + System.lineSeparator()
                      + "save [filename] [image]");
            }
            return;
          case ".png":
            try {
              SaveNew.saveAlpha(filename, "png", gallery.get(modelName));
              transmitMessage("State " + modelName + " saved to "
                      + filename);
            } catch (Exception e) {
              transmitMessage("Illegal arguments passed. Format is:"
                      + System.lineSeparator()
                      + "save [filename] [image]");
            }
            return;
          case ".bmp":
            try {
              SaveNew.save(filename, "bmp", gallery.get(modelName));
              transmitMessage("State " + modelName + " saved to "
                      + filename);
            } catch (Exception e) {
              transmitMessage("Illegal arguments passed. Format is:"
                      + System.lineSeparator()
                      + "save [filename] [image]");
            }
            return;
          case ".jpg":
            try {
              SaveNew.save(filename, "jpg", gallery.get(modelName));
              transmitMessage("State " + modelName + " saved to "
                      + filename);
            } catch (Exception e) {
              transmitMessage("Illegal arguments passed. Format is:"
                      + System.lineSeparator()
                      + "save [filename] [image]");
            }
            return;
          default:
            transmitMessage("Illegal file format: Legal formats are: ppm png jpg bmp");
            return;
        }

      default:
        transmitMessage("Illegal command!");
    }
  }

  /**
   * Transmits a message to this controller's reader.
   *
   * @param message the message to transmit
   * @throws IllegalStateException if the transfer is invalid
   */
  protected void transmitMessage(String message) throws IllegalStateException {
    try {
      this.view.renderMessage(message);
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

}
