/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fhkoeln.gm.ki.alg.genes;

import lejos.nxt.Motor;

/**
 *
 * @author Mahdi
 */
public class BotTestGeneC extends AbstractGene{
    final int MOTORSPEED = 100;
    
    @Override
    public char getName() {
        char name;
        name = 'C';
        return name;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getDescription() {
        String desc;
        desc = "-15";
        return desc;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public float execute() {
        float value;
        value = -15;
        Motor.A.setSpeed(MOTORSPEED);
        Motor.A.rotate((int) value);
        return value;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
