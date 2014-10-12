/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.hojczak.sw.agent;

import jade.core.Agent;


/**
 *
 * @author hojczak
 */
public abstract class Unit extends Agent {
    
    private int life=10;
    public void getHit(Hit hit){
        life-=hit.getDamage();
    }
    public boolean isDead(){
        return life<=0;
    }
}
