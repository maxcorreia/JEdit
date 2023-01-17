# JEdit
## By Jason Maria and Max Correia

An image manipulator with rudimentary functions, similar to `imagemagick`. The program can currently load files of the .ppm file format, which can then be modified in various ways.
All files must be saved if a user wishes to keep their changes.

The **ImageModelImpl** class implements the **ImageModel** interface, and stores the values of an image file in a Java object.
It stores the width, height, maximum color value (all as ints), and a two-dimensional ArrayList of **RGBColor**s to represent an image. These values represent three ints to store the red, green, and blue values of a pixel.
Getter methods are given to access image width, height, and maximum color value.

The **ImageViewImpl** class implements the **ImageView** interface, and allows the controller to parse messages, as there is no GUI representation involved with this program.

The **ImageControllerImpl** class implements the **ImageController** interface. It allows the user to load, save, and modify .ppm files through various **Command** classes. These classes represent different ways of modifying PPM files and ImageModels. The controller also manages image states that can be created and changed through all Commands other than `save`.

To quit the program while it is running, type in "quit" or "q" (standalone, case-insensitive) into the interpreter.

___

The current controller implementation can parse .ppm files and modify them in the following ways:

`load [filename] [image]` loads a given .ppm file from the current directory into a given image state (not saved!)

`save [filename] [image]` saves a given image to a .ppm file to the current directory; files can be overwritten

`brighten [value] [image] [destination]` brightens an image by a given amount, then loads it to a destination state

`horizontal-flip [image] [destination]` flips an image horizontally, then loads it to a destination state

`vertical-flip [image] [destination]` flips an image vertically, then loads it to a destination state

`red-component [image] [destination]` grayscales an image based on its red component, then loads it to a destination state

`green-component [image] [destination]` grayscales an image based on each pixel's green component, then loads it to a destination state

`blue-component [image] [destination]` grayscales an image based on each pixel's blue component, then loads it to a destination state

`luma-component [image] [destination]` grayscales an image based on each pixel's luma value, then loads it to a destination state

`value-component [image] [destination]` grayscales an image based on each pixel's largest color value, then loads it to a destination state

`intensity-component [image] [destination]` grayscales an image based on each pixel's average color value, then loads it to a destination state

An example set of inputs would be:

`load res/dolphin.ppm dolphin`

`brighten -50 dolphin dark-dolphin`

`save res/dark-dolphin.ppm dark-dolphin`

This loads the file `res/dolphin.ppm` to a state named `dolphin`. That state is then darkened by a value of 50 and written to the state `dark-dolphin`. Finally, `dark-dolphin` is written and saved to a PPM file `res/dark-dolphin.ppm`.

These lines should be typed into the main interpreter when the program runs.

___

## Credits

The files in `res/` were all created by Max Correia, and are available under the public domain.
