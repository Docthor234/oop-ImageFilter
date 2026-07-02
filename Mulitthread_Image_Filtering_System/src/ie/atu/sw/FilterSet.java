package ie.atu.sw;

public enum FilterSet {
	IDENTITY(new double[][]{{0, 0, 0}, {0, 1, 0}, {0, 0, 0}}),
	EDGE_DETECTION(new double[][]{{1, 0, -1}, {0, 0, 0}, {-1, 0, 1}}),
	SHARPEN(new double[][]{{0, -1, 0}, {-1, 5, -1}, {0, -1, 0}}),
	HORIZONTAL_LINES(new double[][]{{-1, -1, -1}, {2, 2, 2}, {-1, -1, -1}}),
	SOBEL_HORIZONTAL(new double[][]{{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}}),
	EDGE_DETECTION2(new double[][]{{-1, -1, -1}, {-1, 8, -1}, {-1, -1, -1}}),
	LAPLACIAN(new double[][]{{0, -1, 0}, {-1, 4, -1}, {0, -1, 0}}),
	VERTICAL_LINES(new double[][]{{-1, 2, -1}, {-1, 2, -1}, {-1, 2, -1}}),
	DIAGONAL_45_LINES(new double[][]{{-1, -1, 2}, {-1, 2, -1}, {2, -1, -1}}),
	SOBEL_VERTICAL(new double[][]{{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}} ),
	BOX_BLUR(new double[][]{{0.111, 0.111, 0.111}, {0.111, 0.111, 0.111}, {0.111, 0.111, 0.111}});
	// The kernel matrix this filter applies — one grid per constant.
	private final double [][] kernel;
	/** 
     * Stores the kernel matrix for a filter constant.
     * @param kernel the 3x3 matrix of weights for this filter
     */
	FilterSet(double[][] kernel) {
		this.kernel = kernel;
	}
	/**
     * Returns this filter's kernel matrix so it can be applied to an image.
     * @return the 3x3 matrix of weights
     */
    public double[][] getKernel() {
        return kernel;
    }
}
