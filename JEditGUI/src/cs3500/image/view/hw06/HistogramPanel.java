package cs3500.image.view.hw06;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import cs3500.image.RGBColor;
import cs3500.image.model.hw05.ImageModelAlpha;

/**
 * A panel class to represent a Histogram.
 */
public class HistogramPanel extends JPanel {

  private ImageModelAlpha model;

  private Double scalar;

  /**
   * Constructor for a new HistogramPanel.
   */
  public HistogramPanel() {
    super();
  }

  /**
   * Sets the model to base the histogram off of.
   *
   * @param model the model to create a histogram for
   */
  public void setModel(ImageModelAlpha model) {
    this.model = model;
  }

  /**
   * Sets the scalar to scale the histogram's height.
   *
   * @param scalar the amount to scale the histogram by
   */
  public void setHeight(Double scalar) {
    this.scalar = scalar;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D histogram = (Graphics2D) g;
    histogram.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);

    if (model != null) {
      int[] red = redHist(model);
      int[] green = greenHist(model);
      int[] blue = blueHist(model);
      int[] intensity = intensityHist(model);

      int bottom = 300;
      histogram.setColor(new Color(255, 0, 0, 100));
      for (int i = 0; i < 255; i++) {
        histogram.drawLine(i, bottom, i, bottom - (int) ((red[i] * scalar)));
      }
      histogram.setColor(new Color(0, 255, 0, 100));
      for (int i = 0; i < 255; i++) {
        histogram.drawLine(i, bottom, i, bottom - (int) ((green[i] * scalar)));
      }
      histogram.setColor(new Color(0, 0, 255, 100));
      for (int i = 0; i < 255; i++) {
        histogram.drawLine(i, bottom, i, bottom - (int) ((blue[i] * scalar)));
      }
      histogram.setColor(new Color(133, 133, 133, 100));
      for (int i = 0; i < 255; i++) {
        histogram.drawLine(i, bottom, i, bottom - (int) ((intensity[i] * scalar)));
      }
    }
  }

  /**
   * Creates a histogram based on a model's red values.
   *
   * @param model the model to read from
   * @return a list of values corresponding to the number of values in this model
   */
  private int[] redHist(ImageModelAlpha model) {
    int[] list = new int[256];
    for (int row = 0; row < model.getHeight(); row++) {
      for (int col = 0; col < model.getWidth(); col++) {
        RGBColor current = model.getColorAt(row, col);
        int red = current.getRed();
        list[red]++;
      }
    }
    return list;

  }

  /**
   * Creates a histogram based on a model's green values.
   *
   * @param model the model to read from
   * @return a list of values corresponding to the number of values in this model
   */
  private int[] greenHist(ImageModelAlpha model) {
    int[] list = new int[256];
    for (int row = 0; row < model.getHeight(); row++) {
      for (int col = 0; col < model.getWidth(); col++) {
        RGBColor current = model.getColorAt(row, col);
        int green = current.getGreen();
        list[green]++;
      }
    }
    return list;
  }

  /**
   * Creates a histogram based on a model's blue values.
   *
   * @param model the model to read from
   * @return a list of values corresponding to the number of values in this model
   */
  private int[] blueHist(ImageModelAlpha model) {
    int[] list = new int[256];
    for (int row = 0; row < model.getHeight(); row++) {
      for (int col = 0; col < model.getWidth(); col++) {
        RGBColor current = model.getColorAt(row, col);
        int blue = current.getBlue();
        list[blue]++;
      }
    }
    return list;
  }

  /**
   * Creates a histogram based on a model's intensity values.
   *
   * @param model the model to read from
   * @return a list of values corresponding to the number of values in this model
   */
  private int[] intensityHist(ImageModelAlpha model) {
    int[] list = new int[256];
    for (int row = 0; row < model.getHeight(); row++) {
      for (int col = 0; col < model.getWidth(); col++) {
        RGBColor current = model.getColorAt(row, col);
        int avg = (current.getRed() + current.getGreen() + current.getBlue()) / 3;
        list[avg]++;
      }
    }
    return list;
  }

}
