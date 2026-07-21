package com.swingy.persistence;

import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

import com.swingy.model.Hero;
import com.swingy.model.HeroBuilder;
import com.swingy.model.HeroDirector;
import com.swingy.model.ArtifactTier;
import com.swingy.model.Artifact;
import com.swingy.model.ArtifactFactory;

public class HeroParser {
    private HeroDirector heroDirector;
    private Map<String, Hero> heroMap = new HashMap();

    public HeroParser(HeroBuilder heroBuilder) {
        this.heroDirector = new HeroDirector(heroBuilder);
    }

    public List<Hero> getHeroes() {
        return heroMap != null ? new ArrayList<>(heroMap.values()) : new ArrayList<>();
    }

    public void parseStringToHeroData(String heroData) {
        String[] parts = heroData.split("[|]");
        if (parts.length < 2) {
            throw new IllegalArgumentException("Invalid hero data format");
        }
        String heroId = parts[0].trim();
        String dataType = parts[1].trim();
        String[] dataParts = Arrays.copyOfRange(parts, 2, parts.length);
        if (dataType.equals("hero"))
        {
            Hero hero = parseHero(dataParts);
            if (hero != null ) {
                heroMap.put(heroId, hero);
                System.out.println("HERO "+ heroId);
            }
        } else if (dataType.equals("artifact"))
        {
            Artifact artifact = parseArtifact(dataParts);
            if (artifact != null && heroMap != null && heroMap.containsKey(heroId)) {
                heroMap.get(heroId).addArtifact(artifact);
            }
        }
        else
        {
            throw new IllegalArgumentException("Invalid hero data format");
        }
    }

    // name archetype level experience hitPoints attack defense
    private Hero parseHero(String[] parts) {
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

    // name artifactType tier hitPoints attack defense
    private Artifact parseArtifact(String[] parts) {
        if (parts.length != 6) {
            throw new IllegalArgumentException("Invalid artifact data format");
        }

        String name = parts[0].trim();
        String artifactType = parts[1].trim();
        ArtifactTier tier = ArtifactTier.valueOf(parts[2].trim().toUpperCase());
        int hitPoints = Integer.parseInt(parts[3].trim());
        int attack = Integer.parseInt(parts[4].trim());
        int defense = Integer.parseInt(parts[5].trim());

        // add Factory method to create artifact based on type
        return ArtifactFactory.getInstance().createArtifactFromRepo(name, artifactType, tier, attack, defense, hitPoints);
    }

}
