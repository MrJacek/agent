/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.hojczak.swa.agent.behaviour;

import jade.core.behaviours.SimpleBehaviour;
import pl.hojczak.swa.abstracts.BehaviourHelper;
import pl.hojczak.swa.agents.Country;
import pl.hojczak.swa.annotations.CountryBehaviour;

/**
 *
 * @author jhojczak
 */
@CountryBehaviour
public class SmartBuyerBehaviour implements MyBehaviour {

    private static final long serialVersionUID = -7298935055403075376L;

    Country agent;
    BehaviourHelper helper;

    public SmartBuyerBehaviour(Country a, BehaviourHelper helper) {
        this.agent = a;
          this.helper = helper;
    }

    @Override
    public boolean done() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void action(SimpleBehaviour beh) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
