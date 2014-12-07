/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.hojczak.swa.agent.behaviour;

import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
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

    Market market;

    public MarketBehaviour(Market agent, BehaviourHelper helper) {
        this.market = agent;
        this.helper = helper;

    }

    @Override
    public boolean done() {
        return market.getCash() <= 0;
    }

    @Override
    public void action(SimpleBehaviour beh) {
        System.out.println(market.getName() + ": Perform ");
        ACLMessage msg = helper.waitForMessage(market);
        if (msg == null) {
            return;
        }
        Contract con = getContract(msg);
        if (isUncompliteContract(con)) {
            System.out.println(market.getName() + ": Recive uncomplete contract: " + con.toString());
            helper.sendMsg(ACLMessage.NOT_UNDERSTOOD, con, msg.getSender(), market);
            return;
        }

        if (ACLMessage.PROPOSE == msg.getPerformative()) {
            System.out.println(market.getName() + ": Recive contract propose: " + con.toString());
            if (market.quantity(con.resource) < con.counts) {
                helper.sendMsg(ACLMessage.REJECT_PROPOSAL, con, msg.getSender(), market);
                return;
            }
            int price = market.calculatePricePeerUnit(con.resource);
            con.totalPrice = con.counts * price;
            market.contracts.put(msg.getSender(), con);
            helper.sendMsg(ACLMessage.INFORM, con, msg.getSender(), market);
            return;
        }

        if (ACLMessage.AGREE == msg.getPerformative() && market.contracts.containsKey(msg.getSender())) {
            System.out.println(market.getName() + ": Recive contract agree: " + con.toString());
            if (market.quantity(con.resource) < con.counts) {
                helper.sendMsg(ACLMessage.CANCEL, con, msg.getSender(), market);
                return;
            }
            for (int i = 0; i < con.counts; ++i) {
                market.removeResource(con.resource);
            }
            market.addCash(con.totalPrice);
            helper.sendMsg(ACLMessage.ACCEPT_PROPOSAL, con, msg.getSender(), market);
            return;
        }

        helper.sendMsg(ACLMessage.NOT_UNDERSTOOD, con, msg.getSender(), market);

    }

    private Contract getContract(ACLMessage msg) {
        try {
            return Contract.class.cast(msg.getContentObject());
        } catch (UnreadableException ex) {
            throw new IllegalStateException(ex);
        }
    }

    private boolean isUncompliteContract(Contract con) {
        return con.resource == null || con.counts <= 0 || con.type == null;
    }

}
