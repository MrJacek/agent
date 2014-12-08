/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.hojczak.swa.enums;

import pl.hojczak.swa.agent.behaviour.SimpleBuyerBehaviour;
import pl.hojczak.swa.agent.behaviour.SmartBuyerBehaviour;

/**
 *
 * @author jhojczak
 */
public enum BehaviourProfile {

    Simple(SimpleBuyerBehaviour.class),
    Smart(SmartBuyerBehaviour.class),
    Market(pl.hojczak.swa.agent.behaviour.MarketBehaviour.class);
    Class<?>[] behaviour;

    BehaviourProfile(Class<?>... behaviour) {
        this.behaviour = behaviour;
    }

    public Class<?>[] getBehaviours() {
        return behaviour;
    }

}
