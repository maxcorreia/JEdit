package cs3500.image.view.hw06;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.event.ListSelectionListener;

import cs3500.image.model.hw05.ImageGallery;
import cs3500.image.model.hw05.ImageModelAlpha;

/**
 * This interface represents view methods for a GUI implementation.
 */
public interface GUIView {

  /**
   * Renders an image model to a BufferedImage.
   * @param model the model to draw
   * @return a BufferedImage to represent the image model
   */
  BufferedImage renderImage(ImageModelAlpha model);

  /**
   * Renders an error message.
   * @param message the message to write
   */
  void errorMessage(String message);

  /**
   * Renders a success message for a command.
   * @param currentModel the model before
   * @param currentModelTo the model after
   * @param commandMessage the command applied
   */
  void successMessage(String currentModel,
                             String currentModelTo,
                             String commandMessage);

  /**
   * Renders a save message.
   * @param name the save location
   */
  void saveMessage(String name);

  /**
   * Displays the view.
   */
  void display();

  /**
   * Sets the listeners for the current view.
   * @param action the action listener
   * @param list the list listener
   */
  void setListeners(ActionListener action, ListSelectionListener list);

  /**
   * Returns a message stating the current working image name.
   * @param name the name of the current image
   */
  void currentImageMessage(String name);

  /**
   * Uses a window to save a file.
   * @return the File to be passed to a Controller.
   */
  File fileSaver();

  /**
   * Uses a window to load a file.
   * @return the File to be passed to a Controller.
   */
  File fileChooser();

  /**
   * Updates the current view with a new image model.
   * @param model the model to use
   * @param name the name of the new model
   */
  void updateModel(ImageModelAlpha model, String name);

  /**
   * Creates a list of options to pass to a controller.
   * @param options the list of options
   * @return the value of the selected option
   */
  int optionsMessage(String[] options);

  /**
   * Refreshes the current view with a new icon.
   * @param gallery the gallery to select the chosen image from
   * @return the name of the current image model
   */
  String setNewIcon(ImageGallery gallery);

  /**
   * Passes a scalar value to a histogram.
   * @param scalar the scalar value
   */
  void setScale(Double scalar);

  /**
   * Refreshes the GUI display.
   */
  void refresh();

}
