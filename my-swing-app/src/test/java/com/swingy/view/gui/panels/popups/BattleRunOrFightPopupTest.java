package com.swingy.view.gui;

import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.swingy.controller.GameController;
import com.swingy.controller.Phases;

@ExtendWith(MockitoExtension.class)
class BattleRunOrFightPopupTest {

    @Mock
    private GameController controller;

    @Mock
    private PopupManager popupManager;

    /**
     * Creates BattleRunOrFightPopup without executing its constructor.
     *
     * The real constructor opens a Swing dialog, which we don't want
     * during a unit test.
     */
    private BattleRunOrFightPopup createPopupWithoutConstructor() {
        try {
            org.objenesis.ObjenesisStd objenesis =
                new org.objenesis.ObjenesisStd();

            return objenesis.newInstance(BattleRunOrFightPopup.class);

        } catch (Exception e) {
            throw new RuntimeException(
                "Could not create BattleRunOrFightPopup without constructor",
                e
            );
        }
    }

    /**
     * Sets a field using reflection.
     *
     * This also searches the parent classes because controller and
     * popupManager are probably declared in APopup.
     */
    private void setField(
        Object object,
        String fieldName,
        Object value
    ) throws Exception {

        Class<?> currentClass = object.getClass();

        while (currentClass != null) {
            try {
                Field field =
                    currentClass.getDeclaredField(fieldName);

                field.setAccessible(true);
                field.set(object, value);

                return;

            } catch (NoSuchFieldException e) {
                currentClass = currentClass.getSuperclass();
            }
        }

        throw new NoSuchFieldException(
            "Could not find field: " + fieldName
        );
    }

    /**
     * Invokes a private method using reflection.
     */
    private void invokePrivateMethod(
        Object object,
        String methodName
    ) throws Exception {

        Method method =
            object.getClass().getDeclaredMethod(methodName);

        method.setAccessible(true);
        method.invoke(object);
    }

    @Test
    void runChoice_runsFromBattleAndGoesToRunResult()
        throws Exception {

        BattleRunOrFightPopup popup =
            createPopupWithoutConstructor();

        setField(popup, "controller", controller);
        setField(popup, "popupManager", popupManager);

        invokePrivateMethod(popup, "onChoiceToRun");

        // Verify that the player runs from the battle.
        verify(controller).runFromBattle();

        // Verify the next game phase.
        verify(controller).setGamePhase(
            Phases.BATTLE_RUN_RESULT
        );

        // Verify that the next popup is displayed.
        verify(popupManager).next();

        // Make sure fight logic was not called.
        verify(controller, never()).simulateBattle();
    }

    @Test
    void fightChoice_simulatesBattleAndGoesToBattleResult()
        throws Exception {

        BattleRunOrFightPopup popup =
            createPopupWithoutConstructor();

        setField(popup, "controller", controller);
        setField(popup, "popupManager", popupManager);

        invokePrivateMethod(popup, "onChoiceToFight");

        // Verify the phase is changed to BATTLE_RESULT.
        verify(controller).setGamePhase(
            Phases.BATTLE_RESULT
        );

        // Verify that the battle is simulated.
        verify(controller).simulateBattle();

        // Verify that the next popup is displayed.
        verify(popupManager).next();

        // Make sure running logic was not called.
        verify(controller, never()).runFromBattle();
    }

    @Test
    void runChoice_callsMethodsInCorrectOrder()
        throws Exception {

        BattleRunOrFightPopup popup =
            createPopupWithoutConstructor();

        setField(popup, "controller", controller);
        setField(popup, "popupManager", popupManager);

        invokePrivateMethod(popup, "onChoiceToRun");

        /*
         * In the actual method the order is:
         *
         * 1. runFromBattle()
         * 2. setGamePhase()
         * 3. popupManager.next()
         */
        var inOrder = inOrder(controller, popupManager);

        inOrder.verify(controller).runFromBattle();

        inOrder.verify(controller).setGamePhase(
            Phases.BATTLE_RUN_RESULT
        );

        inOrder.verify(popupManager).next();
    }

    @Test
    void fightChoice_callsMethodsInCorrectOrder()
        throws Exception {

        BattleRunOrFightPopup popup =
            createPopupWithoutConstructor();

        setField(popup, "controller", controller);
        setField(popup, "popupManager", popupManager);

        invokePrivateMethod(popup, "onChoiceToFight");

        /*
         * In the actual method the order is:
         *
         * 1. setGamePhase()
         * 2. simulateBattle()
         * 3. popupManager.next()
         */
        var inOrder = inOrder(controller, popupManager);

        inOrder.verify(controller).setGamePhase(
            Phases.BATTLE_RESULT
        );

        inOrder.verify(controller).simulateBattle();

        inOrder.verify(popupManager).next();
    }
}
