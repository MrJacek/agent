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
public class SimpleSellerBehaviour implements MyBehaviour {

    private static final long serialVersionUID = -5415577555363991932L;

    Country agent;
    BehaviourHelper helper;

    public SimpleSellerBehaviour(Country a, BehaviourHelper helper) {
        this.agent = a;
        this.helper=helper;
    }

    @Override
    public boolean done() {
        System.out.println(SimpleSellerBehaviour.class.getName() + "Done");
        return true;
    }

    @Override
    public void action(SimpleBehaviour beh) {
        System.out.println(SimpleSellerBehaviour.class.getName() + "Action");
    }

}
