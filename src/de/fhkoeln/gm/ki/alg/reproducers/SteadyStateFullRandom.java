/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fhkoeln.gm.ki.alg.reproducers;

import de.fhkoeln.gm.ki.alg.util.Individual;
import de.fhkoeln.gm.ki.alg.util.Population;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Mahdi
 */
public class SteadyStateFullRandom extends AbstractReproducer{

    @Override
    public Population reproduce(Population oldGeneration, Population tmpGeneration) {
        Population newPop = new Population();
        Random r = new Random();
        ArrayList<Individual> old = oldGeneration.getPop();
        ArrayList<Individual> tmp = tmpGeneration.getPop();
        ArrayList<Individual> neu = new ArrayList();
        
        for(int i = 0; i < old.size(); i++){
            if(r.nextInt()%2==0 || i >= tmp.size()){
                neu.add(old.get(i));
            }
            else{
                neu.add(tmp.get(i));
            }
        }
        
        newPop = new Population(neu);
        
        return newPop;
    }

    @Override
    public String getName() {
        String name;
        name = "SteadyStateFullRandom";
        return name;
    }
    
}
