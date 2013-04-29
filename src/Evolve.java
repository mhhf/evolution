import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

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

public class Evolve {

	private IChromosome bestSoluton;

	public static final int lines = 200;
	public static final int informations = 4;

	public static final int res = 256;
	public static final int iterations = 1000;
	public static final int populationSize = 2;

	public Evolve(BufferedImage img) throws InvalidConfigurationException,
			IOException {
		
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


		FileWriter fstream = new FileWriter("out.txt");
		BufferedWriter out = new BufferedWriter(fstream);

		
		out.write("{");
		int i=0;
		for (i = 0; i<Evolve.iterations; i++) {
			population.evolve();
			if(i% ( Evolve.iterations/100) == 0) {
				bestSoluton = population.getFittestChromosome();
				out.write("{"+i+","+bestSoluton.getFitnessValue()+"},");
			}
		}

		bestSoluton = population.getFittestChromosome();
		((Fitness)fit).drawChromosome( bestSoluton );
		((Fitness)fit).save( "best.png" );
		System.out.println("Fittest: "+bestSoluton.getFitnessValue());
		out.write("{"+i+","+bestSoluton.getFitnessValue()+"}}");
		out.close();

		/* System.out.println("best Solution:"+bestSoluton.getGene(0).getAllele()); */

	}	
}
