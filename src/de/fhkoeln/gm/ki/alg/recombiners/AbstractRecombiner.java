package de.fhkoeln.gm.ki.alg.recombiners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.fhkoeln.gm.ki.alg.util.DataSource;
import de.fhkoeln.gm.ki.alg.util.Population;

/***
 * Abstract Class for Recombiners, also handles GUI integration
 * @author Sascha Schewe
 *
 */
public abstract class AbstractRecombiner implements ActionListener {
		
	public AbstractRecombiner(){
		DataSource.getInstance().currentRecombiner = this;
	}
	
	public abstract String getName();

	/***
	 * Recombines the population
	 * @param currentPopulation The population to recombine (Could be preselected or selected internally)
	 * @return the recombined population
	 */
	public abstract Population recombine(Population currentPopulation);

	public final void actionPerformed(ActionEvent e) {
		DataSource.getInstance().currentRecombiner=this;
		
	}

}
