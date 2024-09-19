package com.accenture.boot.camp.demo;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class CharacterSheet {

    protected String name;
    protected String alignment;
    public CharacterSheet(
            String name,
            String alignment
    ) {
        setName(name);
        setAlignment(alignment);
    }



}
