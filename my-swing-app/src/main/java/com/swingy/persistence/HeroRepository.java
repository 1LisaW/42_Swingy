package com.swingy.persistence;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import com.swingy.model.Hero;
import com.swingy.model.HeroBuilder;
import com.swingy.persistence.HeroParser;


public class HeroRepository {
    private HeroParser heroParser;
    private List<Hero> heroes;
    private static HeroRepository instance;

    private HeroRepository() {
        this.heroParser = new HeroParser(new HeroBuilder());
    }

    public static HeroRepository getInstance() {
        if (instance == null) {
            instance = new HeroRepository();
        }
        return instance;
    }

    public List<String> readHeroesFromFile(Path file) throws Exception {
        List<String> heroDataList = Files.readAllLines(file);
        heroDataList.removeIf(String::isEmpty); // Remove empty lines
        heroDataList.removeIf(line -> line.trim().isEmpty()); // Remove lines that are only whitespace
        heroDataList.removeIf(line -> line.startsWith("#")); // Remove comment lines starting with
        return heroDataList;
    }

    public void saveHeroesToFile(Path file, List<Hero> heroes) throws Exception {
        List<String> heroDataList = heroes.stream()
                .map(Hero::toString)
                .toList();
        Files.write(file, heroDataList);
    }

    public void parseHeroesFromRepository(List<String> heroDataList) {
        for (String heroData : heroDataList) {
            this.heroParser.parseStringToHeroData(heroData);
        }
        this.heroes = this.heroParser.getHeroes();
    }

    public List<Hero> getHeroes() {
        return this.heroes;
    }


}
