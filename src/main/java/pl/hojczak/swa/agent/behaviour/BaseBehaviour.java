/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.hojczak.swa.agent.behaviour;

import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;

/**
 *
 * @author jhojczak
 */
public class BaseBehaviour extends SimpleBehaviour {
    private static final long serialVersionUID = -6153311597730477193L;

    private final MyBehaviour beh;

    public BaseBehaviour(MyBehaviour beh, Agent agent) {
        super(agent);
        this.beh = beh; 
    }

    @Override
    public void action() {
        beh.action(this);

    }

    @Override
    public boolean done() {
        return beh.done();
    }


}
