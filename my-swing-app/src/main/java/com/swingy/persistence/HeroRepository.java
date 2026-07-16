package com.swingy.persistence;

import java.nio.file.Files;
import java.nio.file.Path;
import java.io.Exception;
import java.util.List;

public class HeroRepository {
    public List<String> readHeroesFromFile(Path file) throws Exception {
        return Files.readAllLines(file);
    }

}
