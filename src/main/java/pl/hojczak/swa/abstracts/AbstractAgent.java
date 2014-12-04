/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.hojczak.swa.abstracts;

import jade.content.lang.sl.SLCodec;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import java.lang.reflect.InvocationTargetException;
import pl.hojczak.swa.agent.behaviour.BaseBehaviour;
import pl.hojczak.swa.agent.behaviour.MyBehaviour;
import pl.hojczak.swa.enums.BehaviourProfile;
import pl.hojczak.swa.annotations.CountryBehaviour;
import pl.hojczak.swa.annotations.MarketBehaviour;

/**
 *
 * @author jhojczak
 */
public abstract class AbstractAgent extends Agent {

    protected BehaviourProfile profile;
    protected int cash;

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
        addBehaviours(profile);

        cash = Integer.parseInt(arg[1].toString());

    }

    private void addBehaviours(BehaviourProfile profile) {
        for (Class<?> behaviour : profile.getBehaviours()) {
            if (behaviour.isAnnotationPresent(CountryBehaviour.class)) {
                try {
                    Object obj = behaviour.getConstructor(this.getClass(),BehaviourHelper.class).newInstance(this,new BehaviourHelper());
                    System.out.println("Add " + obj.getClass().getSimpleName());
                    MyBehaviour bebe = MyBehaviour.class.cast(obj);
                    
                    addBehaviour(new BaseBehaviour(bebe,this));
                } catch (NoSuchMethodException | SecurityException | InstantiationException |
                        IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    throw new IllegalStateException("Behaviour initalization problem", ex);
                }
            }
            if (behaviour.isAnnotationPresent(MarketBehaviour.class)) {
                try {
                    Object obj = behaviour.getConstructor(this.getClass(),BehaviourHelper.class).newInstance(this,new BehaviourHelper());
                    System.out.println("Add " + obj.getClass().getSimpleName());
                    MyBehaviour bebe = MyBehaviour.class.cast(obj);
                    
                    addBehaviour(new BaseBehaviour(bebe,this));
                } catch (NoSuchMethodException | SecurityException | InstantiationException |
                        IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    throw new IllegalStateException("Behaviour initalization problem", ex);
                }
            }
        }
    }


    @Override
    protected void takeDown() {
        System.out.println("Agent " + getAID().getName() + " kończy prace.");
    }

    private void DFRegister(String typAgenta, Agent agent) {
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
