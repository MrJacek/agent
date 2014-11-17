/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.hojczak.swa.abstracts;

import jade.core.behaviours.SimpleBehaviour;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;

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

    private class TestAgent extends AbstractAgent {

        @Override
        protected List<SimpleBehaviour> getBehaviors() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    };
}
