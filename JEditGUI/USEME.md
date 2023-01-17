This controller supports images of the `.ppm`, `.png`, `.bmp`, and `.jpg` formats.


The GUI controller supports the following commands. All commands other than saving and scaling the histogram present a window before being run indicating the image state that is currently being manipulated:

`Set histogram scale` allows the user to set the scale of the image histogram. This is useful when larger images generate histograms that have larger disparities in maximum and average color amounts.

`Open a file` opens a window to load a file from, in which the file is then named to an image state.

`Save the current state to file` opens a window to save a file from the current image model.

`Edit the current image state` shows a window of commands to edit the image state, including `Blur`, `Sharpen`, `Sepia`, `Greyscale`, `Horizontal flip`, `Vertical flip`, and `Brighten`. `Brighten` requires some additional input to obtain the value of which to brighten the image by.

`Apply components to the current image state` writes a new state with the components of the current working image model, including `Red`, `Green`, `Blue`, `Luma`, `Intensity`, and `Value` component options. These were separated from the previous commands due to how many component commands there are, but they function in a similar manner to other commands.

Closing the window using your operating system's window manager close button terminates the program.

Run the program in GUi mode by running the following in a command line in the res/ directory:

`java -jar ImageManipulatorGUI.jar`

___

The text controller implementation can parse image files and modify them in the following ways:

`load [filename] [image]` loads a valid file from the current directory into a given image state (not saved!)

`save [filename] [image]` saves a given image to a valid file in the current directory; files can be overwritten

`brighten [value] [image] [destination]` brightens an image by a given amount, then loads it to a destination state

`horizontal-flip [image] [destination]` flips an image horizontally, then loads it to a destination state

`vertical-flip [image] [destination]` flips an image vertically, then loads it to a destination state

`red-component [image] [destination]` grayscales an image based on its red component, then loads it to a destination state

`green-component [image] [destination]` grayscales an image based on each pixel's green component, then loads it to a destination state

`blue-component [image] [destination]` grayscales an image based on each pixel's blue component, then loads it to a destination state

`luma-component [image] [destination]` grayscales an image based on each pixel's luma value, then loads it to a destination state

`value-component [image] [destination]` grayscales an image based on each pixel's largest color value, then loads it to a destination state

`intensity-component [image] [destination]` grayscales an image based on each pixel's average color value, then loads it to a destination state

`greyscale [image] [destination]` grayscales an image based on luma values using matrix multiplication, then loads it to a destination state

`sepia [image] [destination]` applies sepia to an image using matrix multiplication, then loads it to a destination state

`sharpen [image] [destination]` sharpens an image based on matrix math, then loads it to a destination state

`blur [image] [destination]` blurs an image based on matrix math, then loads it to a destination state


An example set of inputs would be:

`load res/dolphin.ppm dolphin`

`brighten -50 dolphin dark-dolphin`

`save res/dark-dolphin.ppm dark-dolphin q`

This loads the file `res/dolphin.ppm` to a state named `dolphin`. That state is then darkened by a value of 50 and written to the state `dark-dolphin`. Finally, `dark-dolphin` is written and saved to a PPM file `res/dark-dolphin.ppm`. The program is then quit.

The program runs by executing the following in a command line in the `res/` directory:

`java -jar ImageManipulatorGUI.jar -text`

From there, the aforementioned list of inputs can be typed in to the interpreter to execute those commands.

Alternatively, `res/script.txt` is a file that can be passed to the program as a command line argument, and can be run in `res/` to generate most of the files in `/res` using `dolphin.ppm` and `dots.png` alone.

This should be passed through a command line in the `res/` directory with the following text:

`java -jar ImageManipulatorGUI.jar -file script.txt`
