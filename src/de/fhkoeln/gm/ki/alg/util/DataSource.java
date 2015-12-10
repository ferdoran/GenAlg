package de.fhkoeln.gm.ki.alg.util;

import java.rmi.activation.UnknownObjectException;
import java.util.ArrayList;

import de.fhkoeln.gm.ki.alg.GeneticAlgorithm;
import de.fhkoeln.gm.ki.alg.fitnessFunctions.BotFitness;
import de.fhkoeln.gm.ki.alg.fitnessFunctions.FitnessFunction;
import de.fhkoeln.gm.ki.alg.fitnessFunctions.AbstractFitness;
import de.fhkoeln.gm.ki.alg.genes.AbstractGene;
import de.fhkoeln.gm.ki.alg.genes.BotTestGeneA;
import de.fhkoeln.gm.ki.alg.genes.BotTestGeneB;
import de.fhkoeln.gm.ki.alg.genes.BotTestGeneC;
import de.fhkoeln.gm.ki.alg.genes.BotTestGeneD;
import de.fhkoeln.gm.ki.alg.genes.TestGene1;
import de.fhkoeln.gm.ki.alg.genes.TestGene2;
import de.fhkoeln.gm.ki.alg.genes.TestGene3;
import de.fhkoeln.gm.ki.alg.genes.TestGene4;
import de.fhkoeln.gm.ki.alg.mutators.AbstractMutator;
import de.fhkoeln.gm.ki.alg.mutators.RandomResetter;
import de.fhkoeln.gm.ki.alg.recombiners.AbstractRecombiner;
import de.fhkoeln.gm.ki.alg.recombiners.OnePointCrossover;
import de.fhkoeln.gm.ki.alg.reproducers.FullReplacement;
import de.fhkoeln.gm.ki.alg.reproducers.AbstractReproducer;
import de.fhkoeln.gm.ki.alg.reproducers.SteadyStateFullRandom;
import de.fhkoeln.gm.ki.alg.reproducers.SteadyStateStrongestForWeakest;
import de.fhkoeln.gm.ki.alg.selectors.AbstractSelector;
import de.fhkoeln.gm.ki.alg.selectors.WeightedProbabilitySelector;


/***
 * Saves initial Data such as known and selected components to pass to the GA.
 * Also used by the GA to pass information back to the GUI.
 * @author Sascha Schewe
 *
 */
public class DataSource {

	
	private ArrayList<AbstractGene> genes;
	private ArrayList<AbstractFitness> fitnessFunctions;
	private ArrayList<AbstractMutator> mutators;
	private ArrayList<AbstractReproducer> reproducers;
	private ArrayList<AbstractSelector> selectors;
	private ArrayList<AbstractRecombiner> recombiners;
	private Population pop;
	private AbstractGene[] viableGenes;
	
	private static DataSource self;
	
	public AbstractFitness currentFitness;
	public AbstractMutator currentMutator;
	public AbstractReproducer currentReproducer;
	public AbstractSelector currentSelector;
	public AbstractRecombiner currentRecombiner;
	public GeneticAlgorithm currentGeneticAlgorithm;
	
	public static String BOTNAME = "A_BLAU";
	private DataSource(){}
	
	public static DataSource getInstance(){
		if(self==null)
			self=new DataSource();
		return self;
	}
	
	private DataSource(ArrayList<AbstractGene> genes, ArrayList<AbstractFitness> fitnessFunctions, ArrayList<AbstractMutator> mutators, ArrayList<AbstractReproducer> reproducers){
		this.genes = genes;
//		this.population = genomes;
		this.fitnessFunctions = fitnessFunctions;
		this.mutators = mutators;
		this.reproducers = reproducers;
	}
	
	public static DataSource getDataSource(ArrayList<AbstractGene> genes, ArrayList<AbstractFitness> fitnessFunctions, ArrayList<AbstractMutator> mutators, ArrayList<AbstractReproducer> reproducers){
		if(self==null)
			self= new DataSource(genes,fitnessFunctions,mutators,reproducers);
		return self;
	}
	
	public ArrayList<AbstractGene> getGenes(){
		return genes;
	}
	
//	public ArrayList<Individual> getPopulation(){
//		return population;
//	}
	public Population getPopulation(){
		return pop;
	}
	public void setPopulation(Population population){
		pop = population;
	}
	
	public ArrayList<AbstractFitness> getFitnessFunctions(){
		return fitnessFunctions;
	}
	
	public ArrayList<AbstractSelector> getSelectors(){
		return selectors;
	}
	
	public ArrayList<AbstractRecombiner> getRecombiners(){
		return recombiners;
	}
	
	public ArrayList<AbstractMutator> getMutators(){
		return mutators;
	}
	
	public ArrayList<AbstractReproducer> getReproducers(){
		return reproducers;
	}
	
	public void setViableGenes(AbstractGene[] viableGenes){
		this.viableGenes = viableGenes;
	}
	
	public AbstractGene[] getViableGenes(){
		return viableGenes;
	}

	/***
	 * Only necessary if the Lists aren't passed via Constructor.
	 * In which case please implement.
	 */
	public void initialize(){
		genes = new ArrayList<AbstractGene>();
		fitnessFunctions = new ArrayList<AbstractFitness>();
		mutators = new ArrayList<AbstractMutator>();
		reproducers = new ArrayList<AbstractReproducer>();
		recombiners = new ArrayList<AbstractRecombiner>();
		selectors = new ArrayList<AbstractSelector>();
//		genes.add(new BotTestGeneA());
//		genes.add(new BotTestGeneB());
//		genes.add(new BotTestGeneC());
//		genes.add(new BotTestGeneD());
		genes.add(new TestGene1());
		genes.add(new TestGene2());
		genes.add(new TestGene3());
		genes.add(new TestGene4());
		fitnessFunctions.add(new FitnessFunction());
		fitnessFunctions.add(new BotFitness());
		mutators.add(new RandomResetter());
		reproducers.add(new FullReplacement());
		reproducers.add(new SteadyStateStrongestForWeakest());
		reproducers.add(new SteadyStateFullRandom());
		recombiners.add(new OnePointCrossover());
		selectors.add(new WeightedProbabilitySelector());

//		throw new UnsupportedOperationException("Initialize needs to be implemented before you use it!");
	}

	public AbstractSelector findSelector(String string) throws UnknownObjectException {
		for(AbstractSelector sel : selectors)
			if(sel.getName().equals(string))
				return sel;
		throw new UnknownObjectException("This selector is unknown");
	}
	public AbstractRecombiner findRecombiner(String string) throws UnknownObjectException {
		for(AbstractRecombiner sel : recombiners)
			if(sel.getName().equals(string))
				return sel;
		throw new UnknownObjectException("This recombiner is unknown");
	}
	public AbstractMutator findMutator(String string) throws UnknownObjectException {
		for(AbstractMutator sel : mutators)
			if(sel.getName().equals(string))
				return sel;
		throw new UnknownObjectException("This mutator is unknown");
	}
	public AbstractReproducer findReproducer(String string) throws UnknownObjectException {
		for(AbstractReproducer sel : reproducers)
			if(sel.getName().equals(string))
				return sel;
		throw new UnknownObjectException("This reproducer is unknown");
	}
	public AbstractFitness findFitnessFunction(String string) throws UnknownObjectException {
		for(AbstractFitness sel : fitnessFunctions)
			if(sel.getName().equals(string))
				return sel;
		throw new UnknownObjectException("This fitness function is unknown");
	}
}