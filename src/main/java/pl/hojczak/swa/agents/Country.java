/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.hojczak.swa.agents;

import pl.hojczak.swa.abstracts.AbstractAgent;
import pl.hojczak.swa.enums.Resources;

/**
 *
 * @author jhojczak
 */
public class Country extends AbstractAgent {

    private static final long serialVersionUID = -2622338287143408788L;

    public int[] collects = new int[Resources.values().length];
    public int cancelContractCount = 0;

    public String printState() {
        StringBuilder builder = new StringBuilder(this.getLocalName());
        builder.append(": {");
        for (Resources res : Resources.values()) {
            builder.append(res.name()).append("=").append(collects[res.ordinal()]).append(" ");
        }
        builder.append("cash=").append(this.cash).append("}");
        return builder.toString();
    }

}
