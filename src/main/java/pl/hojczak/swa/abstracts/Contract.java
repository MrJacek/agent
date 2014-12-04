/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.hojczak.swa.abstracts;

import pl.hojczak.swa.enums.ContractType;
import java.io.Serializable;
import pl.hojczak.swa.enums.Resources;

/**
 *
 * @author jhojczak
 */
public class Contract implements Serializable {

    public ContractType type;
    public Resources resource;
    public int counts;
    public int totalPrice;

}
