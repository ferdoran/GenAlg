/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fhkoeln.gm.ki.alg.selectors;

import de.fhkoeln.gm.ki.alg.util.Individual;
import de.fhkoeln.gm.ki.alg.util.Population;
import java.util.ArrayList;

/**
 *
 * @author Mahdi
 */
public class WeightedProbabilitySelector extends AbstractSelector{

    @Override
    public Population selectFromPopulation(Population currentGen) {
        Population selectedGen = null;
        float overallFitness = 0;
        ArrayList<Individual> tmpPop = currentGen.getPop();
        
        for(Individual ind: tmpPop){
            overallFitness += ind.execute();
        }
        
        
        return selectedGen;
    }

    @Override
    public String getName() {
        String name;
        name = "WeightedProbabilitySelector";
        return name;
    }
    
}
