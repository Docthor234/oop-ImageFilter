package ie.atu.sw;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class Runner {

	public static void main(String[] args) {
		// Scanner is used to read from console
		Scanner scanner = new Scanner(System.in);
		boolean running = true;
		int choice = -1;
		// while loop to keep the menu running until the user chooses to exit
		while (running) {
			// Display menu
			System.out.println("========Main Menu=========");
			System.out.println("****Image Filtering System ****");
			System.out.println("1.Enter Image Directory");
			System.out.println("2. Select Single Image");
			System.out.println("3. Add Customer Filter");
			System.out.println("4 Quit/Close");

			try {
				choice = scanner.nextInt();
				scanner.nextLine(); // consume new line

				switch (choice) {
				case 1: {
					System.out.println("1.Enter Image Directory");
					break;
				}
				case 2: {
					System.out.println("2. Select Single Image");
					// print. opposed to println, allows to type on the same line
					System.out.print("Enter the path to the iamge file: ");
					// Read the whole line they type as the file path
					String path = scanner.nextLine();
					// Shoves the workload off to the reader method
					readImage(path);
					break;
				}
				case 3: {
					System.out.println("3. Add Customer Filter");
					break;
				}
				case 4: {
					System.out.println("4 Quit/Close");
					System.out.println("Closing App");
					running = false;
				}
				default:
					throw new IllegalArgumentException("Unexpected value: " + choice);
				}

			} catch (Exception e) {
				System.out.println("Invalid input! Please enter a number.");
				scanner.nextLine(); // clears invalid input
			}
		}
	}

	/**
	 * Reads a single image from the given file path and prints it dimensions
	 */
	private static void readImage(String path) {
		try {
			// Wrap the path string in a File object so we can query it
			File file = new File(path);
			// Making sure there is a file to read
			// Making sure the file actually exist before it is read
			if (!file.exists()) {
				System.out.println("File is not found " + path);
				return; // bail out if nothing to read
			}
			// decode the file into Memory an in memory Buffered image (Loading from non
			// volatile to volatile memory - disk to RAM< JVM heap)
			BufferedImage image = ImageIO.read(file);

			// Making ImageIO returns null (not an exception) if the file exists but is not
			// valid or recognised image format.
			if (image == null) {
				System.out.println("The file is unreadable, try correct file type!");
			}

			System.out.println("Loaded: " + image.getWidth() + " X " + image.getHeight() + "Pixels"); double[][] identity = {
		            {0, 0, 0},
		            {0, 1, 0},
		            {0, 0, 0}
		        };
		        ImageFilter filter = new ConvolutionFilter(identity);
		        BufferedImage result = filter.apply(image);
		        ImageIO.write(result, "png", new File("output.png"));
		        System.out.println("Saved output.png"); 
		        /**
		         * //Since we have not folder attached this will stay in out program. 
		         * Refresh folder bin, image untouched.
		         * end temporary test
		         */

		    } catch (Exception e) {
		        System.out.println("Error reading image: " + e.getMessage());
		    }
		}
}
