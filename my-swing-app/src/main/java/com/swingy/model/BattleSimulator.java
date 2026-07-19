package com.swingy.model;

import com.swingy.model.Hero;
import com.swingy.model.Villain;

public class BattleSimulator {
    private Hero hero;
    private Villain villain;

    public BattleSimulator(Hero hero, Villain villain) {
        this.hero = hero;
        this.villain = villain;
    }

    public int fight() {
        return 1;
    }

    public int run() {
        return 1;
    }
}
