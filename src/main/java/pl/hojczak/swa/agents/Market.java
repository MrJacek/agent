/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.hojczak.swa.agents;

import pl.hojczak.swa.enums.Resources;

/**
 *
 * @author jhojczak
 */
public class Market {

    int[] resourcesCollection = new int[Resources.values().length];

    public void addResource(Resources resources) {
        resourcesCollection[resources.ordinal()] += 1;

    }

    public int calculatePricePeerUnit(Resources resources) {
        return resources.startPrice - ((resources.startPrice) * (resourcesCollection[resources.ordinal()]) / resources.stability);
    }

    public boolean isAvailable(Resources resources) {
        return resourcesCollection[resources.ordinal()] > 0;
    }

}
