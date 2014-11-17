/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.hojczak.swa.abstracts;

import jade.content.lang.sl.SLCodec;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import java.util.List;

/**
 *
 * @author jhojczak
 */
public abstract class AbstractAgent extends Agent {

    String getType() {
        return this.getClass().getSimpleName();
    }

    protected abstract List<SimpleBehaviour> getBehaviors();

    @Override
    protected void setup() {
        System.out.println("Rejestracja!" + getType() + " " + getAID().getName() + " gotowy do pracy.");
        getContentManager().registerLanguage(new SLCodec());

        DFRegister(getType(), this);

        getBehaviors().stream().forEach((beh) -> {
            addBehaviour(beh);
        });

    }

    @Override
    protected void takeDown() {
        System.out.println("Agent " + getAID().getName() + " kończy prace.");
    }

    public static void DFRegister(String typAgenta, Agent agent) {
        DFAgentDescription template = new DFAgentDescription();
        template.setName(agent.getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType(typAgenta);
        sd.setName(agent.getName());
        template.addServices(sd);
        try {
            DFService.register(agent, template);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
    }
}
