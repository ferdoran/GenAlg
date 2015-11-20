package de.fhkoeln.gm.ki.alg.fitnessFunctions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.fhkoeln.gm.ki.alg.util.DataSource;
import de.fhkoeln.gm.ki.alg.util.Individual;

/***
 * Abstract Class for Fitness Functions, also handles GUI integration
 * @author Sascha Schewe
 *
 */
public abstract class AbstractFitness implements ActionListener {

	public AbstractFitness(){
		DataSource.getInstance().currentFitness = this;
	}
	/***
	 * Method for evaluating a genome
	 * @param genome the genome to be evaluated
	 * @return the genome's fitness
	 */
	public abstract float evaluate(Individual genome);
	
	/***
	 * Queried by the GA to find out whether the target fitness has been reached at some point
	 * @return true if the target fitness has been reached
	 */
	public abstract boolean thresholdReached();
	
	
	/***
	 * The name that's displayed on the menu
	 * @return the name to be displayed.
	 */
	public abstract String getName();
	
	public final void actionPerformed(ActionEvent e) {
		DataSource.getInstance().currentFitness=this;
		
	}

}
