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
class LevelUpPopupTest {

    @Mock
    private GameController controller;

    @Mock
    private PopupManager popupManager;

    /**
     * Creates LevelUpPopup without executing its constructor.
     *
     * The real constructor opens a Swing dialog, which we don't want
     * during a unit test.
     */
    private LevelUpPopup createPopupWithoutConstructor() {
        try {
            org.objenesis.ObjenesisStd objenesis =
                new org.objenesis.ObjenesisStd();

            return objenesis.newInstance(LevelUpPopup.class);

        } catch (Exception e) {
            throw new RuntimeException(
                "Could not create LevelUpPopup without constructor",
                e
            );
        }
    }

    /**
     * Sets a field using reflection.
     *
     * Searches the parent classes as well because controller and
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
     * Invokes the private onAccept() method.
     */
    private void invokeOnAccept(LevelUpPopup popup)
        throws Exception {

        Method method =
            LevelUpPopup.class.getDeclaredMethod("onAccept");

        method.setAccessible(true);
        method.invoke(popup);
    }

    @Test
    void accept_changesPhaseToGameplay()
        throws Exception {

        LevelUpPopup popup =
            createPopupWithoutConstructor();

        setField(popup, "controller", controller);
        setField(popup, "popupManager", popupManager);

        invokeOnAccept(popup);

        verify(controller).setGamePhase(
            Phases.GAMEPLAY
        );
    }

    @Test
    void accept_checksGameOver()
        throws Exception {

        LevelUpPopup popup =
            createPopupWithoutConstructor();

        setField(popup, "controller", controller);
        setField(popup, "popupManager", popupManager);

        invokeOnAccept(popup);

        verify(popupManager).checkGameOver();
    }

    @Test
    void accept_changesPhaseBeforeCheckingGameOver()
        throws Exception {

        LevelUpPopup popup =
            createPopupWithoutConstructor();

        setField(popup, "controller", controller);
        setField(popup, "popupManager", popupManager);

        invokeOnAccept(popup);

        var inOrder = inOrder(controller, popupManager);

        inOrder.verify(controller).setGamePhase(
            Phases.GAMEPLAY
        );

        inOrder.verify(popupManager).checkGameOver();
    }

    @Test
    void accept_doesNotCallUnexpectedControllerMethods()
        throws Exception {

        LevelUpPopup popup =
            createPopupWithoutConstructor();

        setField(popup, "controller", controller);
        setField(popup, "popupManager", popupManager);

        invokeOnAccept(popup);

        verify(controller).setGamePhase(
            Phases.GAMEPLAY
        );

        verify(controller, never()).runFromBattle();
        verify(controller, never()).simulateBattle();

        verify(popupManager).checkGameOver();
        verify(popupManager, never()).next();
    }
}
