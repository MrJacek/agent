package example;

import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import java.util.List;

public class AgentSeller extends Agent {

    private Codec codec = new SLCodec();
    private List<Staff> toSell;
    private int profitToMake;

    protected void setup() {
        System.out.println("Hello! Seller-agent " + getAID().getName() + " is ready.");
        Object[] args = getArguments();
        
        getContentManager().registerLanguage(codec);

        DFRegister("seller", this);
        BehaviourSeller behaviourSeller = new BehaviourSeller(this);
        addBehaviour(behaviourSeller);

    }

    protected void takeDown() {
        System.out.println("Buyer-agent " + getAID().getName() + " terminating.");
    }

    public static void DFRegister(String typAgenta, Agent agent) {
        DFAgentDescription template = new DFAgentDescription();
        template.setName(agent.getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType(typAgenta);
        sd.setName(agent.getName());
        template.addServices(sd);

        try {
            DFService.register(agent, template);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
    }
}
