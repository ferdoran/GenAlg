package de.fhkoeln.gm.ki.alg;

import java.io.IOException;
import java.io.NotActiveException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.rmi.activation.UnknownObjectException;
import java.util.ArrayList;
import java.util.List;

import de.fhkoeln.gm.ki.alg.fitnessFunctions.AbstractFitness;
import de.fhkoeln.gm.ki.alg.mutators.AbstractMutator;
import de.fhkoeln.gm.ki.alg.recombiners.AbstractRecombiner;
import de.fhkoeln.gm.ki.alg.reproducers.AbstractReproducer;
import de.fhkoeln.gm.ki.alg.selectors.AbstractSelector;
import de.fhkoeln.gm.ki.alg.util.DataSource;
import de.fhkoeln.gm.ki.alg.util.Individual;
import de.fhkoeln.gm.ki.alg.util.Population;
import de.fhkoeln.gm.ki.gui.GUI_Swing;
import de.fhkoeln.gm.ki.gui.GraphData;
import de.fhkoeln.gm.ki.remoteControl.BotMonitor;

/***
 * Controls the standard flow of the GA
 * Should be able to accommodate most styles of GA without modification
 * 
 * @author Sascha Schewe
 *
 */
public class GeneticAlgorithm extends Thread {
		
	private int macroStepPause = 200; //200
	private int microStepPause = 10; // 10
	public boolean microPauses=true;
	public boolean macroPauses=true;
	public boolean currentlyPaused=false;
	public boolean finished=false;
	
	private ArrayList<Population> allGenerations;
	private Population currentPopulation;
	private Population tmpPopulation;
	private AbstractSelector selector;
	private AbstractRecombiner recombiner;
	private AbstractMutator mutator;
	private AbstractReproducer reproducer;
	private AbstractFitness fitnessFunction;
	
	private boolean paused = false;
	private boolean killed = false;
	private int maxGenerations=2000;
	private int generation=0;
	private float overrideFitness=Float.POSITIVE_INFINITY;
	

	@SuppressWarnings("unused")
	private GeneticAlgorithm(){}
	
	public GeneticAlgorithm(Population firstGeneration, AbstractFitness fitnessFunction, AbstractSelector selector, AbstractRecombiner recombiner, AbstractMutator mutator, AbstractReproducer reproducer, int maxGenerations, float fitnessOverride){
		super();
		setDaemon(true);
		this.maxGenerations = maxGenerations;
		this.overrideFitness = fitnessOverride;
		allGenerations = new ArrayList<Population>();
		currentPopulation=firstGeneration;
		this.selector=selector;
		this.recombiner=recombiner;
		this.mutator=mutator;
		this.reproducer=reproducer;
		this.fitnessFunction = fitnessFunction;
		DataSource.getInstance().currentGeneticAlgorithm = this;
		DataSource.getInstance().currentFitness = fitnessFunction;
		DataSource.getInstance().currentReproducer = reproducer;
		DataSource.getInstance().currentMutator = mutator;
		DataSource.getInstance().currentRecombiner = recombiner;
		DataSource.getInstance().currentSelector = selector;
		DataSource.getInstance().setPopulation(currentPopulation);
	}
	
