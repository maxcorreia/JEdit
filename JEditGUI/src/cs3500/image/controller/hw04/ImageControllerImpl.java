package cs3500.image.controller.hw04;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import cs3500.image.command.hw04.BlueGreyScale;
import cs3500.image.command.hw04.Brighten;
import cs3500.image.command.hw04.GreenGreyScale;
import cs3500.image.command.hw04.HorizontalFlip;
import cs3500.image.command.hw04.IntensityGreyScale;
import cs3500.image.command.hw04.LumaGreyScale;
import cs3500.image.command.hw04.RedGreyScale;
import cs3500.image.command.hw04.ValueGreyScale;
import cs3500.image.command.hw04.VerticalFlip;
import cs3500.image.model.hw04.ImageModel;
import cs3500.image.view.hw04.ImageView;

import static cs3500.image.command.hw04.Load.load;
import static cs3500.image.command.hw04.Save.save;

/**
 * An implementation of a controller for a text-based image manipulator.
 * Users can input different commands to create new images, as well as load and save said images.
 * This controller has a HashMap that pairs each new model with a given String name.
 * These represent different named "states", which can then be written to some files
 * using the save command. This implementation was done due to how users interact with ImageModels,
 * as each newly "created" model is not explicitly saved to an associate file
 * (at least not without user interaction).
 */
public class ImageControllerImpl implements ImageController {

  protected final HashMap<String, ImageModel> modelList;
  protected final ImageView view;

  protected final Readable readable;

  /**
   * Constructor for our controller implementation.
   * The primary invariants are that the ImageView
   * and the Readable it takes in are non-null.
   *
   * @param view the view to renderImage user input into
   */
  public ImageControllerImpl(ImageView view, Readable readable) {
    if ((view == null) || (readable == null)) {
      throw new IllegalArgumentException("None of the parameters can be null.");
    }
    this.modelList = new HashMap<>();
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
  @Override
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
  protected void processCommand(String userInstruction, Scanner sc) {
    String filename;
    String modelName;
    String modelNameTo;
    switch (userInstruction) {
      case "load":
        filename = sc.next();
        modelName = sc.next();
        try {
          modelList.put(modelName, load(filename));
          transmitMessage("Loaded " + filename + " to " + modelName);
        } catch (IllegalStateException e) {
          transmitMessage(e.getMessage());
        }
        return;
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
          modelList.put(modelNameTo, Brighten.execute(modelList.get(modelName), val));
          transmitMessage("Brightened " + modelName + " by " + val + "; written to state "
                  + modelNameTo);
        } catch (Exception e) {
          transmitMessage("Illegal arguments passed. Format is:"
                  + System.lineSeparator()
                  + "brighten [value] [image] [destination]");
        }
        return;
      case "horizontal-flip":
        modelName = sc.next();
        modelNameTo = sc.next();
        try {
          modelList.put(modelNameTo, HorizontalFlip.execute(modelList.get(modelName)));
          transmitMessage("Horizontally flipped " + modelName + "; written to state "
                  + modelNameTo);
        } catch (Exception e) {
          transmitMessage("Illegal arguments passed. Format is:"
                  + System.lineSeparator()
                  + "horizontal-flip [image] [destination]");
        }
        return;
      case "vertical-flip":
        modelName = sc.next();
        modelNameTo = sc.next();
        try {
          modelList.put(modelNameTo, VerticalFlip.execute(modelList.get(modelName)));
          transmitMessage("Vertically flipped " + modelName + "; written to state "
                  + modelNameTo);
        } catch (Exception e) {
          transmitMessage("Illegal arguments passed. Format is:"
                  + System.lineSeparator()
                  + "vertical-flip [image] [destination]");
        }
        return;
      case "red-component":
        modelName = sc.next();
        modelNameTo = sc.next();
        try {
          modelList.put(modelNameTo, RedGreyScale.execute(modelList.get(modelName)));
          transmitMessage("Red grayscale applied to " + modelName + "; written to state "
                  + modelNameTo);
        } catch (Exception e) {
          transmitMessage("Illegal arguments passed. Format is:"
                  + System.lineSeparator()
                  + "red-component [image] [destination]");
        }
        return;
      case "green-component":
        modelName = sc.next();
        modelNameTo = sc.next();
        try {
          modelList.put(modelNameTo, GreenGreyScale.execute(modelList.get(modelName)));
          transmitMessage("Green grayscale applied to " + modelName + "; written to state "
                  + modelNameTo);
        } catch (Exception e) {
          transmitMessage("Illegal arguments passed. Format is:"
                  + System.lineSeparator()
                  + "green-component [image] [destination]");
        }
        return;
      case "blue-component":
        modelName = sc.next();
        modelNameTo = sc.next();
        try {
          modelList.put(modelNameTo, BlueGreyScale.execute(modelList.get(modelName)));
          transmitMessage("Blue grayscale applied to " + modelName + "; written to state "
                  + modelNameTo);
        } catch (Exception e) {
          transmitMessage("Illegal arguments passed. Format is:"
                  + System.lineSeparator()
                  + "blue-component [image] [destination]");
        }
        return;
      case "luma-component":
        modelName = sc.next();
        modelNameTo = sc.next();
        try {
          modelList.put(modelNameTo, LumaGreyScale.execute(modelList.get(modelName)));
          transmitMessage("Luma grayscale applied to " + modelName + "; written to state "
                  + modelNameTo);
        } catch (Exception e) {
          transmitMessage("Illegal arguments passed. Format is:"
                  + System.lineSeparator()
                  + "luma-component [image] [destination]");
        }
        return;
      case "value-component":
        modelName = sc.next();
        modelNameTo = sc.next();
        try {
          modelList.put(modelNameTo, ValueGreyScale.execute(modelList.get(modelName)));
          transmitMessage("Value grayscale applied to " + modelName + "; written to state "
                  + modelNameTo);
        } catch (Exception e) {
          transmitMessage("Illegal arguments passed. Format is:"
                  + System.lineSeparator()
                  + "value-component [image] [destination]");
        }
        return;
      case "intensity-component":
        modelName = sc.next();
        modelNameTo = sc.next();
        try {
          modelList.put(modelNameTo, IntensityGreyScale.execute(modelList.get(modelName)));
          transmitMessage("Intensity grayscale applied to " + modelName + "; written to state "
                  + modelNameTo);
        } catch (Exception e) {
          transmitMessage("Illegal arguments passed. Format is:"
                  + System.lineSeparator()
                  + "intensity-component [image] [destination]");
        }
        return;
      case "save":
        filename = sc.next();
        modelName = sc.next();
        try {
          save(filename, modelList.get(modelName));
          transmitMessage("State " + modelName + " saved to "
                  + filename);
        } catch (Exception e) {
          transmitMessage("Illegal arguments passed. Format is:"
                  + System.lineSeparator()
                  + "save [filename] [image]");
        }
        return;
      default:
        transmitMessage("Illegal command!");
    }
  }

  private void transmitMessage(String message) throws IllegalStateException {
    try {
      this.view.renderMessage(message);
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

}
