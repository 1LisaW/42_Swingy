package com.swingy.model;

public class HeroDirector {

    private HeroBuilder heroBuilder;

    public HeroDirector(HeroBuilder heroBuilder) {
        this.heroBuilder = heroBuilder;
    }

    private Hero constructNewWizard(String name) {
        return heroBuilder.setName(name)
                .setArchetype("wizard")
                .setLevel(1)
                .setExperience(0)
                .setHitPoints(5)
                .setAttack(5)
                .setDefense(0)
                .build();
    }

    private Hero constructNewWarrior(String name) {
        return heroBuilder.setName(name)
                .setArchetype("warrior")
                .setLevel(1)
                .setExperience(0)
                .setHitPoints(4)
                .setAttack(3)
                .setDefense(3)
                .build();
    }

    private Hero constructNewBarbarian(String name) {
        return heroBuilder.setName(name)
                .setArchetype("barbarian")
                .setLevel(1)
                .setExperience(0)
                .setHitPoints(4)
                .setAttack(6)
                .setDefense(0)
                .build();
    }

    public Hero constructNewHero(String name, String archetype) {
        switch (archetype.toLowerCase()) {
            case "wizard":
                return constructNewWizard(name);
            case "warrior":
                return constructNewWarrior(name);
            case "barbarian":
                return constructNewBarbarian(name);
            default:
                throw new IllegalArgumentException("Invalid archetype: " + archetype);
        }
    }

    public Hero constructHeroFromRepo(String name, String archetype, int level, int experience, int hitPoints, int attack, int defense) {
        return heroBuilder.setName(name)
                .setArchetype(archetype)
                .setLevel(level)
                .setExperience(experience)
                .setHitPoints(hitPoints)
                .setAttack(attack)
                .setDefense(defense)
                .build();
    }
}
