/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.hojczak.swa.abstracts;

import jade.content.lang.sl.SLCodec;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import pl.hojczak.swa.agents.Country;
import pl.hojczak.swa.agents.Market;
import pl.hojczak.swa.enums.ContractType;

/**
 *
 * @author jhojczak
 */
public class BehaviourHelper {

    public AID getAID(Agent a) {
        return a.getAID();
    }

    public DFAgentDescription[] findMarkets(Agent a) {
        return findAgent(Market.class.getSimpleName(), a);
    }

    public DFAgentDescription[] findCountry(Agent a) {
        return findAgent(Country.class.getSimpleName(), a);
    }

    public void sendMsg(int type, Contract contract, AID reciver, Agent sender) {
        try {
            ACLMessage msg = new ACLMessage(type);
            msg.setLanguage(new SLCodec().getName());
            msg.setContentObject(contract);
            sender.send(msg);
        } catch (IOException ex) {
            throw new IllegalStateException("Problem with sending contract", ex);
        }
    }

    private DFAgentDescription[] findAgent(String type, Agent agent) {
        DFAgentDescription template = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType(type);
//        sd.setName(type);
        template.addServices(sd);
        DFAgentDescription[] result = new DFAgentDescription[0];
        try {
            result = DFService.search(agent, template);

        } catch (FIPAException fe) {
            throw new IllegalStateException("Problem with search markets agents");
        }
        return result;
    }

    public MessageTemplate.MatchExpression buyMessage() {
        return new MessageTemplate.MatchExpression() {
            private static final long serialVersionUID = -5666929549657574052L;

            @Override
            public boolean match(ACLMessage aclm) {
                try {
                    Contract contract = Contract.class.cast(aclm.getContentObject());
                    return ContractType.BUY == contract.type;
                } catch (UnreadableException ex) {
                    throw new IllegalStateException("Problem with filtring ACLMessage", ex);
                }
            }
        };
    }

    public MessageTemplate.MatchExpression contract() {
        return new MessageTemplate.MatchExpression() {
            private static final long serialVersionUID = -5666929549657574052L;

            @Override
            public boolean match(ACLMessage aclm) {
                try {
                    Contract contract = Contract.class.cast(aclm.getContentObject());
                    return contract != null;
                } catch (UnreadableException ex) {
                    throw new IllegalStateException("Problem with filtring ACLMessage", ex);
                }
            }
        };
    }

    public MessageTemplate sellMessage() {
        return new MessageTemplate(
                (ACLMessage aclm) -> {
                    try {
                        Contract contract = Contract.class.cast(aclm.getContentObject());
                        return ContractType.SELL == contract.type;
                    } catch (UnreadableException ex) {
                        throw new IllegalStateException("Problem with filtring ACLMessage", ex);
                    }
                });

    }

    public Contract getContract(ACLMessage msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public ACLMessage waitForMessage(Agent agent) {
        try {
            Thread.sleep(1000);
            return agent.receive();
        } catch (InterruptedException ex) {
            throw new IllegalStateException(ex);
        }
    }

    public ACLMessage waitForContract(Agent agent) {
        ACLMessage msg = agent.blockingReceive(new MessageTemplate(contract()), 1000);
        return msg;
    }
}
