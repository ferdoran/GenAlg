/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fhkoeln.gm.ki.alg.genes;

import lejos.nxt.Motor;
import de.fhkoeln.gm.ki.remoteControl.Connector;

/**
 *
 * @author Mahdi
 */
public class BotTestGeneB extends AbstractGene{
    final int MOTORSPEED = 180;

    @Override
    public char getName() {
        char name;
        name = 'B';
        return name;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getDescription() {
        String desc;
        desc = "+360";
        return desc;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public float execute() {
        float value;
        value = +360;
        Motor.B.setSpeed(MOTORSPEED);
        Motor.B.rotate((int) value);
        return value;
        
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
