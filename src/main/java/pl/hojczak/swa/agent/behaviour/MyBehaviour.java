/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.hojczak.swa.agent.behaviour;

import jade.core.behaviours.SimpleBehaviour;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import pl.hojczak.swa.agents.Country;

/**
 *
 * @author jhojczak
 */
public interface MyBehaviour {

    public void action(SimpleBehaviour beh);

    public boolean done();

    default void wirteResult(Country agent) throws FileNotFoundException, UnsupportedEncodingException {
        try (PrintWriter writer = new PrintWriter(agent.getLocalName() + ".result", "UTF-8")) {
            writer.println(agent.printState());
        }

    }

}
