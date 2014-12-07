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
public class Goal {

    final public Resources res;

    public Goal(Resources res) {
        this.res = res;
    }

    @Override
    public String toString() {
        return "Goal{" + "res=" + res + '}';
    }
}
