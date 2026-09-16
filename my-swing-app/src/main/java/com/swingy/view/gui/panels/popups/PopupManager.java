package com.swingy.view.gui;

import com.swingy.controller.GameController;
import com.swingy.controller.Phases;

import com.swingy.view.gui.APopup;
import com.swingy.view.gui.BattleRunOrFightPopup;
import com.swingy.view.gui.BattleRunResultPopup;
import com.swingy.view.gui.BattleResultPopup;
import com.swingy.view.gui.ArtifactPopup;
import com.swingy.view.gui.LevelUpPopup;

import com.swingy.view.gui.GamePanel;



public class PopupManager {
    private final GameController controller;
    private APopup currentPopup = null;
    private final GamePanel gp;

    public PopupManager(GameController controller, GamePanel gp) {
        this.controller = controller;
        this.gp = gp;
    }

    public void closeCurrentPopup() {
        if (currentPopup == null)
            return;
        currentPopup.close();
        currentPopup = null;
    }

    public void next() {
        Phases phase = this.controller.getGamePhase();
        switch (phase) {
            case BATTLE_RUN_OR_FIGHT:
                currentPopup = new BattleRunOrFightPopup(this.controller, this);
                break ;
            case BATTLE_RUN_RESULT:
                currentPopup = new BattleRunResultPopup(this.controller, this);
                gp.repaint();
                break ;
            case BATTLE_RESULT:
                currentPopup = new BattleResultPopup(this.controller, this);
                gp.repaint();
                break ;
            case BATTLE_ARTIFACT:
                currentPopup = new ArtifactPopup(this.controller, this);
                gp.repaint();
                break ;
            case HERO_LEVEL_UP:
                currentPopup = new LevelUpPopup(this.controller, this);
                gp.repaint();
                break ;
            default:
                break;
        }
    }
}


