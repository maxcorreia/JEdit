package cs3500.image.controller.hw06;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

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
import cs3500.image.view.hw06.GUIView;

import static cs3500.image.command.hw05.LoadNew.genAlpha;

/**
 * A controller for an image manipulator with a GUI.
 */
public class ImageControllerGUI implements ImageController, ActionListener, ListSelectionListener {
  private String currentModel;
  private final ImageGallery gallery;
  private final GUIView view;

  /**
   * A constructor for an ImageControllerGUI.
   *
   * @param gallery the gallery of images
   * @param view    the view to render the images/commands to
   */
  public ImageControllerGUI(ImageGallery gallery, GUIView view) {
    this.gallery = gallery;
    this.view = view;
    this.currentModel = "";
    view.setListeners(this, this);
  }

  /**
   * Runs the current program by displaying the view.
   */
  public void runProgram() {
    view.display();
  }


  @Override
  public void actionPerformed(ActionEvent command) {
    switch (command.getActionCommand()) {
      case "Scale": {
        Double value = Double.valueOf(JOptionPane.
                showInputDialog("Input the value (double/decimal included) to scale by"));
        view.setScale(value);
        view.refresh();
        return;
      }
      case "Open file": {
        view.currentImageMessage(currentModel);
        File f = view.fileChooser();

        // From here the code works with our previously implemented load commands

        String filename = f.getName();
        String filetype;
        try {
          filetype = filename.substring(filename.length() - 4);
        } catch (StringIndexOutOfBoundsException e) {
          view.errorMessage(
                  "The filename is too small!");
          return;
        }
        String currentModelTo = JOptionPane.showInputDialog("Name the state you wish to load to");
        if (".ppm".equals(filetype)) {
          try {
            ImageModel file = Load.load(f.getAbsolutePath());
            gallery.put(currentModelTo,
                    new ImageModelAlphaImpl(new ImageModelImpl(file.getWidth(),
                            file.getHeight(),
                            file.getMaxValue(),
                            LoadNew.genPPMImage(file)),
                            LoadNew.genOpaqueAlpha(file)));
            view.successMessage(filename, currentModelTo,
                    "Loaded ");
          } catch (Exception e) {
            view.errorMessage("The load failed!");
          }
          view.updateModel(gallery.get(currentModelTo), currentModelTo);
          return;
        }
        if (!(filetype.equals(".png") || filetype.equals(".bmp") || filetype.equals(".jpg"))) {
          view.errorMessage(
                  "Illegal file format!");
          return;
        }
        try {
          gallery.put(currentModelTo, LoadNew.load(f.getAbsolutePath()));
        } catch (IOException e) {
          view.errorMessage(
                  e.getMessage());
        }
        view.successMessage(filename, currentModelTo,
                "Loaded ");
        view.updateModel(gallery.get(currentModelTo), currentModelTo);
        currentModel = currentModelTo;
        return;
      }
      case "Save file": {
        view.currentImageMessage(currentModel);

        File f = view.fileSaver();

        // From here the code works with our previously implemented save commands

        String filename = f.getName();
        String filetype;
        try {
          filetype = filename.substring(filename.length() - 4);
        } catch (StringIndexOutOfBoundsException e) {
          view.errorMessage("The filename is too small!");
          return;
        }
        switch (filetype) {
          case ".ppm":
            try {
              ImageModelAlpha model = gallery.get(currentModel);
              ImageModel convert = new ImageModelImpl(model.getWidth(),
                      model.getHeight(),
                      model.getMaxValue(),
                      LoadNew.genPPMImage(model));
              Save.save(filename, convert);
              view.saveMessage(filename);
            } catch (Exception e) {
              view.errorMessage(
                      "An unexpected error has occurred!");
            }
            return;
          case ".png":
            try {
              SaveNew.saveAlpha(filename, "png", gallery.get(currentModel));
              view.saveMessage(filename);
            } catch (Exception e) {
              view.errorMessage(
                      "An unexpected error has occurred!");
            }
            return;
          case ".bmp":
            try {
              SaveNew.save(filename, "bmp", gallery.get(currentModel));
              view.saveMessage(filename);
            } catch (Exception e) {
              view.errorMessage(
                      "An unexpected error has occurred!");
            }
            return;
          case ".jpg":
            try {
              SaveNew.save(filename, "jpg", gallery.get(currentModel));
              view.saveMessage(filename);
            } catch (Exception e) {
              view.errorMessage(
                      "An unexpected error has occurred!");
            }
            return;
          default:
            view.errorMessage(
                    "Incorrect file format!");
            return;
        }
      }
      case "Option": {
        view.currentImageMessage(currentModel);
        String[] options = {"Blur", "Sharpen", "Sepia", "Greyscale",
                            "Horizontal flip", "Vertical flip", "Brighten"};
        int retvalue = view.optionsMessage(options);
        try {
          processCommand(options[retvalue]);
        } catch (Exception e) {
          view.errorMessage(
                  "This image does not exist, or the action was cancelled.");
        }
        return;
      }
      case "Component": {
        view.currentImageMessage(currentModel);
        String[] options = {"Red", "Green", "Blue", "Luma", "Intensity", "Value"};
        int retvalue = view.optionsMessage(options);
        try {
          processCommand(options[retvalue]);
        } catch (Exception e) {
          view.errorMessage(
                  "This image does not exist, or the action was cancelled.");
        }
        return;
      }
      default:
        view.errorMessage("The program should not get to this point!");
    }
  }

