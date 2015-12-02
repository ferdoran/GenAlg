/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fhkoeln.gm.ki.alg.fitnessFunctions;

import de.fhkoeln.gm.ki.alg.genes.AbstractGene;
import de.fhkoeln.gm.ki.alg.util.Individual;
import java.util.ArrayList;

/**
 *
 * @author Mahdi
 */
public class FitnessFunction extends AbstractFitness{
    private float highest = -10000000;

    @Override
    public float evaluate(Individual genome) {
        float fitness;
        fitness = 0;
        ArrayList<AbstractGene> genomeList;
        genomeList = genome.getGenes();
        for(AbstractGene gene : genomeList){
            fitness += gene.execute();
        }
        if(fitness>highest){
            highest = fitness;
        }
        return fitness;
    }

    @Override
    public boolean thresholdReached() {
        boolean threshold;
        threshold = highest>=1200;
               
        return threshold;
    }

    @Override
    public String getName() {
        String name;
        name = "Fitness Aufgabe 2.1";
        return name;
    }
    
}
