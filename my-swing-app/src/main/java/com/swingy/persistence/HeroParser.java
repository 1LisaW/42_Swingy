package com.example.swingy.persistence;

import com.example.swingy.model.hero.Hero;
import com.example.swingy.model.hero.HeroBuilder;
import com.example.swingy.model.hero.HeroDirector;

public class HeroParser {
    private HeroDirector heroDirector;

    public HeroParser(HeroBuilder heroBuilder) {
        this.heroDirector = new HeroDirector(heroBuilder);
    }

    public Hero parseStringToHeroData(String heroData) {
        String[] parts = heroData.split(" ");
        if (parts[1].equals("hero"))
{
            return parseHero(parts);
        } else if (parts[1].equals("artifact"))
        {
            // Implement villain parsing logic here
            return null; // Placeholder for villain parsing
        }
        else
        {
            throw new IllegalArgumentException("Invalid hero data format");
        }
    }

    public Hero parseHero(String[] parts) {
        if (parts.length != 7) {
            throw new IllegalArgumentException("Invalid hero data format");
        }

        String name = parts[0].trim();
        String archetype = parts[1].trim();
        int level = Integer.parseInt(parts[2].trim());
        int experience = Integer.parseInt(parts[3].trim());
        int hitPoints = Integer.parseInt(parts[4].trim());
        int attack = Integer.parseInt(parts[5].trim());
        int defense = Integer.parseInt(parts[6].trim());

        return heroDirector.constructHeroFromRepo(name, archetype, level, experience, hitPoints, attack, defense);
    }
}