import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImagePainter
{


	BufferedImage img;
	Graphics2D g;
	int w,h;

	public ImagePainter( int w, int h ) {
		this.w = w;
		this.h = h;
		img = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
		g = (Graphics2D) img.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor( new Color(255,255,255) );
		g.fillRect( 0, 0, w-1, h-1 );
		g.setColor( new Color(0,0,0) );
	}	

	// d in [0, 39]
	public void drawLine(int d, int e) {
		int[] p1,p2;
		p1 = getPoint ( d );
		p2 = getPoint ( e );

		Shape l = new Line2D.Double( p1[0], p1[1], p2[0], p2[1] );
		g.draw(l);
	}

	public void save(String name) {
		try {
			// retrieve image
			File outputfile = new File(name);
			ImageIO.write(img, "png", outputfile);
		} catch (IOException e) {
		}	
	}

	private int[] getPoint(int d) {
		int dw = w/Evolve.res;
		int dh = h/Evolve.res;
		int[] point = new int[2];
		if(d<Evolve.res*2) { // 
			if(d/Evolve.res<1){ // top
				point[0] = 0;
				point[1] = (d % Evolve.res) * dh + dh/2;
			} else { // bottom
				point[0] = w;
				point[1] = (d % Evolve.res) * dh + dh/2;
			}
		} else { // LEFT / RIGHT
			if(d/(Evolve.res*3)<1){ // left
				point[0] = ( d % Evolve.res ) * dw + dw/2;
				point[1] = 0;
			} else { // right
				point[0] = ( d % Evolve.res ) * dw + dw/2;
				point[1] = h;
			}
		}

		return point;
	}

	public double compare(int[][] arr) {
		double sum = 0;

		for (int i = 0; i<w; i++ ) {
			for (int j = 0; j<h; j++) {
				sum += 1.0-Math.abs(arr[i][j]-new Color(img.getRGB(i,j)).getRed())/255.0;
			}
		}
		
		return sum;
	}

	public void clear() {
		g.setColor( new Color(255,255,255) );
		g.fillRect( 0, 0, w-1, h-1 );
		g.setColor( new Color(0,0,0) );
	}

	
}