	@Override
	public void run(){
		currentlyPaused=false;
		for(generation = 0; generation<maxGenerations; generation++){
			evaluate();
			if(killed)
				return;
			currentPopulation.sort();
			if(fitnessFunction.thresholdReached()||currentPopulation.getFittestIndividual().fitness>=overrideFitness){
				updateGUI();
				GUI_Swing.getInstance().updateWeakestFittestIndividual(currentPopulation.getWeakestIndividual(), currentPopulation.getFittestIndividual());
				notifyGUIFinishedAlgorithm();
				return;
			}
			select();
			recombine();
			mutate();
			updateGUI();
			reproduce();
			
			while(paused){
				currentlyPaused=true;
				yield();
			}
			currentlyPaused=false;
			try {
				if(macroPauses)
					Thread.sleep(macroStepPause);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		GUI_Swing.getInstance().algorithmIsFinished();
	}
	
	private void notifyGUIFinishedAlgorithm() {
		finished=true;
		GUI_Swing.getInstance().algorithmIsFinished();
	}

	public void setMaxGenerations(int generations){
		maxGenerations=generations;
	}

	public void pauseThread(){
		paused=true;
	}
	
	public void resumeThread(){
		paused=false;
	}
	
	private void evaluate(){
		for(int i=0;i<currentPopulation.getPop().size();i++){
			boolean wasPaused=false;
			try {
				//BotMonitor.getInstance().resetArm();
				if(!BotMonitor.getInstance().checkSensorRange()){
					paused=true;
					wasPaused=true;
					GUI_Swing.getInstance().warnOfSensorRange();
				}
			} catch (NotActiveException e1) {}	

			while(paused){
				currentlyPaused=true;
				yield();
			}
			currentlyPaused=false;
			if(wasPaused){
				i--;
				continue;
			}

			fitnessFunction.evaluate(currentPopulation.getPop().get(i));

			if(killed)
				return;
			if(microPauses){
				currentPopulation.sort();
				updateGUI();
				try {
					Thread.sleep(microStepPause);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		currentPopulation.sort();
		GraphData.getInstance().addGeneration(generation, currentPopulation.getMaximumFitness(), currentPopulation.getAverageFitness(), currentPopulation.getMinimumFitness());
	}
	
	private void select(){
		tmpPopulation=selector.selectFromPopulation(currentPopulation);
	}
	
	private void recombine() {
		tmpPopulation= recombiner.recombine(tmpPopulation);
	}

	private void mutate() {
		tmpPopulation=mutator.mutate(tmpPopulation, DataSource.getInstance().getViableGenes());
		
	}

	private void reproduce() {
		allGenerations.add(currentPopulation);
		currentPopulation = reproducer.reproduce(currentPopulation, tmpPopulation);
		
	}

	private void updateGUI(){
		DataSource.getInstance().setPopulation(currentPopulation);
		GUI_Swing.getInstance().updateTextFields();
	}
	
	public void kill() {
		killed=true;
		paused=false;
		
	}
	public boolean isPaused(){
		return paused;
	}
	
	public void saveState(String fileName){
		ArrayList<String> state = new ArrayList<String>();
		state.add(selector.getName());
		state.add(recombiner.getName());
		state.add(mutator.getName());
		state.add(reproducer.getName());
		state.add(fitnessFunction.getName());
		state.add(String.valueOf(maxGenerations));
		state.add(String.valueOf(overrideFitness));
		
		
		state.addAll(currentPopulation.getIndividualsAsStrings());
		
		try {
			Files.write(Paths.get(fileName), state, Charset.defaultCharset(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static GeneticAlgorithm loadState(String fileName){
		ArrayList<String> state = new ArrayList<String>();
		GeneticAlgorithm ga = null;
		
		try {
			List<String> lines = Files.readAllLines(Paths.get(fileName), Charset.defaultCharset());
			state.addAll(lines);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			AbstractSelector selector=DataSource.getInstance().findSelector(state.get(0));
			AbstractRecombiner recombiner=DataSource.getInstance().findRecombiner(state.get(1));
			AbstractMutator mutator=DataSource.getInstance().findMutator(state.get(2));
			AbstractReproducer reproducer=DataSource.getInstance().findReproducer(state.get(3));
			AbstractFitness fitnessFunction=DataSource.getInstance().findFitnessFunction(state.get(4));
			int maxGens=Integer.valueOf(state.get(5));
			float overrideFitness= Float.valueOf(state.get(6));
			ArrayList<Individual> individuals = new ArrayList<Individual>();
			for(int i=7;i<state.size();i++){
				individuals.add(new Individual(state.get(i),DataSource.getInstance().getGenes()));
			}
			Population pop = new Population(individuals);
			pop.sort();
			GUI_Swing.getInstance().updateWeakestFittestIndividual(pop.getWeakestIndividual(), pop.getFittestIndividual());
			ga = new GeneticAlgorithm(pop, fitnessFunction, selector, recombiner, mutator, reproducer, maxGens, overrideFitness);
			ga.currentlyPaused=true;
		} catch (UnknownObjectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ga;
	}

}


