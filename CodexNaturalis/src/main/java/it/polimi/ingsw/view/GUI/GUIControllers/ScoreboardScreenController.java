package it.polimi.ingsw.view.GUI.GUIControllers;

import it.polimi.ingsw.exceptions.ServerDisconnectedException;
import it.polimi.ingsw.model.Player;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.HashMap;
import java.util.List;

public class ScoreboardScreenController extends GUIController {
    public TextFlow textFlow;

    @Override
    public void sceneInitializer() {

        HashMap<String, Integer> playersPoints = viewGUI.showScoreboard();

        Text player = new Text("dhfjks");
        textFlow.getChildren().add(player);
    }
}