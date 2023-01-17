package cs3500.image.model.hw05;

import java.util.HashMap;

/**
 * This class represents multiple image models, and groups them together using a HashMap.
 * It is a wrapper class for a HashMap of Strings and image models.
 */
public class ImageGallery {
  HashMap<String, ImageModelAlpha> gallery;

  /**
   * A constructor for an ImageGallery.
   */
  public ImageGallery() {
    this.gallery = new HashMap<>();
  }

  /**
   * Puts a new String ImageModelAlpha pair into the gallery.
   * @param name the name to add
   * @param model the image model to add
   */
  public void put(String name, ImageModelAlpha model) {
    this.gallery.put(name, model);
  }

  /**
   * Retrieves the corresponding model to the inputted String.
   * @param name the name to retrieve from
   * @return the image model associated with the name
   */
  public ImageModelAlpha get(String name) {
    return this.gallery.get(name);
  }
}
