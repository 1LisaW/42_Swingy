package com.swingy.view.gui;

import static org.mockito.Mockito.*;

import java.lang.reflect.Method;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.swingy.controller.GameController;
import com.swingy.controller.Phases;
import com.swingy.model.BattleResult;

@ExtendWith(MockitoExtension.class)
class BattleResultPopupTest {

    @Mock
    private GameController controller;

    @Mock
    private PopupManager popupManager;

    private BattleResultPopup popup;

    @BeforeEach
    void setUp() {
        /*
         * We don't construct BattleResultPopup here because its constructor
         * immediately creates and displays a Swing dialog.
         *
         * The tests below create the object without invoking its constructor.
         */
    }

    /**
     * Invokes the private onAccept() method.
     */
    private void invokeOnAccept() throws Exception {
        Method method = BattleResultPopup.class.getDeclaredMethod("onAccept");
        method.setAccessible(true);
        method.invoke(popup);
    }

    /**
     * Creates a BattleResultPopup instance without running its constructor.
     *
     * This requires Objenesis, which is normally already available through
     * Mockito.
     */
    private BattleResultPopup createPopupWithoutConstructor() {
        try {
            org.objenesis.ObjenesisStd objenesis =
                new org.objenesis.ObjenesisStd();

            return objenesis.newInstance(BattleResultPopup.class);

        } catch (Exception e) {
            throw new RuntimeException(
                "Could not create BattleResultPopup without constructor",
                e
            );
        }
    }

    @Test
    void winWithArtifact_goesToBattleArtifact() throws Exception {
        popup = createPopupWithoutConstructor();

        when(controller.getBattleResult())
            .thenReturn(BattleResult.WIN);

        when(controller.isBattleProduceArtifact())
            .thenReturn(true);

        // Inject mocked fields into APopup/BattleResultPopup.
        setField(popup, "controller", controller);
        setField(popup, "popupManager", popupManager);

        invokeOnAccept();

        verify(controller).setGamePhase(Phases.BATTLE_ARTIFACT);
        verify(popupManager).next();

        verify(popupManager, never()).checkGameOver();
    }

    @Test
    void winWithLevelUp_goesToHeroLevelUp() throws Exception {
        popup = createPopupWithoutConstructor();

        when(controller.getBattleResult())
            .thenReturn(BattleResult.WIN);

        when(controller.isBattleProduceArtifact())
            .thenReturn(false);

        /*
         * First call = level before experience
         * Second call = level after experience
         */
        when(controller.getHeroLevel())
            .thenReturn(1)
            .thenReturn(2);

        setField(popup, "controller", controller);
        setField(popup, "popupManager", popupManager);

        invokeOnAccept();

        verify(controller).collectBattleExperience();
        verify(controller).setGamePhase(Phases.HERO_LEVEL_UP);
        verify(popupManager).next();

        verify(popupManager, never()).checkGameOver();
    }

    @Test
    void winWithoutLevelUp_goesToGameplay() throws Exception {
        popup = createPopupWithoutConstructor();

        when(controller.getBattleResult())
            .thenReturn(BattleResult.WIN);

        when(controller.isBattleProduceArtifact())
            .thenReturn(false);

        /*
         * Same level before and after collecting experience.
         */
        when(controller.getHeroLevel())
            .thenReturn(3)
            .thenReturn(3);

        setField(popup, "controller", controller);
        setField(popup, "popupManager", popupManager);

        invokeOnAccept();

        verify(controller).collectBattleExperience();
        verify(controller).setGamePhase(Phases.GAMEPLAY);
        verify(popupManager).checkGameOver();

        verify(popupManager, never()).next();
    }

    @Test
    void lose_checksGameOver() throws Exception {
        popup = createPopupWithoutConstructor();

        when(controller.getBattleResult())
            .thenReturn(BattleResult.LOSE);

        setField(popup, "controller", controller);
        setField(popup, "popupManager", popupManager);

        invokeOnAccept();

        verify(popupManager).checkGameOver();
        verify(controller, never()).setGamePhase(any());
        verify(popupManager, never()).next();
    }

    @Test
    void draw_checksGameOver() throws Exception {
        popup = createPopupWithoutConstructor();

        when(controller.getBattleResult())
            .thenReturn(BattleResult.DRAW);

        setField(popup, "controller", controller);
        setField(popup, "popupManager", popupManager);

        invokeOnAccept();

        verify(popupManager).checkGameOver();
        verify(controller, never()).setGamePhase(any());
        verify(popupManager, never()).next();
    }

    /**
     * Sets a private/protected field using reflection.
     *
     * The fields controller and popupManager are inherited from APopup,
     * so this method searches through the class hierarchy.
     */
    private void setField(
        Object object,
        String fieldName,
        Object value
    ) throws Exception {

        Class<?> currentClass = object.getClass();

        while (currentClass != null) {
            try {
                java.lang.reflect.Field field =
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
}
