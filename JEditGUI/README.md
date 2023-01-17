# JEdit++
## By Jason Maria and Max Correia

An image manipulator with rudimentary functions, similar to `imagemagick`. The program can currently load files of the `.ppm`, `.png`, `.bmp`, and `.jpg` file formats, which can then be modified in various ways.
All files must be saved if a user wishes to keep their changes.

This version adds a GUI interface to our program; it maintains existing functionality (commands, states) while presenting them in a user-friendly form.

The **ImageControllerGUI** uses the older commands by taking input from the various buttons within the **ImageViewGUI** view. The view extends a JFrame in order to create graphical functionality, with windows for the current image, an image histogram to represent the image's values (created with a **HistogramPanel**), as well as the various commands introduced in prior versions.

Lastly, the program is run through the **ImageProgramGUI** class, which has the options of running in text mode or from a file through command line arguments.

___

Further instructions for running/using the program can be found in `USEME.md`.

___


## Credits

The images in `res/` were all created by Max Correia, and are available under the public domain.
