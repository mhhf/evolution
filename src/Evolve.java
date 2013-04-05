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
	public static final int informations = 4;

	public static final int res = 256;
	public static final int iterations = 100;
	public static final int populationSize = 2;

	public Evolve( BufferedImage img ) throws InvalidConfigurationException {
		
		Configuration conf = new DefaultConfiguration();

		FitnessFunction fit = new Fitness( img );

		conf.setFitnessFunction(fit);

		Gene[] sampleGenes = new Gene[Evolve.lines*Evolve.informations];

		for (int i=0; i<Evolve.lines*Evolve.informations; i++) {
			sampleGenes[i] = new IntegerGene(conf, 0, Evolve.res-1);
		}

		Chromosome crom = new Chromosome(conf, sampleGenes);

		conf.setSampleChromosome(crom);

		conf.setPopulationSize(Evolve.populationSize);

		Genotype population = Genotype.randomInitialGenotype(conf);


		for (int i = 0; i<Evolve.iterations; i++) {
			population.evolve();
			if(i% ( Evolve.iterations/100) == 0) {
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
