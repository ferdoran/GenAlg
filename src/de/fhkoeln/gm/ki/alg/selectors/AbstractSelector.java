package de.fhkoeln.gm.ki.alg.selectors;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.fhkoeln.gm.ki.alg.util.DataSource;
import de.fhkoeln.gm.ki.alg.util.Population;

/***
 * Abstract Class for Selectors, also handles GUI integration
 * @author Sascha Schewe
 *
 */
public abstract class AbstractSelector implements ActionListener {

	public AbstractSelector(){
		DataSource.getInstance().currentSelector = this;
	}
	
	/***
	 * Selects a new sub-population to be handed over to the recombiner (First step)
	 * @param currentGen the starting population
	 * @return a subset of the starting population to be handed to the recombiner
	 */
	public abstract Population selectFromPopulation(Population currentGen);
	
	public abstract String getName();


	public final void actionPerformed(ActionEvent e) {
		DataSource.getInstance().currentSelector=this;
		
	}


}
