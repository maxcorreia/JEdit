# JEdit+
## By Jason Maria and Max Correia

An image manipulator with rudimentary functions, similar to `imagemagick`. The program can currently load files of the `.ppm`, `.png`, `.bmp`, and `.jpg` file formats, which can then be modified in various ways.
All files must be saved if a user wishes to keep their changes.

This version adds functionality for the three newly supported file types, and adds functionality to `greyscale`, `sharpen`, `blur`, and apply a `sepia` filer to image states.

While the previous implementation is untouched, a new **ImageModelAlphaImpl** model implements the **ImageModelAlpha** interface, which in turn extends the previously existing **ImageModel** interface. This allows for support for images with transparent values so that such values are saved through each file conversion. These values are stored in a two-dimension ArrayList of integer values. The other values are stored in a constructed **ImageModelImpl**, so as to maintain backwards compatibility with commands while allowing access to multi-format file conversion.

A new **ImageController2** controller implements the previously existing **ImageController** interface, allowing access to the new commands while maintaining backwards compatibility with pre-existing ones for both .ppm and the newer file types. Like **ImageControllerImpl**, it uses image states to manage the creation of new images through the various commands; byt these states are now stored in a model class **ImageGallery**, which manages the states seperate from the controller method.

The **ImageViewImpl** class is still used in the new controller for the purpose of displaying program messages.

___

Further instructions for running/using the program can be found in `USEME.md`.

___


## Credits

The images in `res/` were all created by Max Correia, and are available under the public domain.
