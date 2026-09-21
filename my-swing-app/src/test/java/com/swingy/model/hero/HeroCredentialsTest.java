package com.swingy.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeroCredentialsTest {

    @Test
    void shouldCreateCredentialsWithNameAndArchetype() {
        HeroCredentials credentials =
                new HeroCredentials("Gandalf", "wizard");

        assertEquals("Gandalf", credentials.getName());
        assertEquals(HeroArchetype.WIZARD, credentials.getHeroType());
    }

    @Test
    void shouldSetName() {
        HeroCredentials credentials = new HeroCredentials();

        credentials.setName("Aragorn");

        assertEquals("Aragorn", credentials.getName());
    }

    @Test
    void shouldSetWizardArchetype() {
        HeroCredentials credentials = new HeroCredentials();

        credentials.setHeroArchetype("wizard");

        assertEquals(HeroArchetype.WIZARD, credentials.getHeroType());
    }

    @Test
    void shouldSetWarriorArchetype() {
        HeroCredentials credentials = new HeroCredentials();

        credentials.setHeroArchetype("warrior");

        assertEquals(HeroArchetype.WARRIOR, credentials.getHeroType());
    }

    @Test
    void shouldSetBarbarianArchetype() {
        HeroCredentials credentials = new HeroCredentials();

        credentials.setHeroArchetype("barbarian");

        assertEquals(HeroArchetype.BARBARIAN, credentials.getHeroType());
    }

    @Test
    void shouldAcceptUppercaseArchetype() {
        HeroCredentials credentials = new HeroCredentials();

        credentials.setHeroArchetype("WIZARD");

        assertEquals(HeroArchetype.WIZARD, credentials.getHeroType());
    }

    @Test
    void shouldAcceptMixedCaseArchetype() {
        HeroCredentials credentials = new HeroCredentials();

        credentials.setHeroArchetype("WaRrIoR");

        assertEquals(HeroArchetype.WARRIOR, credentials.getHeroType());
    }

    @Test
    void shouldReturnTrueForValidName() {
        HeroCredentials credentials = new HeroCredentials();

        assertTrue(credentials.isNameValid("Aragorn"));
    }

    @Test
    void shouldReturnFalseForEmptyName() {
        HeroCredentials credentials = new HeroCredentials();

        assertFalse(credentials.isNameValid(""));
    }

    @Test
    void shouldReturnFalseForWhitespaceOnlyName() {
        HeroCredentials credentials = new HeroCredentials();

        assertFalse(credentials.isNameValid("   "));
    }

    @Test
    void shouldReturnTrueForNameWithSpacesAroundIt() {
        HeroCredentials credentials = new HeroCredentials();

        assertTrue(credentials.isNameValid("  Aragorn  "));
    }
}
