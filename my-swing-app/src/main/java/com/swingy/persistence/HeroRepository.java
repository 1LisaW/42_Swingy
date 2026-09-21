package com.swingy.persistence;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
// import java.util.List;
import java.util.stream.Collectors;

import java.util.Objects;
import java.util.stream.IntStream;


import com.swingy.model.Hero;
import com.swingy.model.HeroBuilder;
import com.swingy.persistence.HeroParser;


public class HeroRepository {
    private HeroParser heroParser;
    private List<Hero> heroes = Collections.emptyList();
    private static HeroRepository instance;
    // private final Path filePath = Paths.get("src/test/resources/save.txt");
    // private Path filePath = java.nio.file.Paths.get("save.txt");
    private Path filePath = Paths.get("src/main/resources/save.txt");

    private HeroRepository() {
        this.heroParser = new HeroParser(new HeroBuilder());
    }

    public static HeroRepository getInstance() {
        if (instance == null) {
            instance = new HeroRepository();
        }
        return instance;
    }

    public void updateFilePath(Path filePath) {
        this.filePath = filePath;
    }

    public List<String> readHeroesFromFile() throws Exception {
        // InputStream inputStream =
        //         getClass().getClassLoader().getResourceAsStream("save.txt");

        // if (inputStream == null) {
        //     throw new FileNotFoundException("save.txt not found in resources");
        // }

        // try (BufferedReader reader =
        //             new BufferedReader(new InputStreamReader(inputStream))) {

        //     List<String> heroDataList = reader.lines()
        //             .collect(Collectors.toList());

        //     heroDataList.removeIf(String::isEmpty);
        //     heroDataList.removeIf(line -> line.trim().isEmpty());
        //     heroDataList.removeIf(line -> line.startsWith("#"));

        //     return heroDataList;
        // }
        List<String> heroDataList = Files.readAllLines(filePath);
        heroDataList.removeIf(String::isEmpty); // Remove empty lines
        heroDataList.removeIf(line -> line.trim().isEmpty()); // Remove lines that are only whitespace
        heroDataList.removeIf(line -> line.startsWith("#")); // Remove comment lines starting with
        return heroDataList;
    }

    public void saveHeroesToFile() throws Exception {
        AtomicInteger index = new AtomicInteger();
        List<String> heroDataList = this.heroes.stream()
                .map(hero -> hero.toRepoFormat(index))
                .toList();
        Files.write(filePath, heroDataList);
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

    public void addHero(Hero hero) {
        if (hero == null)
            return ;
        int id = hero.getOriginalId();
        int toBeReplacedId = IntStream.range(0, heroes.size())
            .filter(i -> Objects.equals(heroes.get(i).getOriginalId(), id)
            )
            .findFirst()
            .orElse(-1);
        if (toBeReplacedId == -1) {
            hero.setOriginalId(heroes.size());
            heroes.add(hero);
        } else {
            heroes.set(toBeReplacedId, hero);
        }
    }

    public boolean containsHero(Hero hero) {
        return this.heroes.contains(hero);
    }
}
