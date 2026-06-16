package ie.atu.sw;

import java.awt.image.BufferedImage;

/**
 * Menu and processor will talk to this. Never a concrete filter class, meaning
 * you can add new filter wthout touching anything else. Defines the
 * transformation that can be applied to an image. Core abstraction of the image
 * system. ANy class can that takes an image and applies an effect imlements
 * this interface. When code is called that depends on this interface, never a
 * conrete filter, meaning it is loosely coupled.
 */

public interface ImageFilter {
	/**
	 * Applies this filter to the given image and returns the results
	 * 
	 * @param image the source image to transform, never modified.
	 * @return a new image with the filtered result applied.
	 * 
	 */
	BufferedImage apply(BufferedImage image);
}
