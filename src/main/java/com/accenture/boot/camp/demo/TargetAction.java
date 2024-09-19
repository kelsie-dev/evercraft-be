package com.accenture.boot.camp.demo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TargetAction {
    protected CharacterSheet subject;
    protected CharacterSheet target;

    public TargetAction(CharacterSheet subject, CharacterSheet target) {
        setSubject(subject);
        setTarget(target);
    }

    public boolean isSuccessful(int subjectRoll) {
        return subject.roll(subjectRoll) >= target.getArmorClass();
    }
}
