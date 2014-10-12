package example;

import java.util.ArrayList;
import java.util.List;
import jade.Boot3;

public class BootAgents {

    public static void main(String args[]) {

        List<String> bootAgents = new ArrayList<>(0);
        bootAgents.add("-gui");
        bootAgents.add("Seller1:"+AgentSeller.class.getName());
        bootAgents.add("Buyer1:"+AgentSeller.class.getName()+"(Hobbit)");


        String[] strarray = bootAgents.toArray(new String[0]);
        new Boot3(strarray);
    }
}
