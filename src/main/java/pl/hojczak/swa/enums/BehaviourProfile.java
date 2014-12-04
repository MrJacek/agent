/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.hojczak.swa.enums;

import jade.core.behaviours.SimpleBehaviour;
import pl.hojczak.swa.agent.behaviour.SimpleBuyerBehaviour;
import pl.hojczak.swa.agent.behaviour.SimpleSellerBehaviour;
import pl.hojczak.swa.agent.behaviour.SmartBuyerBehaviour;
import pl.hojczak.swa.agent.behaviour.SmartSellerBehaviour;

/**
 *
 * @author jhojczak
 */
public enum BehaviourProfile {

    Simple(SimpleBuyerBehaviour.class, SimpleSellerBehaviour.class),
    Smart(SmartBuyerBehaviour.class, SmartSellerBehaviour.class),
    Market(pl.hojczak.swa.agent.behaviour.MarketBehaviour.class);
    Class<?>[] behaviour;

    BehaviourProfile(Class<?>... behaviour) {
        this.behaviour = behaviour;
    }

    public Class<?>[] getBehaviours() {
        return behaviour;
    }

}
