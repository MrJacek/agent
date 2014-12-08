/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.hojczak.swa;

import java.util.Random;
import pl.hojczak.swa.enums.Resources;

/**
 *
 * @author jhojczak
 */
public class Goal {

    final public Resources res;
    final int count;

    public Goal(Resources res,int count) {
        this.res = res;
        this.count = count;
    }

    public boolean reachGoal(int have) {
        return have == count;
    }

    @Override
    public String toString() {
        return "Goal{" + "res=" + res + ", count=" + count + '}';
    }

 
}
