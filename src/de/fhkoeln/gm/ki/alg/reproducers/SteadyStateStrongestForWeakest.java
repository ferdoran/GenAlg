/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fhkoeln.gm.ki.alg.reproducers;

import de.fhkoeln.gm.ki.alg.util.Individual;
import de.fhkoeln.gm.ki.alg.util.IndividualComparator;
import de.fhkoeln.gm.ki.alg.util.Population;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author Mahdi
 */
public class SteadyStateStrongestForWeakest extends AbstractReproducer{

    @Override
    public Population reproduce(Population oldGeneration, Population tmpGeneration) {
        Population newPop = null;
        Random r = new Random();
        ArrayList<Individual> old = oldGeneration.getPop();
        ArrayList<Individual> tmp = tmpGeneration.getPop();
        ArrayList<Individual> neu = null;
        
        for(int i = 0; i < tmp.size(); i++){
            old.add(tmp.get(i));
        }
 
        Collections.sort(old, new IndividualComparator());
        
        for(int i = 0; i < tmp.size(); i++){
            neu.add(old.get(i));
        }
        
        newPop = new Population(neu);
        
        return newPop;
    }

    @Override
    public String getName() {
        String name;
        name = "SteadyStateStrongestForWeakest";
        return name;
    }
    
}
