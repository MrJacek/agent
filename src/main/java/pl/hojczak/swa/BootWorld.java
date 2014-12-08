/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.hojczak.swa;

import jade.Boot3;
import java.util.ArrayList;
import java.util.List;
import pl.hojczak.swa.agents.Country;
import pl.hojczak.swa.agents.Market;
import pl.hojczak.swa.enums.BehaviourProfile;
import pl.hojczak.swa.enums.Nation;
import static pl.hojczak.swa.enums.Resources.Gems;

/**
 *
 * @author jhojczak
 */
public class BootWorld {

    public static void main(String args[]) {

        List<String> bootAgents = new ArrayList<>(0);
        System.out.println(String.format("(%s,%s,%s)", 100, 10, 20));
        bootAgents.add("-gui");
        bootAgents.add(Nation.China+":" + Country.class.getName() + String.format("(%s %s %s)", BehaviourProfile.Simple, 1000000, Gems.name()+";45"));
        bootAgents.add(Nation.British+":" + Country.class.getName() + String.format("(%s %s %s)", BehaviourProfile.Simple, 1000000, Gems.name()+";45"));
        bootAgents.add(Nation.Egypt+":" + Country.class.getName() + String.format("(%s %s %s)", BehaviourProfile.Simple, 1000000, Gems.name()+";45"));
        bootAgents.add(Nation.Germany+":" + Country.class.getName() + String.format("(%s %s %s)", BehaviourProfile.Simple, 1000000, Gems.name()+";45"));
        bootAgents.add(Nation.Brazil+":" + Country.class.getName() + String.format("(%s %s %s)", BehaviourProfile.Simple, 1000000, Gems.name()+";45"));
        bootAgents.add(Nation.Poland+":" + Country.class.getName() + String.format("(%s %s %s)", BehaviourProfile.Smart, 1000000, Gems.name()+";45"));
        bootAgents.add("Europe:" + Market.class.getName() + String.format("(%s %s)", BehaviourProfile.Market,1));
        bootAgents.add("America:" + Market.class.getName() + String.format("(%s %s)", BehaviourProfile.Market, 100000));
        bootAgents.add("Asia:" + Market.class.getName() + String.format("(%s %s)", BehaviourProfile.Market, 100000));
        String[] strarray = bootAgents.toArray(new String[0]);
        new Boot3(strarray);
    }
}
