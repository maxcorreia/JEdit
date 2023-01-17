package cs3500.image.view.hw06;

import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ListSelectionModel;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.ImageIcon;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import cs3500.image.model.hw05.ImageGallery;
import cs3500.image.model.hw05.ImageModelAlpha;

import static java.awt.image.BufferedImage.TYPE_INT_ARGB;

/**
 * A view class to render images in Swing to be passed to a controller.
 */
public class ImageViewGUI extends JFrame implements GUIView {

  private final JList<String> listOfStrings;
  private final DefaultListModel<String> dataForListOfStrings;

  private final JButton scaleButton;
  private final JButton fileOpenButton;
  private final JButton fileSaveButton;
  private final JButton optionButton;
  private final JButton componentButton;

  private final JLabel imageLabel;
  private final HistogramPanel histPanel;

  /**
   * A constructor for the GUI view.
   *
   * @param caption the caption to display on the view window
   */
  public ImageViewGUI(String caption) {
    super(caption);
    setSize(600, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
    JScrollPane mainScrollPane = new JScrollPane(mainPanel);
    add(mainScrollPane);

    //Scrollable image display panel
    JPanel imagePanel = new JPanel();
    imagePanel.setBorder(BorderFactory.createTitledBorder("Showing an image"));
    imagePanel.setLayout(new GridLayout(1, 0, 10, 10));
    mainPanel.add(imagePanel);

    imageLabel = new JLabel();
    JScrollPane imageScrollPane = new JScrollPane(imageLabel);
    imageScrollPane.setPreferredSize(new Dimension(300, 300));
    imagePanel.add(imageScrollPane);

    //Scrollable histogram display panel
    JPanel histViewPanel = new JPanel();
    histViewPanel.setBorder(BorderFactory.createTitledBorder("Image histogram"));
    histViewPanel.setLayout(new GridLayout(1, 0, 10, 10));
    mainPanel.add(histViewPanel);

    this.histPanel = new HistogramPanel();
    JScrollPane histScrollPane = new JScrollPane(this.histPanel);
    histScrollPane.setPreferredSize(new Dimension(256, 300));
    histViewPanel.add(histScrollPane);

    //Selection lists for image states
    JPanel selectionListPanel = new JPanel();
    selectionListPanel.setBorder(BorderFactory.createTitledBorder("Image states"));
    selectionListPanel.setLayout(new BoxLayout(selectionListPanel, BoxLayout.X_AXIS));
    mainPanel.add(selectionListPanel);

    dataForListOfStrings = new DefaultListModel<>();
    listOfStrings = new JList<>(dataForListOfStrings);
    listOfStrings.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    selectionListPanel.add(listOfStrings);

    //dialog boxes for commands
    JPanel dialogBoxesPanel = new JPanel();
    dialogBoxesPanel.setBorder(BorderFactory.createTitledBorder("Options"));
    dialogBoxesPanel.setLayout(new BoxLayout(dialogBoxesPanel, BoxLayout.PAGE_AXIS));
    mainPanel.add(dialogBoxesPanel);

    //scale the histogram
    JPanel scalePanel = new JPanel();
    scalePanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(scalePanel);
    scaleButton = new JButton("Set histogram scale");
    scaleButton.setActionCommand("Scale");
    scalePanel.add(scaleButton);

    //file open
    JPanel fileopenPanel = new JPanel();
    fileopenPanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(fileopenPanel);
    fileOpenButton = new JButton("Open a file");
    fileOpenButton.setActionCommand("Open file");
    fileopenPanel.add(fileOpenButton);

    //file save
    JPanel filesavePanel = new JPanel();
    filesavePanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(filesavePanel);
    fileSaveButton = new JButton("Save the current state to file");
    fileSaveButton.setActionCommand("Save file");
    filesavePanel.add(fileSaveButton);

    //Options dialog for most commands
    JPanel optionsDialogPanel = new JPanel();
    optionsDialogPanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(optionsDialogPanel);

    optionButton = new JButton("Edit the current image state");
    optionButton.setActionCommand("Option");
    optionsDialogPanel.add(optionButton);

    //Component options dialog
    JPanel componentDialogPanel = new JPanel();
    componentDialogPanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(componentDialogPanel);

    componentButton = new JButton("Apply components to the current image state");
    componentButton.setActionCommand("Component");
    componentDialogPanel.add(componentButton);
  }

  @Override
  public void display() {
    setVisible(true);
  }

  @Override
  public void setListeners(ActionListener action, ListSelectionListener list) {
    listOfStrings.addListSelectionListener(list);
    scaleButton.addActionListener(action);
    fileOpenButton.addActionListener(action);
    fileSaveButton.addActionListener(action);
    optionButton.addActionListener(action);
    componentButton.addActionListener(action);
  }

  /**
   * Renders a non-transparent image.
   *
   * @param model the model to save to an image
   * @throws IOException if there is a write error
   */
  public BufferedImage renderImage(ImageModelAlpha model) {
    BufferedImage bi = new BufferedImage(model.getWidth(),
            model.getHeight(), TYPE_INT_ARGB);
    for (int row = 0; row < model.getHeight(); row++) {
      for (int col = 0; col < model.getWidth(); col++) {
        int color = model.getColorAt(row, col).getBlue()
                + 256 * model.getColorAt(row, col).getGreen()
                + 65536 * model.getColorAt(row, col).getRed()
                + 16777216 * model.getAlphaAt(row, col);
        bi.setRGB(col, row, color);
      }
    }
    return bi;
  }


  /**
   * Renders an error message.
   *
   * @param message the message to write
   */
  public void errorMessage(String message) {
    JOptionPane.showMessageDialog(this,
            message, "Error",
            JOptionPane.ERROR_MESSAGE);
  }

  /**
   * Renders a message for a command success.
   *
   * @param currentModel   the model before
   * @param currentModelTo the model after
   * @param commandMessage the command applied
   */
  public void successMessage(String currentModel,
                             String currentModelTo,
                             String commandMessage) {
    JOptionPane.showMessageDialog(this, commandMessage + currentModel
                    + "; written to state " + currentModelTo,
            "Applied command", JOptionPane.PLAIN_MESSAGE);
  }

  /**
   * Renders a message to indicate a saved file.
   *
   * @param name the save location
   */
  public void saveMessage(String name) {
    JOptionPane.showMessageDialog(this, "Saved current image to " + name,
            "Saved file", JOptionPane.PLAIN_MESSAGE);
  }

  /**
   * Renders a new file chooser for a controller.
   *
   * @return the File chosen in the chooser
   */
  public File fileChooser() {
    final JFileChooser fchooser = new JFileChooser(".");
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "BMP, JPG, PNG & PPM Images", "bmp", "jpg", "png", "ppm");
    fchooser.setFileFilter(filter);
    int retvalue = fchooser.showOpenDialog(this);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      return fchooser.getSelectedFile();
    } else {
      throw new IllegalStateException();
    }
  }

  /**
   * Saves an image from the controller to a file.
   *
   * @return the File chosen by the chooser
   */
  public File fileSaver() {
    final JFileChooser fchooser = new JFileChooser(".");
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "BMP, JPG, PNG & PPM Images", "bmp", "jpg", "png", "ppm");
    fchooser.setFileFilter(filter);
    int retvalue = fchooser.showSaveDialog(this);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      return fchooser.getSelectedFile();
    } else {
      throw new IllegalStateException();
    }
  }

  /**
   * Returns a message indicating which model is being edited.
   *
   * @param name the name of the current image model
   */
  public void currentImageMessage(String name) {
    JOptionPane.showMessageDialog(this,
            "The current image is now " + name,
            "Command list", JOptionPane.PLAIN_MESSAGE);
  }

  /**
   * Updates the model, used by the controller after each command.
   *
   * @param model the current working model
   * @param name  the name of the current working model
   */
  public void updateModel(ImageModelAlpha model, String name) {
    imageLabel.setIcon(new ImageIcon(renderImage(model)));
    dataForListOfStrings.addElement(name);
    histPanel.setModel(model);
    histPanel.setHeight(1.0);
    this.repaint();
  }

  /**
   * Renders a message with multiple options.
   *
   * @param options the list of options
   * @return the int corresponding to the chosen option
   */
  public int optionsMessage(String[] options) {
    return JOptionPane.showOptionDialog(this,
            "Please choose the command you would like to execute", "Options",
            JOptionPane.YES_OPTION, JOptionPane.INFORMATION_MESSAGE,
            null, options, options[4]);
  }

  /**
   * Sets a new icon in the image panel.
   *
   * @param gallery the gallery to take an image from
   * @return the name of the current model
   */
  public String setNewIcon(ImageGallery gallery) {
    String name = this.listOfStrings.getSelectedValue();
    imageLabel.setIcon(new
            ImageIcon(renderImage(gallery.get(name))));
    histPanel.setModel(gallery.get(name));
    histPanel.setHeight(1.0);
    this.repaint();
    return name;
  }

  /**
   * Sets the scalar value for this view's histogram.
   * @param scalar the scalar value
   */
  public void setScale(Double scalar) {
    histPanel.setHeight(scalar);
  }

  /**
   * Refreshes the current view.
   */
  public void refresh() {
    this.repaint();
  }
}
