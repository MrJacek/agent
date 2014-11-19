/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.hojczak.swa.abstracts;

import jade.content.lang.sl.SLCodec;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pl.hojczak.swa.agent.behaviour.SimpleBuyerBehaviour;
import pl.hojczak.swa.agents.CountryAgent;
import pl.hojczak.swa.enums.BehaviourProfile;
import pl.hojczak.swa.enums.CountryBehaviour;

/**
 *
 * @author jhojczak
 */
public abstract class AbstractAgent extends Agent {

    protected BehaviourProfile profile;

    String getType() {
        return this.getClass().getSimpleName();
    }

    @Override
    protected void setup() {
        System.out.println("Rejestracja! " + getType() + ":" + getAID().getName() + " gotowy do pracy.");
        getContentManager().registerLanguage(new SLCodec());

        DFRegister(getType(), this);

        Object[] arg = getArguments();
        profile = BehaviourProfile.valueOf(arg[0].toString());
        for (Class<?> behaviour : profile.getBehaviours()) {
            if (behaviour.isAnnotationPresent(CountryBehaviour.class)) {
                try {
                    Object obj=behaviour.getConstructor(this.getClass()).newInstance(this);
                    System.out.println("Add "+obj.getClass().getSimpleName());
                    Behaviour bebe = Behaviour.class.cast(obj);
                    addBehaviour(bebe);
                } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    Logger.getLogger(AbstractAgent.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

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
