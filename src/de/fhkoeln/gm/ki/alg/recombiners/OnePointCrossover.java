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
        
        for(int i = 0; i < 500; i++){
            int r1=-1, r2=-1;
            while(r1==r2){
                r1 = Math.abs(r.nextInt() % tmpPop.size());
                r2 = Math.abs(r.nextInt() % tmpPop.size());
            }
                        
            int split = Math.abs(r.nextInt() % tmpPop.get(0).getGenes().size());
            
            ArrayList<AbstractGene> p1 = tmpPop.get(r1).getGenes();
            ArrayList<AbstractGene> p2 = tmpPop.get(r2).getGenes();
            
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
