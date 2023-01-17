package cs3500.image.model.hw05;

import cs3500.image.model.hw04.ImageModel;

/**
 * An extension of ImageModel that supports alpha values.
 * Some pictures have transparencies in their pixels, and this interface is used
 * to support such functionality.
 */
public interface ImageModelAlpha extends ImageModel {
  int getAlphaAt(int row, int col);
}
