/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.hojczak.swa.agents;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import java.io.IOException;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.testng.Assert;
import static org.testng.Assert.assertNotNull;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.hojczak.swa.abstracts.BehaviourHelper;
import pl.hojczak.swa.abstracts.Contract;
import pl.hojczak.swa.agent.behaviour.MarketBehaviour;
import pl.hojczak.swa.enums.ContractType;

/**
 *
 * @author jhojczak
 */
public class MarketBehaviourTest {

    @Mock
    Market market;

    @Mock
    BehaviourHelper helper;

    @Mock
    SimpleBehaviour simpleBehaviour;

    MarketBehaviour behaviour;

    ACLMessage example = new ACLMessage(ACLMessage.PROPOSE);
    AID aidReciver = new AID("reciver", true);
    AID aidSender = new AID("sender", true);

    @BeforeMethod
    public void init() {
        MockitoAnnotations.initMocks(this);
        assertNotNull(helper);
        assertNotNull(market);
        assertNotNull(simpleBehaviour);
        behaviour = new MarketBehaviour(market, helper);
    }

    @Test
    public void shouldAcceptContract() throws IOException {
        Contract contract = new Contract();
        contract.type = ContractType.BUY;
        example.setSender(aidSender);
        example.setContentObject(contract);
        doReturn(example).when(helper).waitForBuyContract(market);
        doReturn(aidReciver).when(helper).getAID(market);
        behaviour.action(simpleBehaviour);

        ArgumentCaptor<Contract> contractCaptor = ArgumentCaptor.forClass(Contract.class);
        verify(helper).waitForBuyContract(market);
        verify(helper).sendMsg(Mockito.eq(ACLMessage.ACCEPT_PROPOSAL), contractCaptor.capture(),eq(aidSender), eq(market));
        Assert.assertEquals(ContractType.BUY, contractCaptor.getValue().type);

    }
}
