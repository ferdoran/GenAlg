/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fhkoeln.gm.ki.alg.genes;

/**
 *
 * @author Mahdi
 */
public class TestGene2 extends AbstractGene{

    @Override
    public char getName() {
        char name;
        name = '2';
        return name;
    }

    @Override
    public String getDescription() {
        String desc;
        desc = "+10";
        return desc;
    }

    @Override
    public float execute() {
        return 10;
    }
    
}
