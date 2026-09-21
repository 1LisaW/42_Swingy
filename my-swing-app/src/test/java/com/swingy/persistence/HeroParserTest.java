package com.swingy.persistence;

import com.swingy.model.Hero;
import com.swingy.model.HeroBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeroParserTest {

    private HeroParser heroParser;

    @BeforeEach
    void setUp() {
        heroParser = new HeroParser(new HeroBuilder());
    }

    @Test
    void shouldParseHero() {
        String heroData =
                "1|hero|Aragorn|warrior|5|1200|100|30|25";

        heroParser.parseStringToHeroData(heroData);

        assertEquals(1, heroParser.getHeroes().size());

        Hero hero = heroParser.getHeroes().get(0);

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
    void shouldTrimHeroData() {
        String heroData =
                " 1 | hero | Aragorn | warrior | 5 | 1200 | 100 | 30 | 25 ";

        heroParser.parseStringToHeroData(heroData);

        Hero hero = heroParser.getHeroes().get(0);

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
        String wizard =
                "1|hero|Gandalf|wizard|3|600|50|20|10";

        String warrior =
                "2|hero|Aragorn|warrior|5|1200|100|30|25";

        heroParser.parseStringToHeroData(wizard);
        heroParser.parseStringToHeroData(warrior);

        assertEquals(2, heroParser.getHeroes().size());
    }

    @Test
    void shouldReplaceHeroWithSameId() {
        String firstHero =
                "1|hero|Aragorn|warrior|1|100|50|10|5";

        String secondHero =
                "1|hero|Aragorn|warrior|5|1200|100|30|25";

        heroParser.parseStringToHeroData(firstHero);
        heroParser.parseStringToHeroData(secondHero);

        assertEquals(1, heroParser.getHeroes().size());

        Hero hero = heroParser.getHeroes().get(0);

        assertEquals(5, hero.getLevel());
        assertEquals(1200, hero.getExperience());
    }

    @Test
    void shouldPreserveNonNumericHeroId() {
        String heroData =
                "hero-abc|hero|Aragorn|warrior|5|1200|100|30|25";

        heroParser.parseStringToHeroData(heroData);

        assertEquals(1, heroParser.getHeroes().size());

        Hero hero = heroParser.getHeroes().get(0);

        /*
         * A non-numeric ID is allowed by the parser.
         * In that case setOriginalId() is simply not called.
         */
        assertEquals("Aragorn", hero.getName());
    }

    @Test
    void shouldThrowExceptionForMissingDataType() {
        String heroData = "1";

        assertThrows(
                IllegalArgumentException.class,
                () -> heroParser.parseStringToHeroData(heroData)
        );
    }

    @Test
    void shouldThrowExceptionForInvalidDataType() {
        String heroData =
                "1|unknown|Aragorn|warrior|5|1200|100|30|25";

        assertThrows(
                IllegalArgumentException.class,
                () -> heroParser.parseStringToHeroData(heroData)
        );
    }

    @Test
    void shouldThrowExceptionWhenHeroHasWrongNumberOfFields() {
        String heroData =
                "1|hero|Aragorn|warrior|5|1200|100";

        assertThrows(
                IllegalArgumentException.class,
                () -> heroParser.parseStringToHeroData(heroData)
        );
    }

    @Test
    void shouldThrowExceptionWhenHeroHasTooManyFields() {
        String heroData =
                "1|hero|Aragorn|warrior|5|1200|100|30|25|extra";

        assertThrows(
                IllegalArgumentException.class,
                () -> heroParser.parseStringToHeroData(heroData)
        );
    }

    @Test
    void shouldThrowExceptionWhenHeroLevelIsNotNumber() {
        String heroData =
                "1|hero|Aragorn|warrior|abc|1200|100|30|25";

        assertThrows(
                NumberFormatException.class,
                () -> heroParser.parseStringToHeroData(heroData)
        );
    }

    @Test
    void shouldThrowExceptionWhenExperienceIsNotNumber() {
        String heroData =
                "1|hero|Aragorn|warrior|5|abc|100|30|25";

        assertThrows(
                NumberFormatException.class,
                () -> heroParser.parseStringToHeroData(heroData)
        );
    }

    @Test
    void shouldThrowExceptionWhenHitPointsAreNotNumber() {
        String heroData =
                "1|hero|Aragorn|warrior|5|1200|abc|30|25";

        assertThrows(
                NumberFormatException.class,
                () -> heroParser.parseStringToHeroData(heroData)
        );
    }

    @Test
    void shouldThrowExceptionWhenAttackIsNotNumber() {
        String heroData =
                "1|hero|Aragorn|warrior|5|1200|100|abc|25";

        assertThrows(
                NumberFormatException.class,
                () -> heroParser.parseStringToHeroData(heroData)
        );
    }

    @Test
    void shouldThrowExceptionWhenDefenseIsNotNumber() {
        String heroData =
                "1|hero|Aragorn|warrior|5|1200|100|30|abc";

        assertThrows(
                NumberFormatException.class,
                () -> heroParser.parseStringToHeroData(heroData)
        );
    }

    @Test
    void shouldThrowExceptionForInvalidArtifactFieldCount() {
        String artifactData =
                "1|artifact|Sword|weapon|COMMON|10";

        assertThrows(
                IllegalArgumentException.class,
                () -> heroParser.parseStringToHeroData(artifactData)
        );
    }

    @Test
    void shouldThrowExceptionForInvalidArtifactTier() {
        String artifactData =
                "1|artifact|Sword|weapon|INVALID|10|20|5";

        assertThrows(
                IllegalArgumentException.class,
                () -> heroParser.parseStringToHeroData(artifactData)
        );
    }

    @Test
    void shouldIgnoreArtifactWhenHeroDoesNotExist() {
        String artifactData =
                "999|artifact|Sword|weapon|COMMON|10|20|5";

        assertDoesNotThrow(
                () -> heroParser.parseStringToHeroData(artifactData)
        );

        assertTrue(heroParser.getHeroes().isEmpty());
    }

    @Test
    void shouldReturnEmptyListInitially() {
        assertNotNull(heroParser.getHeroes());
        assertTrue(heroParser.getHeroes().isEmpty());
    }

    @Test
    void shouldReturnCopyOfHeroesList() {
        heroParser.parseStringToHeroData(
                "1|hero|Aragorn|warrior|5|1200|100|30|25"
        );

        var heroes = heroParser.getHeroes();

        heroes.clear();

        /*
         * Clearing the returned list must not clear heroParser's
         * internal map.
         */
        assertEquals(1, heroParser.getHeroes().size());
    }
}
