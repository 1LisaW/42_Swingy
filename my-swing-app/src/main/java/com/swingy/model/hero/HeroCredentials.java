package com.swingy.model;

import com.swingy.model.HeroArchetype;

public class HeroCredentials {
    private String name;
    private HeroArchetype heroType;

    public HeroCredentials() {}

    public void setName(String name) {
        this.name = name;
    }

    public void setHeroArchetype(String archetype) {
        switch(archetype.toLowerCase()) {
            case "wizard":
                this.heroType = HeroArchetype.WIZARD;
                break;
            case "warrior":
                this.heroType = HeroArchetype.WARRIOR;
                break;
            case "barbarian":
                this.heroType = HeroArchetype.BARBARIAN;
                break;

        }
    }
    public String getName() {
        return this.name;
    }

    public HeroArchetype getHeroType() {
        return this.heroType;
    }

    public boolean isNameValid(String name) {
        if (name.trim().isEmpty())
            return false;
        return true;
    }
}
