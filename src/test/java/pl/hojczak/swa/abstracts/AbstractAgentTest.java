/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.hojczak.swa.abstracts;

import example.AgentSeller;
import jade.Boot3;
import jade.core.behaviours.SimpleBehaviour;
import java.util.ArrayList;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;
import pl.hojczak.swa.agents.CountryAgent;
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
    public void shouldStartAgentWithSmartProfile() {

        List<String> bootAgents = new ArrayList<>(0);
        bootAgents.add("TestSmart:" + CountryAgent.class.getName() + "(" + BehaviourProfile.Simple + ")");
        String[] strarray = bootAgents.toArray(new String[0]);
        new Boot3(strarray);

    }

}
