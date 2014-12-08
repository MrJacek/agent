/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.hojczak.swa.agents;

import jade.core.AID;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import pl.hojczak.swa.abstracts.AbstractAgent;
import pl.hojczak.swa.abstracts.Contract;
import pl.hojczak.swa.enums.Resources;

/**
 *
 * @author jhojczak
 */
public class Market extends AbstractAgent {

    private static final long serialVersionUID = 1L;

    int[] resourcesCollection;

    public Map<AID, Contract> contracts;

    private void init() {
        if (resourcesCollection == null) {

            resourcesCollection = new int[Resources.values().length];

            for (int i = 0; i < Resources.values().length; i++) {
                resourcesCollection[i] = 100000;
            }
        }
        if (contracts == null) {
            this.contracts = new HashMap<>();
        }
    }

    public Market() {

    }

    public void addResource(Resources resources) {
        init();
        resourcesCollection[resources.ordinal()] += 1;
    }

    public void removeResource(Resources resources) {
        init();
        resourcesCollection[resources.ordinal()] -= 1;
    }

    public int calculatePricePeerUnit(Resources resources) {
        init();
        return resources.startPrice - ((resources.startPrice) * (resourcesCollection[resources.ordinal()]) / resources.stability);
    }

    public boolean isAvailable(Resources resources) {
        init();
        return resourcesCollection[resources.ordinal()] > 0;
    }

    public int quantity(Resources resources) {
        init();
        return resourcesCollection[resources.ordinal()];
    }
}
