/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fhkoeln.gm.ki.alg.mutators;

import de.fhkoeln.gm.ki.alg.genes.AbstractGene;
import de.fhkoeln.gm.ki.alg.util.Individual;
import de.fhkoeln.gm.ki.alg.util.Population;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Mahdi
 */
public class RandomResetter extends AbstractMutator{

    @Override
    public Population mutate(Population tmpPopulation, AbstractGene[] viableGenes) {
        Population mutPop;
        ArrayList<Individual> tmpPop;
        tmpPop = tmpPopulation.getPop();
        Random r = new Random();
        
        for(Individual ind : tmpPop){
            ArrayList<AbstractGene> gene;
            gene = ind.getGenes();
            
            for(AbstractGene g : gene){
                int zufall = r.nextInt();
                if(zufall%100<=10){
                    g = viableGenes[zufall%viableGenes.length];
                }
            }
        }
        mutPop = new Population(tmpPop);
        return mutPop;
    }

    @Override
    public String getName() {
        String name;
        name = "RandomResetter";
        return name;
    }
    
}
