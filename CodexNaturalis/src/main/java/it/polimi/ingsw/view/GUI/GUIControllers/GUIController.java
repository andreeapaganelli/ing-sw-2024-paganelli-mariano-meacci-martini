package it.polimi.ingsw.view.GUI.GUIControllers;

import it.polimi.ingsw.view.GUI.ViewGUI;
import it.polimi.ingsw.view.View;
import javafx.fxml.FXML;

import javafx.event.ActionEvent;

public class GUIController {

    protected ViewGUI viewGUI;

    public void setView(ViewGUI view) {
        this.viewGUI = view;
    }
}