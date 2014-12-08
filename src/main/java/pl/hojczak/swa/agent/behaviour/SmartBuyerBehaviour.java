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
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import pl.hojczak.swa.Goal;
import pl.hojczak.swa.abstracts.BehaviourHelper;
import pl.hojczak.swa.abstracts.Contract;
import pl.hojczak.swa.agents.Country;
import pl.hojczak.swa.annotations.CountryBehaviour;
import pl.hojczak.swa.enums.ContractType;

/**
 *
 * @author jhojczak
 */
@CountryBehaviour
public class SmartBuyerBehaviour implements MyBehaviour {

    private static final long serialVersionUID = -7298935055403075376L;

    Country agent;
    BehaviourHelper helper;
    Map<AID, Contract> contracts;
    State currentState;
    AID bestOffert;

    enum State {

        SearchMarkets,
        CheckOfferts,
        WaitForAnswer,
        ComputeOffert,
        ComplateContract;

    }

    public SmartBuyerBehaviour(Country a, BehaviourHelper helper) {
        this.agent = a;
        this.helper = helper;
        currentState = State.SearchMarkets;
        contracts = new HashMap<>();
    }

    @Override
    public boolean done() {
        boolean result = false;
        for (Goal g : agent.goals) {
            System.out.println(agent.getName() + ": Goal " + g + " current: " + agent.collects[g.res.ordinal()] + " cash: " + agent.getCash());
            result |= g.reachGoal(agent.collects[g.res.ordinal()]);
        }
        result |= agent.cancelContractCount > 5;
        if (result) {
            try {
                wirteResult(agent);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(SmartBuyerBehaviour.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(SmartBuyerBehaviour.class.getName()).log(Level.SEVERE, null, ex);
            }
            agent.doDelete();
        }
        return result;
    }

    @Override
    public void action(SimpleBehaviour beh) {
        switch (currentState) {
            case SearchMarkets:
                System.out.println(agent.getName() + ": Start looking markets");
                searchMarkets();

                return;
            case CheckOfferts:
                System.out.println(agent.getName() + ": Check offerts markets");
                sendOfferts();
                System.out.println(agent.getName() + ": Wait for market answer");

                break;
            case ComputeOffert:

                Contract con = contracts.get(bestOffert);
                System.out.println(agent.getName() + ": Recive contract: " + con);
                if (con.totalPrice < agent.getCash()) {
                    helper.sendMsg(ACLMessage.AGREE, con, bestOffert, agent);

                } else {
                    System.out.println(agent.getName() + ": Not enough money for contract: " + con.toString());
                    agent.cancelContractCount += 1;
                    currentState = State.CheckOfferts;
                    helper.sendMsg(ACLMessage.CANCEL, con, bestOffert, agent);
                    return;
                }
                currentState = State.ComplateContract;
                break;
            case ComplateContract:
                System.out.println(agent.getName() + ": Wait for complete contract:");
                ACLMessage msg = helper.waitForContract(agent);

                if (ACLMessage.ACCEPT_PROPOSAL == msg.getPerformative()) {
                    con = helper.getContract(msg);
                    System.out.println(agent.getName() + ": Complete contract:\n" + con.toString());
                    agent.removeCash(con.totalPrice);

                    agent.collects[con.resource.ordinal()] += con.counts;
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
            ACLMessage msg = helper.waitForContract(agent);
            con = helper.getContract(msg);
            entry.setValue(con);
        }
        int cash = Integer.MAX_VALUE;
        for (Map.Entry<AID, Contract> entry : contracts.entrySet()) {
            if (entry.getValue().totalPrice < cash) {
                cash = entry.getValue().totalPrice;
                bestOffert = entry.getKey();
            }
        }
        currentState = State.ComputeOffert;

    }

    private void searchMarkets() {
        DFAgentDescription finded[] = helper.findMarkets(agent);
        for (DFAgentDescription df : finded) {
            contracts.put(df.getName(), null);
            System.out.println(agent.getName() + ": Find  " + df.getName());

        }
        if (contracts.size() > 0) {
            currentState = State.CheckOfferts;
        }
    }

}
