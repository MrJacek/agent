/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.hojczak.swa.agent.behaviour;

import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import java.util.logging.Level;
import java.util.logging.Logger;
import pl.hojczak.swa.abstracts.BehaviourHelper;
import pl.hojczak.swa.abstracts.Contract;
import pl.hojczak.swa.agents.Market;

/**
 *
 * @author jhojczak
 */
@pl.hojczak.swa.annotations.MarketBehaviour
public class MarketBehaviour implements MyBehaviour {

    BehaviourHelper helper;

    Market agent;

    public MarketBehaviour(Market agent, BehaviourHelper helper) {
        this.agent = agent;
        this.helper = helper;
    }

    @Override
    public boolean done() {
        return true;
    }

    @Override
    public void action(SimpleBehaviour beh) {
        ACLMessage msg = helper.waitForBuyContract(agent);
        Contract con = getContract(msg);
        helper.sendMsg(ACLMessage.ACCEPT_PROPOSAL, con, msg.getSender(), agent);
    }

    private Contract getContract(ACLMessage msg) {
        try {
            return Contract.class.cast(msg.getContentObject());
        } catch (UnreadableException ex) {
            throw new IllegalStateException(ex);
        }
    }

}
