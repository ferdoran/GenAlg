/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fhkoeln.gm.ki.alg.selectors;

import de.fhkoeln.gm.ki.alg.util.Individual;
import de.fhkoeln.gm.ki.alg.util.Population;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Mahdi
 */
public class WeightedProbabilitySelector extends AbstractSelector{

    @Override
    public Population selectFromPopulation(Population currentGen) {
        ArrayList<Individual> indisList = new ArrayList();
        Individual[] indis = new Individual[2];
        for(int i=0;i < indis.length; i++) {
            indis[i] = getWeightedSelection(currentGen);
        }
        
        for(Individual i : indis) {
            indisList.add(i);
        }
        
        return new Population(indisList);
        
    }
    
    private Individual getWeightedSelection(Population currentGen) {
        ArrayList<Individual> tmpPop = currentGen.getPop();
        float overallFitness = 0;
        Individual[] pop = new Individual[tmpPop.size()];
        pop = tmpPop.toArray(pop);
        
        double weight[] = new double[pop.length];
        double weightSum = 0;
        
        for(Individual ind: tmpPop){
            overallFitness += ind.execute();
        }
        
        for(int i = 0; i < pop.length; i++){
            weight[i] = tmpPop.get(i).execute()/overallFitness;
            weightSum += weight[i];
        }
        
        double value = Math.abs(new Random().nextDouble()) * weightSum;
        for(int j = 0; j< pop.length; j++){
            value -= weight[j];
            if(value <= 0){
               return pop[j];
            }
        }
       
        return pop[pop.length-1];
    }

    @Override
    public String getName() {
        String name;
        name = "WeightedProbabilitySelector";
        return name;
    }
    
}
