/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.hojczak.sw.agent;

import pl.hojczak.sw.agent.utils.Utils;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.core.Agent;
import pl.hojczak.sw.agent.behaviour.BehaviourTrooper;
import pl.hojczak.sw.agent.utils.Team;

/**
 *
 * @author hojczak
 */
public class AgentTrooper extends Unit {

    private Team team;
    private Codec codec = new SLCodec();


    protected void setup() {
       
        System.out.println("Hello! Buyer-agent " + getAID().getName() + " is ready.");
        Object[] args = getArguments();
        getContentManager().registerLanguage(codec);

        Utils.DFRegister("buyer", this);
        addBehaviour(new BehaviourTrooper());
       

//        if (args != null && args.length > 0) {
//            targetBookTitle = (String) args[0];
//            System.out.println("Trying to buy " + targetBookTitle);
//        } else {
//            System.out.println("No book title specified");
//            doDelete();
//        }
    }

    protected void takeDown() {
        System.out.println("Buyer-agent " + getAID().getName() + " terminating.");
    }

    public String getTargetBookTitle() {
        return "";
    }
}
