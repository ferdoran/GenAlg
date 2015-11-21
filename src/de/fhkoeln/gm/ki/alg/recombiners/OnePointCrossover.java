/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fhkoeln.gm.ki.alg.recombiners;

import de.fhkoeln.gm.ki.alg.util.Population;

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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
