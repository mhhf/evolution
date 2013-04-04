import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jgap.InvalidConfigurationException;

public class Evolution {
	static { /* works fine! ! */
		System.setProperty("java.awt.headless", "true");
	}


	public static final void main(String[] args)
			throws InvalidConfigurationException {

		/* EvolutionFramework frame = new EvolutionFramework(); */

		// Load image 
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("./assets/Bob_256.jpeg"));

			new Evolve(img);
			
		} catch (IOException e) {
		}

  }
}
