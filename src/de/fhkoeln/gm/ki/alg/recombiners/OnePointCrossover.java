/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fhkoeln.gm.ki.alg.recombiners;

import de.fhkoeln.gm.ki.alg.genes.AbstractGene;
import de.fhkoeln.gm.ki.alg.util.Individual;
import de.fhkoeln.gm.ki.alg.util.Population;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Mahdi
 */
public class OnePointCrossover extends AbstractRecombiner{

    @Override
    public String getName() {
        String name;
        name = "OnePointCrossover";
        return name;
    }

    @Override
    public Population recombine(Population currentPopulation) {
        Population newPop = new Population();
        Random r = new Random();
        ArrayList<Individual> tmpPop = currentPopulation.getPop();
        int loopEnd;
        if(tmpPop.size()%2==0){
            loopEnd = tmpPop.size();
        }
        else{
            loopEnd = tmpPop.size()-1;
        }
        
        for(int i = 0; i < loopEnd; i+=2){            
            int split = Math.abs(r.nextInt() % tmpPop.get(0).getGenes().size());
            
            ArrayList<AbstractGene> p1 = tmpPop.get(i).getGenes();
            ArrayList<AbstractGene> p2 = tmpPop.get(i+1).getGenes();
            
            ArrayList<AbstractGene> c1 = new ArrayList<>();
            ArrayList<AbstractGene> c2 = new ArrayList<>();
            
            for(int j = 0; j < split; j++){
                c1.add(p1.get(j));
                c2.add(p2.get(j));
            }
            
            for(int j = split; j < tmpPop.get(0).getGenes().size(); j++){
                c1.add(p2.get(j));
                c2.add(p1.get(j));
            }
            
            newPop.add(new Individual(c1));
            newPop.add(new Individual(c2));
            
        }
        
        return newPop;
    }
    
}
