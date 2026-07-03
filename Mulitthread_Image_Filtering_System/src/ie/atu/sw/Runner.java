package ie.atu.sw;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Iterator;
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
					//Case 2 is the orchestrator/coordinator. Calls the methods and passes data between them, 
					//methods don't call each other. Cohesion
					System.out.println("2. Select Single Image");
					// print. opposed to println, allows to type on the same line
					System.out.print("Enter the path to the iamge file: ");
					// Read the whole line they type as the file path
					String path = scanner.nextLine();
					// Shoves the workload off to the selectFilter method
					FilterSet chosen = selectFilter(scanner);//using scanner as we need a input. Once chosen is done below got to case 2.
					// Shoves the workload off to the reader method
					processImage(path, chosen);//Formally read image, now processing image
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
	 * With this we only want it to read an image. If we had it  reading image, running the filter menu & saving, that is low Cohesion.
	 * 
	 */
	private static void processImage(String path, FilterSet chosen) {
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
	//select image filter to link up with our Enum.
	public static FilterSet selectFilter(Scanner scanner) {
		FilterSet[] filters = FilterSet.values();// showing every value in the enum
		System.out.println("\n=== Please select the filter you wish to choose ===");
		for (int i = 0; i < filters.length; i++) {
			//Show a number beside each filters name
			System.out.println((i + 1) + ". " + filters[i]);
		}
		//need input to be selected
		System.out.print("Select your filter - ");
		int pick = scanner.nextInt();
		scanner.nextInt();
		scanner.nextLine(); // eats leftover line
		//since working with matrix we are converting it back into regular unary system 1 to whatever.
		//Also, need to do similar with reading in file path, need something to remove "" when copy and paste is done.
		return filters[pick - 1];
	}
}
