package it.polimi.ingsw.view.GUI.GUIControllers;

import it.polimi.ingsw.enumerations.GUIScene;
import it.polimi.ingsw.enumerations.GameStatus;
import it.polimi.ingsw.exceptions.NotExistingPlayerException;
import it.polimi.ingsw.exceptions.ServerDisconnectedException;
import it.polimi.ingsw.model.Game;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashSet;

import static org.fusesource.jansi.Ansi.ansi;

public class JoinGameScreenController extends GUIController {
    public ChoiceBox<String> gameList;

    public String gameOnTextFlow;
    public TextFlow textFlow;
    public Button joinButton;
    private ArrayList<Integer> availableGames = new ArrayList<>();
    Task<Void> checkGames;

    public ListView<String> gameList2;
    ObservableList<String> items = FXCollections.observableArrayList();
    public Pane errorPane;
    @FXML
    public void back() {
        gui.switchScene(GUIScene.LOBBY);
    }

    @FXML
    public void joinButton() {
        if(gameList2.getSelectionModel().getSelectedItem() != null) {
            joinButton.setDisable(false);
            joinButton.setVisible(true);
        }
    }
    @FXML
    public void join(ActionEvent event)
    {
        int gameId;
        String choice = gameList2.getSelectionModel().getSelectedItem();
        choice = choice.replaceAll("\\D+", "");
        gameId = Integer.parseInt(choice);
        try
        { checkGames.cancel();
            viewGUI.joinGame(gameId);

            gui.switchScene(GUIScene.GAMELOBBY);
        } catch (ServerDisconnectedException e) {
            throw new RuntimeException(e);
        } catch (NotExistingPlayerException e) {
            errorPane.setDisable(false);
            errorPane.setVisible(true);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void exitFromErrorButton() {
        errorPane.setDisable(true);
        errorPane.setVisible(false);
    }

    @Override
    public void sceneInitializer() {
        joinButton.setDisable(true);
        joinButton.setVisible(false);

        gameList2.getItems().clear();
        gameList2.setItems(items);
        try {
            ArrayList<Game> games = viewGUI.showAvailableGames();
            for(Game game: games) {
                if (game.getGameStatus().getStatusNumber() < GameStatus.ALL_PLAYERS_READY.getStatusNumber()) {
                    items.add("GameID : " + game.getGameId());
                } else {
                    items.add("GameID : " + game.getGameId() + " - ALREADY STARTED");
                }
            }
            availableGames.clear();
            availableGames.addAll(viewGUI.showAvailableGames().stream().map(Game::getGameId).toList());
            checkGames = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    while (availableGames.containsAll(viewGUI.showAvailableGames().stream().map(Game::getGameId).toList()) && new HashSet<>(viewGUI.showAvailableGames().stream().map(Game::getGameId).toList()).containsAll(availableGames))
                    {
                        if(isCancelled())
                            return null;
                    }
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            sceneInitializer();
                        }
                    });

                    return null;
                }
            };
          //  new Thread(checkGames).start();

        } catch (ServerDisconnectedException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
