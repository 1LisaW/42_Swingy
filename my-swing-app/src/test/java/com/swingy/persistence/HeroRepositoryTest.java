package com.swingy.persistence;

import com.swingy.model.Hero;
import com.swingy.model.HeroBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HeroRepositoryTest {

    private HeroRepository repository;
    private final Path saveFile = Path.of("save.txt");

    @BeforeEach
    void setUp() throws Exception {
        /*
         * HeroRepository is a singleton, so reset it before
         * every test to avoid sharing state between tests.
         */
        resetRepository();

        Files.deleteIfExists(saveFile);

        repository = HeroRepository.getInstance();

        /*
         * parseHeroesFromRepository initializes the internal
         * heroes list.
         */
        repository.parseHeroesFromRepository(List.of());
    }

    @AfterEach
    void tearDown() throws Exception {
        Files.deleteIfExists(saveFile);
    }

    private void resetRepository() throws Exception {
        java.lang.reflect.Field instanceField =
                HeroRepository.class.getDeclaredField("instance");

        instanceField.setAccessible(true);
        instanceField.set(null, null);
    }

    private Hero createHero(String name) {
        return new HeroBuilder()
                .setName(name)
                .setArchetype("warrior")
                .setLevel(1)
                .setExperience(100)
                .setHitPoints(100)
                .setAttack(20)
                .setDefense(10)
                .build();
    }

    @Test
    void shouldReturnSameSingletonInstance() {
        HeroRepository first = HeroRepository.getInstance();
        HeroRepository second = HeroRepository.getInstance();

        assertSame(first, second);
    }

    @Test
    void shouldInitiallyHaveNoHeroes() {
        assertNotNull(repository.getHeroes());
        assertTrue(repository.getHeroes().isEmpty());
    }

    @Test
    void shouldAddHero() {
        Hero hero = createHero("Aragorn");

        repository.addHero(hero);

        assertEquals(1, repository.getHeroes().size());
        assertSame(hero, repository.getHeroes().get(0));
    }

    @Test
    void shouldAssignIdWhenAddingNewHero() {
        Hero hero = createHero("Aragorn");

        repository.addHero(hero);

        assertEquals(0, hero.getOriginalId());
    }

    @Test
    void shouldAssignDifferentIdsToNewHeroes() {
        Hero firstHero = createHero("Aragorn");
        Hero secondHero = createHero("Gandalf");

        repository.addHero(firstHero);
        repository.addHero(secondHero);

        assertEquals(0, firstHero.getOriginalId());
        assertEquals(1, secondHero.getOriginalId());
    }

    @Test
    void shouldReplaceHeroWithSameId() {
        Hero firstHero = createHero("Aragorn");
        firstHero.setOriginalId(0);

        Hero replacementHero = createHero("Gandalf");
        replacementHero.setOriginalId(0);

        repository.addHero(firstHero);
        repository.addHero(replacementHero);

        assertEquals(1, repository.getHeroes().size());
        assertSame(
                replacementHero,
                repository.getHeroes().get(0)
        );
    }

    @Test
    void shouldNotAddNullHero() {
        repository.addHero(null);

        assertTrue(repository.getHeroes().isEmpty());
    }

    @Test
    void shouldContainAddedHero() {
        Hero hero = createHero("Aragorn");

        repository.addHero(hero);

        assertTrue(repository.containsHero(hero));
    }

    @Test
    void shouldNotContainHeroThatWasNotAdded() {
        Hero hero = createHero("Aragorn");

        assertFalse(repository.containsHero(hero));
    }

    @Test
    void shouldParseHeroesFromRepository() {
        List<String> data = List.of(
                "1|hero|Aragorn|warrior|5|1200|100|30|25"
        );

        repository.parseHeroesFromRepository(data);

        assertEquals(1, repository.getHeroes().size());

        Hero hero = repository.getHeroes().get(0);

        assertEquals("Aragorn", hero.getName());
        assertEquals("warrior", hero.getArchetype());
        assertEquals(5, hero.getLevel());
        assertEquals(1200, hero.getExperience());
        assertEquals(100, hero.getHitPoints());
        assertEquals(30, hero.getAttack());
        assertEquals(25, hero.getDefense());
        assertEquals(1, hero.getOriginalId());
    }

    @Test
    void shouldParseMultipleHeroes() {
        List<String> data = List.of(
                "1|hero|Aragorn|warrior|5|1200|100|30|25",
                "2|hero|Gandalf|wizard|3|600|50|20|10"
        );

        repository.parseHeroesFromRepository(data);

        assertEquals(2, repository.getHeroes().size());
    }

    @Test
    void shouldReadHeroesFromFile() throws Exception {
        Files.write(
                saveFile,
                List.of(
                        "1|hero|Aragorn|warrior|5|1200|100|30|25",
                        "2|hero|Gandalf|wizard|3|600|50|20|10"
                )
        );

        List<String> result =
                repository.readHeroesFromFile();

        assertEquals(2, result.size());
        assertEquals(
                "1|hero|Aragorn|warrior|5|1200|100|30|25",
                result.get(0)
        );
        assertEquals(
                "2|hero|Gandalf|wizard|3|600|50|20|10",
                result.get(1)
        );
    }

    @Test
    void shouldIgnoreEmptyLinesWhenReadingFile()
            throws Exception {

        Files.write(
                saveFile,
                List.of(
                        "",
                        "   ",
                        "1|hero|Aragorn|warrior|5|1200|100|30|25",
                        "",
                        "2|hero|Gandalf|wizard|3|600|50|20|10"
                )
        );

        List<String> result =
                repository.readHeroesFromFile();

        assertEquals(2, result.size());
    }

    @Test
    void shouldIgnoreCommentLinesWhenReadingFile()
            throws Exception {

        Files.write(
                saveFile,
                List.of(
                        "# This is a comment",
                        "1|hero|Aragorn|warrior|5|1200|100|30|25",
                        "# Another comment",
                        "2|hero|Gandalf|wizard|3|600|50|20|10"
                )
        );

        List<String> result =
                repository.readHeroesFromFile();

        assertEquals(2, result.size());
        assertFalse(result.get(0).startsWith("#"));
        assertFalse(result.get(1).startsWith("#"));
    }

    @Test
    void shouldReadAndParseHeroesFromFile()
            throws Exception {

        Files.write(
                saveFile,
                List.of(
                        "1|hero|Aragorn|warrior|5|1200|100|30|25",
                        "2|hero|Gandalf|wizard|3|600|50|20|10"
                )
        );

        List<String> data =
                repository.readHeroesFromFile();

        repository.parseHeroesFromRepository(data);

        assertEquals(2, repository.getHeroes().size());

        assertEquals(
                "Aragorn",
                repository.getHeroes().get(0).getName()
        );

        assertEquals(
                "Gandalf",
                repository.getHeroes().get(1).getName()
        );
    }

    @Test
    void shouldSaveHeroesToFile() throws Exception {
        Hero hero = createHero("Aragorn");

        repository.addHero(hero);
        repository.saveHeroesToFile();

        assertTrue(Files.exists(saveFile));

        List<String> savedData =
                Files.readAllLines(saveFile);

        assertFalse(savedData.isEmpty());
        assertTrue(
                savedData.get(0).contains("Aragorn")
        );
    }

    @Test
    void shouldSaveMultipleHeroesToFile()
            throws Exception {

        repository.addHero(createHero("Aragorn"));
        repository.addHero(createHero("Gandalf"));

        repository.saveHeroesToFile();

        List<String> savedData =
                Files.readAllLines(saveFile)
                .stream()
                .filter(i-> !i.isEmpty())
                .toList();

        assertEquals(2, savedData.size());
        assertTrue(savedData.get(0).contains("Aragorn"));
        assertTrue(savedData.get(1).contains("Gandalf"));
    }
}
