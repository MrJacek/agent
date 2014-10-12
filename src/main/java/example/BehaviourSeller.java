package example;

import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;

public class BehaviourSeller extends SimpleBehaviour {

    AgentSeller agent;

    public BehaviourSeller(AgentSeller agentSeller) {
        super(agentSeller);
        agent = (AgentSeller) myAgent;
    }

    @Override
    public void action() {
        ACLMessage msg = agent.receive();
        if (msg != null) {
            ACLMessage response = new ACLMessage(ACLMessage.INFORM);
            response.addReceiver(msg.getSender());
            response.setContent("OK");
            agent.send(response);
        } else {
            block();
        }
    }

    @Override
    public boolean done() {
        return false;
    }

}
