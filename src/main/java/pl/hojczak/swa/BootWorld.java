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
import static pl.hojczak.swa.enums.Resources.Coal;

/**
 *
 * @author jhojczak
 */
public class BootWorld {

    public static void main(String args[]) {

        List<String> bootAgents = new ArrayList<>(0);
        System.out.println(String.format("(%s,%s,%s)", 100, 10, 20));
        bootAgents.add("-gui");
        bootAgents.add(Country.class.getSimpleName() + ":" + Country.class.getName() + String.format("(%s %s %s)", BehaviourProfile.Simple, 10000, Coal.name()));
        bootAgents.add(Market.class.getSimpleName() + ":" + Market.class.getName() + String.format("(%s %s)", BehaviourProfile.Market, 100000));
        String[] strarray = bootAgents.toArray(new String[0]);
        new Boot3(strarray);
    }
}
