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
public class SimpleSellerBehaviour extends SimpleBehaviour {

    CountryAgent agent;

    public SimpleSellerBehaviour(CountryAgent a) {
        super(a);
        this.agent = a;
    }

    @Override
    public void action() {
        System.out.println(SimpleSellerBehaviour.class.getName() + "Action");
    }

    @Override
    public boolean done() {
        System.out.println(SimpleSellerBehaviour.class.getName() + "Done");
        return true;
    }

}
