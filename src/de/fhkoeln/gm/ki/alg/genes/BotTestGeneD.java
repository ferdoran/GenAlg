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
public class BotTestGeneD extends AbstractGene{
    final int MOTORSPEED = 360;
    
    @Override
    public char getName() {
        char name;
        name = 'D';
        return name;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getDescription() {
        String desc;
        desc = "-240";
        return desc;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public float execute() {
        float value;
        value = -240;
        Motor.B.setSpeed(MOTORSPEED);
        Motor.B.rotate((int) value);
        return value;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
