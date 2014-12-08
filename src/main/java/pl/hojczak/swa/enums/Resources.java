/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.hojczak.swa.enums;

/**
 *
 * @author jhojczak
 */
public enum Resources {

    Coal(1000, 100),
    Gems(3500, 10),
    Iron(1500, 10),
    Gold(2500, 100),
    Silver(2000, 100),
    Aluminum(1500, 100);
    
    public final int startPrice;
    public final int stability;

    private Resources(int price, int stabilityParam) {
        startPrice = price;
        stability = stabilityParam;
    }

}
