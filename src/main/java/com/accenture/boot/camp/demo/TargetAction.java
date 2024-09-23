package com.accenture.boot.camp.demo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TargetAction {
    protected CharacterSheet subject;
    protected CharacterSheet target;
    protected int damage;
    protected Boolean isAlive;

    public TargetAction(CharacterSheet subject, CharacterSheet target) {
        setSubject(subject);
        setTarget(target);
        setDamage(1);
        setIsAlive(true);
    }

    public boolean isSuccessful(int subjectRoll) {
        return subject.roll(subjectRoll) >= target.getArmorClass();
    }

    public int dealDamage(int roll) {

        if(roll == 20) {
            setDamage(damage * 2);
            target.setHitPoints(target.getHitPoints() - damage);
        } else if(isSuccessful(roll)) {
            target.setHitPoints(target.getHitPoints() - damage);
        }

        return target.getHitPoints();
    }
}
