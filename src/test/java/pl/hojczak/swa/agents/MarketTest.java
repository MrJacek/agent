/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.hojczak.swa.agents;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.hojczak.swa.enums.Resources;

/**
 *
 * @author jhojczak
 */
public class MarketTest {

    Market market;

    @BeforeMethod
    public void init() {
        market = new Market();
    }

    @Test
    public void shouldCalculatePriceReturnPricieForSingleResources() {
        market.addResource(Resources.Coal);
        Assert.assertEquals(market.calculatePricePeerUnit(Resources.Coal), Resources.Coal.startPrice - 1);

    }

    @Test
    public void shouldCalculatePriceReturnPricieForManyResources() {
        market.addResource(Resources.Coal);
        market.addResource(Resources.Coal);
        market.addResource(Resources.Coal);
        market.addResource(Resources.Coal);
        market.addResource(Resources.Coal);
        Assert.assertEquals(market.calculatePricePeerUnit(Resources.Coal), Resources.Coal.startPrice - 5);
    }

    @Test
    public void shouldReturnThatResourcesAreAvailable() {
        market.addResource(Resources.Gems);
        Assert.assertTrue(market.isAvailable(Resources.Gems));
    }

    @Test
    public void shouldReturnThatResourcesAreNotAvailable() {
        market.addResource(Resources.Gems);
        Assert.assertFalse(market.isAvailable(Resources.Coal));
    }
}
