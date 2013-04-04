import java.awt.image.BufferedImage;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;


public class Fitness extends FitnessFunction
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private final ImageHandler handler;

	public Fitness( BufferedImage img ) {
		handler = new ImageHandler(img);
	}

	@Override
	protected double evaluate(IChromosome c) {
		
		handler.clear();

		drawChromosome(c);

		return handler.compare();
	}

	protected void drawChromosome(IChromosome c){
		for (int i=0; i<Evolve.lines; i++) {
			handler.drawLine(
					(Integer) c.getGene(i*2).getAllele(),
					(Integer) c.getGene(i*2+1).getAllele() 
					);
		}
	}

	public void save( String name ) {
		handler.save( name );
	}
}
