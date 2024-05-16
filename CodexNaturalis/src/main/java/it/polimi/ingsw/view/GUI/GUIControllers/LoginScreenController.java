package it.polimi.ingsw.view.GUI.GUIControllers;

import it.polimi.ingsw.enumerations.GUIScene;
import it.polimi.ingsw.exceptions.ServerDisconnectedException;
import it.polimi.ingsw.exceptions.WrongInputException;
import it.polimi.ingsw.view.GUI.GUI;
import it.polimi.ingsw.view.GUI.ViewGUI;
import it.polimi.ingsw.view.View;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginScreenController extends GUIController {
    public TextField username;
    public Label error_message;


    @FXML
    public void actionEnter(ActionEvent event) throws IOException, InterruptedException, ServerDisconnectedException {
        try
        {
            viewGUI.setUsername(username.getText());
            gui.switchScene(GUIScene.LOBBY);
        }
        catch (WrongInputException e)
        {
            username.setPromptText("Username already in use, try again");
            username.setStyle("-fx-prompt-text-fill: red");
             //send feedback to GUI by calling gui method
        }

    }
}
