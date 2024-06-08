package it.polimi.ingsw.view;

import it.polimi.ingsw.exceptions.CardTypeMismatchException;
import it.polimi.ingsw.exceptions.GameNotFoundException;
import it.polimi.ingsw.exceptions.NotExistingPlayerException;
import it.polimi.ingsw.exceptions.ServerDisconnectedException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.cards.ObjectiveCard;
import it.polimi.ingsw.network.messages.GameEvent;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Objects;

public interface View  {
    void boardUpdate(Game gameUpdated);
    void newPlayer(Game gameUpdated);
    void update(Game gameUpdated);
    void twentyPoints(String username);
    void chooseObjectiveCard(ArrayList<ObjectiveCard> objectiveCardsToChoose) throws ServerDisconnectedException, GameNotFoundException;
    void finalRound();
    void gameEndDisconnection();
    void gameEnd();
    void chatMessage(String message);
}
