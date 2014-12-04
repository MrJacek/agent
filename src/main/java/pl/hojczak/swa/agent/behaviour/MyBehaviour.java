/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.hojczak.swa.agent.behaviour;

import jade.core.behaviours.SimpleBehaviour;

/**
 *
 * @author jhojczak
 */
public interface MyBehaviour {

    public void action(SimpleBehaviour beh);

    public boolean done();
    
}
