package ie.atu.sw;

import java.awt.image.BufferedImage;
import java.util.Iterator;

/**
 * An {@link ImageFilter} that applies a convolution kernel to an image. Uses a
 * grid (Matrix) of Numerical weight, called a kernel, to process every pixel in
 * an image. The colour of the near by pixels combined according to these
 * weights create a new colour value. The red, green, and blue components are
 * processed separately before being combined into the final pixel. In my minds
 * eye, thinking of a rubrics cube face being slide over numerical data. The
 * middle square or center square is the one being changed Int matrics [3][3]
 */

public class ConvolutionFilter implements ImageFilter {

	private final double [][] kernel; // the matrix of weight, mention above 3*3
	/**
	 * @param kernel a 2d array of weight (as mentioned, in general too, 3*3) 
	 * has to be a double, int can't store a fraction, we need if for the box blur
	 */
	public ConvolutionFilter(double[][] kernel) {
		this.kernel = kernel;
	}
	
	//using override to tell the java complier that we intend to ue the below method over the one in the superclass or the interface
	@Override
	public BufferedImage apply(BufferedImage image) {
		int width = image.getWidth();
		int height = image.getHeight();
		/**
		 * Write the results into a New Image. We can not modify the source while we read it, or later pixels would be computed from an altered
		 * or changed file. 
		 */
		BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		int kRows = kernel.length; // rows in the kernel
		int kCols = kernel[0].length; //columns in the kernel
		int centreRow = kRows / 2; //index of the kernels centre row, remember matrix start at 0
		int centreCol = kCols / 2; //index of the kernel's centre column.
		
		//Visit every pixel in the image, start at top left.
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				double red = 0, green = 0, blue = 0;
				//Sliding the matrix or cubes face over the pixel and it's 8 neighbors.
				for (int kr = 0; kr < kRows; kr++) {
					for (int kc = 0; kc < kCols; kc++) {
						/**
						 * MAp kernels cell. kc & kr, into a neighbouring pixel. The kernel's centre lines up with the current pixel, so offset by 
						 * (kc - centreCol, kr - centreRow).
						 */
						int px = x + kc - centreCol;
						int py = y + kr - centreRow;
						
						/**
						 * These line are to make sure that the image coordinates are not out of bounds.
						 * This is clamping coordinates, reuse the nearest pixel instead of crshing out
						 * Math.max(px, 0) stops it going negative and Math.min (width-1)
						 */
						px = Math.min(Math.max(px, 0), width - 1);
						py = Math.min(Math.max(py, 0), width - 1);
						
						int pixel = image.getRGB(px, py);
						//pull the 3 channels together out of 32 bit int. NB* ARGB and r = 8, g = 8, b = 8. 10101010 10101010 10101010
						int r = (pixel >> 16) & 0xff; //Setting red channel
						int g = (pixel >> 8) & 0xff; //Setting green channel
						int b = pixel & 0xff; //Setting blue channel
						
						double weight = kernel[kr][kc];
						
						//Mulitpy each channel by the weight and accumulate 
						red += r * weight;
						green += g * weight;
						blue += b * weight;
					}
				}
				//Sums can land outside [0, 255], so clamp the channel back in
				int newR = clamp((int) Math.round(red));
				int newG = clamp((int) Math.round(green));
				int newB = clamp((int) Math.round(blue));
				
				//Rebuild one 32-bit RGB value and store it 
				int newPixel = (newR <<16) | (newG<<8) | newB;
				output.setRGB(x, y, newPixel);
			}
		}
		return output;
	}

	/**
	 * restrict the colour channel value to the valid range [0,255]
	 * @param value is the raw computed chanel value
	 * @return is the value limited to between 0 & 255
	 * 	 */
	private int clamp (int value) {
		return Math.min(Math.max(value, 0), 255);
	}
}
