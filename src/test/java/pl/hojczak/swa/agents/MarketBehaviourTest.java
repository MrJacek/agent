/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.hojczak.swa.agents;

import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import java.io.IOException;
import java.util.HashMap;
import org.mockito.ArgumentCaptor;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.hojczak.swa.abstracts.BehaviourHelper;
import pl.hojczak.swa.abstracts.Contract;
import pl.hojczak.swa.agent.behaviour.MarketBehaviour;
import pl.hojczak.swa.enums.ContractType;
import pl.hojczak.swa.enums.Nation;
import pl.hojczak.swa.enums.Resources;

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

    AID aidReciver = new AID("reciver", true);
    AID aidSender = new AID("sender", true);
    Contract contract;
    ACLMessage example;

    @BeforeMethod
    public void init() {
        MockitoAnnotations.initMocks(this);
        assertNotNull(helper);
        assertNotNull(market);
        assertNotNull(simpleBehaviour);
        behaviour = new MarketBehaviour(market, helper);
        contract = new Contract();
        example = new ACLMessage(ACLMessage.PROPOSE);
        example.setSender(aidSender);
        doReturn(example).when(helper).waitForContract(market);
        doReturn(aidReciver).when(helper).getAID(market);
        doCallRealMethod().when(market).calculatePricePeerUnit(any(Resources.class));
        doCallRealMethod().when(market).addResource(any(Resources.class));
        doCallRealMethod().when(market).removeResource(any(Resources.class));
        doCallRealMethod().when(market).isAvailable(any(Resources.class));
        doCallRealMethod().when(market).quantity(any(Resources.class));
        doCallRealMethod().when(market).getCash();
        doCallRealMethod().when(market).addCash(anyInt());
        doCallRealMethod().when(market).removeCash(anyInt());

    }

    @Test
    public void shouldAcceptContract() throws IOException {
        //Given
        contract.type = ContractType.BUY;
        contract.counts = 1;
        contract.resource = Resources.Coal;
        contract.totalPrice = Resources.Coal.startPrice;
        example.setPerformative(ACLMessage.AGREE);
        example.setContentObject(contract);
        market.contracts = new HashMap<>();
        market.contracts.put(aidSender, contract);
        market.addResource(Resources.Coal);

        //When
        behaviour.action(simpleBehaviour);

        ArgumentCaptor<Contract> contractCaptor = ArgumentCaptor.forClass(Contract.class);
        verify(helper).waitForContract(market);
        verify(helper).sendMsg(Mockito.eq(ACLMessage.ACCEPT_PROPOSAL), contractCaptor.capture(), eq(aidSender), eq(market));
        assertEquals(contractCaptor.getValue().type, ContractType.BUY);
        assertEquals(market.getCash(), 1000);
    }

    @Test
    public void shouldAnswerNoUnderstodFormNotCompleteContract() throws IOException {
        //Given
        contract.type = ContractType.BUY;
        example.setContentObject(contract);

        //When
        behaviour.action(simpleBehaviour);

        ArgumentCaptor<Contract> contractCaptor = ArgumentCaptor.forClass(Contract.class);
        verify(helper).waitForContract(market);
        verify(helper).sendMsg(Mockito.eq(ACLMessage.NOT_UNDERSTOOD), contractCaptor.capture(), eq(aidSender), eq(market));
        assertEquals(contractCaptor.getValue().type, ContractType.BUY);
    }

    @Test
    public void shouldReturnTotalPriceContract() throws IOException {
        //Given
        contract.type = ContractType.BUY;
        contract.counts = 1;
        contract.resource = Resources.Coal;
        example.setContentObject(contract);
        market.addResource(Resources.Coal);

        //When
        behaviour.action(simpleBehaviour);
        //Then
        ArgumentCaptor<Contract> contractCaptor = ArgumentCaptor.forClass(Contract.class);

        verify(helper).sendMsg(anyInt(), contractCaptor.capture(), eq(aidSender), eq(market));
        assertEquals(contractCaptor.getValue().type, ContractType.BUY);
    }

    @Test
    public void shouldAnswerInformForContractProposal() throws IOException {
        //Given
        contract.type = ContractType.BUY;
        contract.counts = 1;
        contract.resource = Resources.Coal;
        example.setContentObject(contract);
        market.addResource(Resources.Coal);

        //When
        behaviour.action(simpleBehaviour);
        //Then
        ArgumentCaptor<Contract> contractCaptor = ArgumentCaptor.forClass(Contract.class);

        verify(helper).sendMsg(Mockito.eq(ACLMessage.INFORM), contractCaptor.capture(), eq(aidSender), eq(market));
        assertEquals(contractCaptor.getValue().type, ContractType.BUY);

    }

    @Test
    public void shouldAnswerRejectProposal() throws IOException {
        //Given
        contract.type = ContractType.BUY;
        contract.counts = 1;
        contract.resource = Resources.Gems;
        example.setContentObject(contract);
        market.resourcesCollection = new int[Resources.values().length];
        market.resourcesCollection[Resources.Gems.ordinal()] = 0;

        //When
        behaviour.action(simpleBehaviour);
        //Then
        ArgumentCaptor<Contract> contractCaptor = ArgumentCaptor.forClass(Contract.class);

        verify(helper).sendMsg(Mockito.eq(ACLMessage.REJECT_PROPOSAL), contractCaptor.capture(), eq(aidSender), eq(market));

    }
}
