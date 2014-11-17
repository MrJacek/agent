/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package example;

/**
 *
 * @author jhojczak
 */
class Staff {
    private String Name;
    private int price;

    public Staff(String Name, int price) {
        this.Name = Name;
        this.price = price;
    }

    
    public String getName() {
        return Name;
    }

    public int getPrice() {
        return price;
    }
    
}
