package com.swingy.view.gui;

import com.swingy.controller.GameController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class APopupTest {

    private GameController controller;
    private PopupManager popupManager;
    private TestPopup popup;

    @BeforeEach
    void setUp() {
        controller = mock(GameController.class);
        popupManager = mock(PopupManager.class);

        popup = new TestPopup(controller, popupManager);
    }

    @Test
    void constructorShouldStoreControllerAndPopupManager() {
        assertSame(controller, popup.getController());
        assertSame(popupManager, popup.getPopupManager());
    }

    @Test
    void closeShouldDoNothingWhenDialogIsNull() {
        assertDoesNotThrow(() -> popup.close());

        assertNull(popup.getCurrentDialog());
    }

    @Test
    void closeShouldDisposeDialogAndSetItToNull() {
        JDialog dialog = mock(JDialog.class);
        popup.setCurrentDialog(dialog);

        popup.close();

        verify(dialog).dispose();
        assertNull(popup.getCurrentDialog());
    }

    @Test
    void nextPopupShouldCallPopupManagerNext() {
        popup.nextPopup();

        verify(popupManager).next();
    }

    /**
     * Concrete implementation used only for testing APopup.
     */
    private static class TestPopup extends APopup {

        TestPopup(GameController controller, PopupManager popupManager) {
            super(controller, popupManager);
        }

        GameController getController() {
            return controller;
        }

        PopupManager getPopupManager() {
            return popupManager;
        }

        JDialog getCurrentDialog() {
            return currentDialog;
        }

        void setCurrentDialog(JDialog dialog) {
            currentDialog = dialog;
        }
    }
}
