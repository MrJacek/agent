package example;

import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import java.util.List;

public class AgentBuyer extends Agent {

    private Codec codec = new SLCodec();

    private List<Staff> needToBuy;
    private int budget;

    @Override
    protected void setup() {
        System.out.println("Hello! Buyer-agent " + getAID().getName() + " is ready.");
        Object[] args = getArguments();
        getContentManager().registerLanguage(codec);

        DFRegister("buyer", this);
        BehaviourBuyer behaviourBuyer = new BehaviourBuyer(this);
        addBehaviour(behaviourBuyer);

        if (args != null && args.length > 0) {
            needToBuy = (List<Staff>) args[0];
            budget = (int) args[1];
            System.out.println("Trying to buy " + needToBuy.toString());
        } else {
            System.out.println("No book title specified");
            doDelete();
        }
    }

    @Override
    protected void takeDown() {
        System.out.println("Buyer-agent " + getAID().getName() + " terminating.");
    }

    public List<Staff> getStaffToBy() {
        return needToBuy;
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
