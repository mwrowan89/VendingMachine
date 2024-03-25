package org.example;

public class Duck extends Items{
    
    public Duck(String name, double cost) {
        super(name, cost);
        setMessage("Quack, quack, splash!");
    }
}