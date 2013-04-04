import java.awt.Color;
import java.awt.image.BufferedImage;

public class ImageHandler
{
	
	int[][] arr;
	ImagePainter paint;

	public ImageHandler( BufferedImage img ) {
		genArray(img);
		paint = new ImagePainter(img.getWidth(), img.getHeight());
		
		/* paint.save(); */
	}

	// Generate a 2d array of grey pixels of the image
	protected void genArray( BufferedImage img ) {
		// init new array
		arr = new int[img.getWidth()][img.getHeight()];

		// Convert integer array to grey scale 0-255
		for (int i = 0; i<img.getWidth(); i++ ) {
			for (int j = 0; j<img.getHeight(); j++) {
				arr[i][j] = new Color(img.getRGB(i,j)).getRed();
			}
		}
	}

	// Draw a line on canvas
	public void drawLine( int a, int b ) {
		paint.drawLine(a,b);
	}

	public double compare() {
		return paint.compare(arr);
	}

	public void clear() {
		paint.clear();
	}

	public void save(String name) {
		paint.save(name);
	}

}
