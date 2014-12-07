/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.hojczak.swa.agent.behaviour;

import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import java.util.HashMap;
import java.util.Map;
import pl.hojczak.swa.abstracts.BehaviourHelper;
import pl.hojczak.swa.abstracts.Contract;
import pl.hojczak.swa.agents.Country;
import pl.hojczak.swa.annotations.CountryBehaviour;
import pl.hojczak.swa.enums.ContractType;
import pl.hojczak.swa.enums.Goal;

/**
 *
 * @author jhojczak
 */
@CountryBehaviour
public class SimpleBuyerBehaviour implements MyBehaviour {

    private static final long serialVersionUID = -6659772400634708655L;

    Country agent;
    BehaviourHelper helper;
    Map<AID, Contract> contracts;
    State currentState;
    ACLMessage currentMessage;

    enum State {

        SearchMarkets,
        CheckOfferts,
        WaitForAnswer,
        ComputeOffert,
        ComplateContract;

    }

    public SimpleBuyerBehaviour(Country a, BehaviourHelper helper) {
        this.agent = a;
        this.helper = helper;
        currentState = State.SearchMarkets;
        contracts = new HashMap<>();
    }

    @Override
    public boolean done() {
        System.out.println(agent.getName() + ": Cash left: " + agent.getCash());
        return agent.getCash() <= 0;
    }

    @Override
    public void action(SimpleBehaviour beh) {
        System.out.println(agent.getName() + ": Perform");

        switch (currentState) {
            case SearchMarkets:
                System.out.println(agent.getName() + ": Start looking markets");
                searchMarkets();

                if (contracts.size() > 0) {
                    currentState = State.CheckOfferts;
                }

                return;
            case CheckOfferts:
                System.out.println(agent.getName() + ": Check offerts markets");
                sendOfferts();
                System.out.println(agent.getName() + ": Wait for market answer");
                currentState = State.WaitForAnswer;
                break;
            case WaitForAnswer:
                currentMessage = helper.waitForMessage(agent);
                if (currentMessage != null) {
                    currentState = State.ComputeOffert;
                }
                break;
            case ComputeOffert:

                if (ACLMessage.INFORM == currentMessage.getPerformative()) {
                    Contract con = helper.getContract(currentMessage);
                    System.out.println(agent.getName() + ": Recive contract:\n" + con.toString());
                    if (con.totalPrice < agent.getCash()) {
                        helper.sendMsg(ACLMessage.AGREE, con, currentMessage.getSender(), agent);
                    }
                }
                currentMessage = helper.waitForContract(agent);
                if (currentMessage.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {
                    currentState = State.ComplateContract;
                } else if (currentMessage.getPerformative() == ACLMessage.INFORM) {
                    currentState = State.ComputeOffert;
                }
                break;
            case ComplateContract:
                if (ACLMessage.ACCEPT_PROPOSAL == currentMessage.getPerformative()) {
                    Contract con = helper.getContract(currentMessage);
                    System.out.println(agent.getName() + ": Complete contract:\n" + con.toString());
                    agent.removeCash(con.totalPrice);
                    Integer counts = agent.collects.get(con.resource);
                    if (counts == null) {
                        counts = 0;
                    }
                    agent.collects.put(con.resource, counts + con.counts);
                }
                currentState = State.CheckOfferts;
                break;
        }

    }

    private void sendOfferts() {

        Goal g = agent.goals.get(0);
        System.out.println(agent.getName() + ": Send contract for goal: " + g.toString());

        for (Map.Entry<AID, Contract> entry : contracts.entrySet()) {

            Contract con = new Contract();
            con.counts = 1;
            con.resource = g.res;
            con.type = ContractType.BUY;
            System.out.println(agent.getName() + ": Sending contract: " + con.toString() + " to " + entry.getKey().getName());
            helper.sendMsg(ACLMessage.PROPOSE, con, entry.getKey(), agent);
            entry.setValue(con);
        }

    }

    private void searchMarkets() {
        DFAgentDescription finded[] = helper.findMarkets(agent);

        for (DFAgentDescription df : finded) {
            contracts.put(df.getName(), null);
            System.out.println(agent.getName() + ": Find  " + df.getName());

        }
    }

}
