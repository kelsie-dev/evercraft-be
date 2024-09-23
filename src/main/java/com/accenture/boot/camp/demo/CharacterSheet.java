package com.accenture.boot.camp.demo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CharacterSheet {

    protected String name;
    protected String alignment;
    protected int armorClass;
    protected int hitPoints;
    protected boolean alive;

    public CharacterSheet(
            String name,
            String alignment
    ) {
        setName(name);
        setAlignment(alignment);
        setArmorClass(10);
        setHitPoints(5);
        setAlive(true);
    }

    public void takeDamage(int damage) {
        setHitPoints(hitPoints - damage);
        if (hitPoints <=0) {
            setAlive(false);
        }
    }

    boolean getAlive() {
        return this.alive;
    }

    public int roll(int dieNumber) {
        if(dieNumber >= 1 && dieNumber <= 20) {
            return dieNumber;
        } else {
            return -1;
        }
    }

}