  /**
   * Processes a command passed to it from this controller's listener.
   *
   * @param userInstruction the name of the command
   */
  private void processCommand(String userInstruction) {
    int value;
    String currentModelTo;
    ImageModel convert;
    ImageModelAlpha model;
    switch (userInstruction) {
      // Brightens an image, then adds a new state to the Hashmap
      case "Brighten":
        value = Integer.parseInt(JOptionPane.showInputDialog("Input the value to brighten by"));
        currentModelTo = JOptionPane.showInputDialog("Name the state you wish to write changes to");
        if (currentModelTo == null) {
          view.errorMessage("Cancelled action.");
          return;
        }
        try {
          model = gallery.get(currentModel);
          convert = new ImageModelImpl(model.getWidth(),
                  model.getHeight(),
                  model.getMaxValue(),
                  LoadNew.genPPMImage(model));
          gallery.put(currentModelTo,
                  new ImageModelAlphaImpl(Brighten.execute(convert, value), genAlpha(model)));
          view.successMessage(
                  currentModel, currentModelTo, "Brightened ");
        } catch (Exception e) {
          view.errorMessage(
                  "An unexpected error has occurred!");
        }
        view.updateModel(gallery.get(currentModelTo), currentModelTo);
        currentModel = currentModelTo;
        return;


      // Horizontally flips an image, then adds a new state to the Hashmap
      case "Horizontal flip":
        currentModelTo = JOptionPane.showInputDialog("Name the state you wish to write changes to");
        if (currentModelTo == null) {
          view.errorMessage("Cancelled action.");
          return;
        }
        try {
          model = gallery.get(currentModel);
          convert = new ImageModelImpl(model.getWidth(),
                  model.getHeight(),
                  model.getMaxValue(),
                  LoadNew.genPPMImage(model));
          gallery.put(currentModelTo,
                  new ImageModelAlphaImpl(HorizontalFlip.execute(convert), genAlpha(model)));
          view.successMessage(
                  currentModel, currentModelTo, "Horizontally flipped ");
        } catch (Exception e) {
          view.errorMessage(
                  "An unexpected error has occurred!");
        }
        view.updateModel(gallery.get(currentModelTo), currentModelTo);
        currentModel = currentModelTo;
        return;
      // Vertically flips an image, then adds a new state to the Hashmap
      case "Vertical flip":
        currentModelTo = JOptionPane.showInputDialog("Name the state you wish to write changes to");
        if (currentModelTo == null) {
          view.errorMessage("Cancelled action.");
          return;
        }
        try {
          model = gallery.get(currentModel);
          convert = new ImageModelImpl(model.getWidth(),
                  model.getHeight(),
                  model.getMaxValue(),
                  LoadNew.genPPMImage(model));
          gallery.put(currentModelTo,
                  new ImageModelAlphaImpl(VerticalFlip.execute(convert), genAlpha(model)));
          view.successMessage(
                  currentModel, currentModelTo, "Vertically flipped ");
        } catch (Exception e) {
          view.errorMessage(
                  "An unexpected error has occurred!");
        }
        view.updateModel(gallery.get(currentModelTo), currentModelTo);
        currentModel = currentModelTo;
        return;
      // Creates a gray image with the image's red values, then adds a new state to the Hashmap
      case "Red":
        currentModelTo = JOptionPane.showInputDialog("Name the state you wish to write changes to");
        if (currentModelTo == null) {
          view.errorMessage("Cancelled action.");
          return;
        }
        try {
          model = gallery.get(currentModel);
          convert = new ImageModelImpl(model.getWidth(),
                  model.getHeight(),
                  model.getMaxValue(),
                  LoadNew.genPPMImage(model));
          gallery.put(currentModelTo,
                  new ImageModelAlphaImpl(RedGreyScale.execute(convert), genAlpha(model)));
          view.successMessage(
                  currentModel, currentModelTo, "Red grayscale applied to ");
        } catch (Exception e) {
          view.errorMessage(
                  "An unexpected error has occurred!");
        }
        view.updateModel(gallery.get(currentModelTo), currentModelTo);
        currentModel = currentModelTo;
        return;
      // Creates a gray image with the image's green values, then adds a new state to the Hashmap
      case "Green":
        currentModelTo = JOptionPane.showInputDialog("Name the state you wish to write changes to");
        if (currentModelTo == null) {
          view.errorMessage("Cancelled action.");
          return;
        }
        try {
          model = gallery.get(currentModel);
          convert = new ImageModelImpl(model.getWidth(),
                  model.getHeight(),
                  model.getMaxValue(),
                  LoadNew.genPPMImage(model));
          gallery.put(currentModelTo,
                  new ImageModelAlphaImpl(GreenGreyScale.execute(convert), genAlpha(model)));
          view.successMessage(
                  currentModel, currentModelTo, "Green grayscale applied to ");
        } catch (Exception e) {
          view.errorMessage(
                  "An unexpected error has occurred!");
        }
        view.updateModel(gallery.get(currentModelTo), currentModelTo);
        currentModel = currentModelTo;
        return;
      // Creates a gray image with the image's blue values, then adds a new state to the Hashmap
      case "Blue":
        currentModelTo = JOptionPane.showInputDialog("Name the state you wish to write changes to");
        if (currentModelTo == null) {
          view.errorMessage("Cancelled action.");
          return;
        }
        try {
          model = gallery.get(currentModel);
          convert = new ImageModelImpl(model.getWidth(),
                  model.getHeight(),
                  model.getMaxValue(),
                  LoadNew.genPPMImage(model));
          gallery.put(currentModelTo,
                  new ImageModelAlphaImpl(BlueGreyScale.execute(convert), genAlpha(model)));
          view.successMessage(
                  currentModel, currentModelTo, "Blue grayscale applied to ");
        } catch (Exception e) {
          view.errorMessage(
                  "An unexpected error has occurred!");
        }
        view.updateModel(gallery.get(currentModelTo), currentModelTo);
        currentModel = currentModelTo;
        return;
      // Creates an image with the image's luma values, then adds a new state to the Hashmap
      case "Luma":
        currentModelTo = JOptionPane.showInputDialog("Name the state you wish to write changes to");
        if (currentModelTo == null) {
          view.errorMessage("Cancelled action.");
          return;
        }
        try {
          model = gallery.get(currentModel);
          convert = new ImageModelImpl(model.getWidth(),
                  model.getHeight(),
                  model.getMaxValue(),
                  LoadNew.genPPMImage(model));
          gallery.put(currentModelTo,
                  new ImageModelAlphaImpl(LumaGreyScale.execute(convert), genAlpha(model)));
          view.successMessage(
                  currentModel, currentModelTo, "Luma grayscale applied to ");
        } catch (Exception e) {
          view.errorMessage(
                  "An unexpected error has occurred!");
        }
        view.updateModel(gallery.get(currentModelTo), currentModelTo);
        currentModel = currentModelTo;
        return;
      // Creates an image with the image's largest color values, adding a new state to the Hashmap
      case "Value":
        currentModelTo = JOptionPane.showInputDialog("Name the state you wish to write changes to");
        if (currentModelTo == null) {
          view.errorMessage("Cancelled action.");
          return;
        }
        try {
          model = gallery.get(currentModel);
          convert = new ImageModelImpl(model.getWidth(),
                  model.getHeight(),
                  model.getMaxValue(),
                  LoadNew.genPPMImage(model));
          gallery.put(currentModelTo,
                  new ImageModelAlphaImpl(ValueGreyScale.execute(convert), genAlpha(model)));
          view.successMessage(
                  currentModel, currentModelTo, "Value grayscale applied to ");
        } catch (Exception e) {
          view.errorMessage(
                  "An unexpected error has occurred!");
        }
        view.updateModel(gallery.get(currentModelTo), currentModelTo);
        currentModel = currentModelTo;
        return;
      // Creates an image with the image's average color values, adding a new state to the Hashmap
      case "Intensity":
        currentModelTo = JOptionPane.showInputDialog("Name the state you wish to write changes to");
        if (currentModelTo == null) {
          view.errorMessage("Cancelled action.");
          return;
        }
        try {
          model = gallery.get(currentModel);
          convert = new ImageModelImpl(model.getWidth(),
                  model.getHeight(),
                  model.getMaxValue(),
                  LoadNew.genPPMImage(model));
          gallery.put(currentModelTo,
                  new ImageModelAlphaImpl(IntensityGreyScale.execute(convert), genAlpha(model)));
          view.successMessage(
                  currentModel, currentModelTo, "Intensity grayscale applied to ");
        } catch (Exception e) {
          view.errorMessage(
                  "An unexpected error has occurred!");
        }
        view.updateModel(gallery.get(currentModelTo), currentModelTo);
        currentModel = currentModelTo;
        return;
      // Creates a gray image with the image's luma values, then adds a new state to the Hashmap
      case "Greyscale":
        currentModelTo = JOptionPane.showInputDialog("Name the state you wish to write changes to");
        if (currentModelTo == null) {
          view.errorMessage("Cancelled action.");
          return;
        }
        try {
          gallery.put(currentModelTo, Greyscale.execute(gallery.get(currentModel)));
          view.successMessage(
                  currentModel, currentModelTo, "Applied greyscale to ");
        } catch (Exception e) {
          view.errorMessage(
                  "An unexpected error has occurred!");
        }
        view.updateModel(gallery.get(currentModelTo), currentModelTo);
        currentModel = currentModelTo;
        return;
      // Creates a sepia image with the image's color values, then adds a new state to the Hashmap
      case "Sepia":
        currentModelTo = JOptionPane.showInputDialog("Name the state you wish to write changes to");
        if (currentModelTo == null) {
          view.errorMessage("Cancelled action.");
          return;
        }
        try {
          gallery.put(currentModelTo, Sepia.execute(gallery.get(currentModel)));
          view.successMessage(
                  currentModel, currentModelTo, "Applied sepia to ");
        } catch (Exception e) {
          view.errorMessage(
                  "An unexpected error has occurred!");
        }
        view.updateModel(gallery.get(currentModelTo), currentModelTo);
        currentModel = currentModelTo;
        return;
      // Blurs an image based on its color values, then adds a new state to the Hashmap
      case "Blur":
        currentModelTo = JOptionPane.showInputDialog("Name the state you wish to write changes to");
        if (currentModelTo == null) {
          view.errorMessage("Cancelled action.");
          return;
        }
        try {
          gallery.put(currentModelTo, Blur.execute(gallery.get(currentModel)));
          view.successMessage(
                  currentModel, currentModelTo, "Applied blur to ");
        } catch (Exception e) {
          view.errorMessage(
                  "An unexpected error has occurred!");
        }
        view.updateModel(gallery.get(currentModelTo), currentModelTo);
        currentModel = currentModelTo;
        return;
      // Sharpens an image based on its color values, then adds a new state to the Hashmap
      case "Sharpen":
        currentModelTo = JOptionPane.showInputDialog("Name the state you wish to write changes to");
        if (currentModelTo == null) {
          view.errorMessage("Cancelled action.");
          return;
        }
        try {
          gallery.put(currentModelTo, Sharpen.execute(gallery.get(currentModel)));
          view.successMessage(
                  currentModel, currentModelTo, "Applied sharpen to ");
        } catch (Exception e) {
          view.errorMessage(
                  "An unexpected error has occurred!");
        }
        view.updateModel(gallery.get(currentModelTo), currentModelTo);
        currentModel = currentModelTo;
        return;
      default:
    }
  }

  @Override
  public void valueChanged(ListSelectionEvent e) {
    // This gets us the string value that's currently selected
    currentModel = view.setNewIcon(gallery);
  }
}
