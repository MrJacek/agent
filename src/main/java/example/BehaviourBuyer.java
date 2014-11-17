package example;

import example.AgentBuyer;
import jade.content.lang.sl.SLCodec;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.util.Iterator;

public class BehaviourBuyer extends SimpleBehaviour {

    private enum State{
        FIND_SELLER,
        ASK_FOR_STAFF ,
        BUY_STAFF ,
        END
    }
   
    
    public interface Action{
        public void perfom(Object... args);
    }
    private State step = State.FIND_SELLER;

    AgentBuyer agent;
    AID seller;
    Iterator<Staff> currentStaff;

    public BehaviourBuyer(Agent a) {
        super(a);
        agent = (AgentBuyer) myAgent;
        currentStaff = agent.getStaffToBy().iterator();
    }

    @Override
    public void action() {
        switch (step) {
            case FIND_SELLER:
                seller = findSeller();
                if (null != seller) {
                    step=State.ASK_FOR_STAFF;
                } else {
                    step = State.END;
                }
                break;
            case ASK_FOR_STAFF:
                askForStaff();
                if (getMsgAboutBook() == true) {
                    step=State.BUY_STAFF;
                } else {
                    step=State.END;
                }
                break;
            case BUY_STAFF:
                buyStaff();
                step=State.END;
                break;
            case END:
                deregister();
                step = State.FIND_SELLER;
                break;
        }
    }

    private boolean getMsgAboutBook() {
        String reply = receiveMsg(ACLMessage.INFORM, seller, 1000);
        if (reply.equals("OK")) {
            return true;
        } else {
            return false;
        }
    }

    private void askForStaff() {
         System.out.println("Asking about staff!");
        sendMsg(ACLMessage.REQUEST,currentStaff.next().getName());
    }

    private void buyStaff() {
        currentStaff.remove();
        System.out.println("Buy a staff!");

    }

    private void deregister() {
        myAgent.removeBehaviour(this);
    }

    private String receiveMsg(int msgType, AID fromAgent, long time) {
        String replyMsg = null;
        MessageTemplate mt = MessageTemplate.and(
                MessageTemplate.MatchPerformative(msgType),
                MessageTemplate.MatchSender(fromAgent));

        ACLMessage reply = myAgent.blockingReceive(mt, time);

        if (reply != null) {
            replyMsg = reply.getContent();

        }
        return replyMsg;
    }

    private AID findSeller() {
        DFAgentDescription template = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType("seller");
        template.addServices(sd);
        try {
            DFAgentDescription[] result = DFService.search(myAgent, template);

            if (result.length > 0) {
                seller = result[0].getName();
            }
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
        return seller;
    }

    void sendMsg(int msgType, String content) {
        if (seller == null) {
            System.out.println("Find seller!");
            return;
        }
        ACLMessage msg = new ACLMessage(msgType);
        msg.setLanguage((new SLCodec()).getName());

        try {
            msg.setContent(content);
            msg.addReceiver(seller);
            myAgent.send(msg);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean done() {
        System.out.println("Done");
        return false;
    }

}
