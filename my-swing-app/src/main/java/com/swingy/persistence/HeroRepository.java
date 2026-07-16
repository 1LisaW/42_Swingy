package com.swingy.persistence;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import com.swingy.model.Hero;


public class HeroRepository {
    private List<Hero> heroes;
    public List<String> readHeroesFromFile(Path file) throws Exception {
        return Files.readAllLines(file);
    }

    

}
