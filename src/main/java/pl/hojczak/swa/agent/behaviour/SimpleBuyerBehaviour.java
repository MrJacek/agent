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
public class SimpleBuyerBehaviour implements MyBehaviour {

    private static final long serialVersionUID = -6659772400634708655L;

    Country agent;
    BehaviourHelper helper;

    public SimpleBuyerBehaviour(Country a, BehaviourHelper helper) {
        this.agent = a;
        this.helper = helper;
    }

    @Override
    public boolean done() {
        System.out.println(SimpleBuyerBehaviour.class.getName() + "Done");
        return true;
    }

    @Override
    public void action(SimpleBehaviour beh) {
        System.out.println(SimpleBuyerBehaviour.class.getName() + "Action");
    }

}
