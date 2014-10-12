/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.hojczak.sw.agent;

/**
 *
 * @author hojczak
 */
public class Hit {
    private final int damage;

    public Hit(int damage) {
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }
    
}
