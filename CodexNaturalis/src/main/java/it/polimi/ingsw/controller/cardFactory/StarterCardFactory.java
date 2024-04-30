package it.polimi.ingsw.controller.cardFactory;

import com.google.gson.Gson;
import it.polimi.ingsw.controller.cardFactory.CardFactory;
import it.polimi.ingsw.exceptions.CardNotImportedException;
import it.polimi.ingsw.model.cards.PlayableCard;
import it.polimi.ingsw.model.cards.StarterCard;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;

public class StarterCardFactory extends CardFactory<StarterCard> {
    public ArrayList<StarterCard> createCardList() throws CardNotImportedException {
        StarterCard[] starterCardArray;
        Gson gson = new Gson();
        try(Reader reader = new FileReader("CodexNaturalis/src/main/resources/starterCards.json")) {
            starterCardArray = gson.fromJson(reader, StarterCard[].class);
        } catch (IOException e) {
            throw new CardNotImportedException();
        }
        return new ArrayList<>(Arrays.asList(starterCardArray));

    }
}
