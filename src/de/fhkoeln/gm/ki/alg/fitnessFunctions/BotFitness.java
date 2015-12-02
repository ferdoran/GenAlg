/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fhkoeln.gm.ki.alg.fitnessFunctions;

import de.fhkoeln.gm.ki.alg.util.Individual;

/**
 *
 * @author Mahdi
 */
public class BotFitness extends AbstractFitness{

    @Override
    public float evaluate(Individual genome) {
        float fitness;
        fitness = -1000000;
        
        return fitness;
    }

    @Override
    public boolean thresholdReached() {
        return false;
    }

    @Override
    public String getName() {
        String name;
        name = "Fitness LegoBot";
        return name;
    }
    
}
