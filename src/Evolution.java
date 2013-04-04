import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jgap.InvalidConfigurationException;

public class Evolution {
	public static final void main(String[] args)
			throws InvalidConfigurationException {
		/* EvolutionFramework frame = new EvolutionFramework(); */

		// Load image 
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("./assets/Bob_256.jpeg"));

			Evolve ev = new Evolve(img);
			
		} catch (IOException e) {
		}

  }
}
