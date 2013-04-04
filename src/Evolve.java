import java.awt.image.BufferedImage;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.DoubleGene;
import org.jgap.impl.IntegerGene;

public class Evolve
{

	private IChromosome bestSoluton;

	public static final int lines = 200;
	public static final int res = 128;

	public Evolve( BufferedImage img ) throws InvalidConfigurationException {
		
		Configuration conf = new DefaultConfiguration();

		FitnessFunction fit = new Fitness( img );

		conf.setFitnessFunction(fit);

		Gene[] sampleGenes = new Gene[Evolve.lines*2];

		for (int i=0; i<Evolve.lines*2; i++) {
			sampleGenes[i] = new IntegerGene(conf, 0, Evolve.res*4-1);
		}

		Chromosome crom = new Chromosome(conf, sampleGenes);

		conf.setSampleChromosome(crom);

		conf.setPopulationSize(10);

		Genotype population = Genotype.randomInitialGenotype(conf);


		for (int i = 0; i<1000; i++) {
			population.evolve();
			if(i%100 == 0) {
				System.out.println(i);
			}
		}

		bestSoluton = population.getFittestChromosome();
		((Fitness)fit).drawChromosome( bestSoluton );
		((Fitness)fit).save( "best.png" );
		System.out.println("Fittest: "+bestSoluton.getFitnessValue());

		/* System.out.println("best Solution:"+bestSoluton.getGene(0).getAllele()); */

	}	
}
