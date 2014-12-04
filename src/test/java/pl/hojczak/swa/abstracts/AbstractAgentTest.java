/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.hojczak.swa.abstracts;

import jade.Boot3;
import java.util.ArrayList;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;
import pl.hojczak.swa.agents.Country;
import pl.hojczak.swa.agents.Market;
import pl.hojczak.swa.enums.BehaviourProfile;

/**
 *
 * @author jhojczak
 */
public class AbstractAgentTest {

    @Test
    public void shouldReturnExtendedClassName() {
        TestAgent agent = new TestAgent();
        Assert.assertEquals(agent.getType(), "TestAgent");

    }

    @Test
    public void shouldStartAgentWithOwnProfiles() {
        List<String> bootAgents = new ArrayList<>(0);
        System.out.println(String.format("(%s,%s,%s)",100,10,20));
        bootAgents.add("Duch:" + Country.class.getName() + String.format("(%s %s)",BehaviourProfile.Simple,1000));
        bootAgents.add("Europe:" + Market.class.getName() +String.format("(%s %s)",BehaviourProfile.Market,100));
        String[] strarray = bootAgents.toArray(new String[0]);
        new Boot3(strarray);
    }

}
