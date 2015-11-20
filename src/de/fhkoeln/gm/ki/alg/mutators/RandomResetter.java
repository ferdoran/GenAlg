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

/**
 *
 * @author Mahdi
 */
public class RandomResetter extends AbstractMutator{

    @Override
    public Population mutate(Population tmpPopulation, AbstractGene[] viableGenes) {
        Population mutPop;
        mutPop = null;
        ArrayList<Individual> tmpPop;
        tmpPop = tmpPopulation.getPop();
        for(Individual ind : tmpPop){
            
        }
        return mutPop;
    }

    @Override
    public String getName() {
        String name;
        name = "RandomResetter";
        return name;
    }
    
}
