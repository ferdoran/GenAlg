package de.fhkoeln.gm.ki.alg.genes;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/***
 * Abstract Class for Genes, Also handles GUI integration
 * @author Sascha Schewe
 *
 */
public abstract class AbstractGene implements ItemListener {
	
	private boolean checked = true;
	
	/***
	 * Short name to be used in the individual's full string
	 * @return the short name
	 */
	public abstract char getName();
	/***
	 * Description of the gene for the menu
	 * @return the gene's description
	 */
	public abstract String getDescription();
	/***
	 * Use this to implement any actions for the gene
	 * @return in case a return signal is needed
	 */
	public abstract float execute();
	
	
	
	
	
	@SuppressWarnings("static-access")
        @Override
	public final void itemStateChanged(ItemEvent e) {
		if(e.getStateChange() == e.SELECTED)
			setChecked(true);
		if(e.getStateChange() == e.DESELECTED)
			setChecked(false);
	}
	public final boolean isChecked() {
		return checked;
	}
	private final void setChecked(boolean checked) {
		this.checked = checked;
	}
}
