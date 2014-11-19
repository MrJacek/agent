/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.hojczak.swa.agent.behaviour;

import jade.core.behaviours.SimpleBehaviour;
import pl.hojczak.swa.agents.CountryAgent;
import pl.hojczak.swa.enums.CountryBehaviour;

/**
 *
 * @author jhojczak
 */
@CountryBehaviour
public class SmartSellerBehaviour extends SimpleBehaviour {
    
    CountryAgent agent;

    public SmartSellerBehaviour(CountryAgent a) {
        super(a);
        this.agent=a;
    }

    @Override
    public void action() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean done() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
