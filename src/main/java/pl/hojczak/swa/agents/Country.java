/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.hojczak.swa.agents;

import java.util.HashMap;
import java.util.Map;
import pl.hojczak.swa.abstracts.AbstractAgent;
import pl.hojczak.swa.enums.Resources;

/**
 *
 * @author jhojczak
 */
public class Country extends AbstractAgent {

    private static final long serialVersionUID = -2622338287143408788L;

    public Map<Resources, Integer> collects = new HashMap<>();

}
