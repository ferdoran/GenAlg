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
        Population selectedGen = new Population();
        float overallFitness = 0;
        currentGen.sort();
        ArrayList<Individual> tmpPop = currentGen.getPop();
        int count = 0;
        float weight[] = new float[tmpPop.size()];
        float weightSum = 0;
        
        for(Individual ind: tmpPop){
            overallFitness += ind.execute();
        }
        
        for(int i = 0; i < currentGen.getCurrentSize(); i++){
            weight[i] = tmpPop.get(i).execute()/overallFitness;
            weightSum += weight[i];
        }
        
        while(count<currentGen.getCurrentSize()/10 || count % 2 == 1) {
            float value = new Random().nextFloat() * weightSum;
            for(int j = 0; j<weight.length; j++){
                value -= weight[j];
                if(value <= 0){
                    selectedGen.add(tmpPop.get(j));
                    count++;
                }
            }
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
