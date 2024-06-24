package it.polimi.ingsw.controller.cardFactory;

import com.google.gson.Gson;
import it.polimi.ingsw.exceptions.CardNotImportedException;
import it.polimi.ingsw.model.cards.ObjectiveCard;
import it.polimi.ingsw.model.cards.ResourceObjectiveCard;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * This class is responsible for creating a list of ResourceObjectiveCard instances.
 * It extends the CardFactory class with a type parameter of ObjectiveCard.
 */
public class ResourceObjectiveCardFactory extends CardFactory<ObjectiveCard> {

    /**
     * This method creates a list of ResourceObjectiveCard instances.
     * It reads the card data from a JSON file and uses Gson to parse the JSON data into ResourceObjectiveCard objects.
     * If an IOException occurs during this process, it throws a CardNotImportedException.
     *
     * @return An ArrayList of ObjectiveCard instances.
     * @throws CardNotImportedException if there is an error reading the card data from the JSON file.
     */
    public ArrayList<ObjectiveCard> createCardList() throws CardNotImportedException {
        ResourceObjectiveCard[] resourceObjectiveCardArray;
        Gson gson = new Gson();
        try(Reader reader = new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/cardJson/resourceObjectiveCards.json")))) {
            resourceObjectiveCardArray = gson.fromJson(reader, ResourceObjectiveCard[].class);
        } catch (IOException e) {
            throw new CardNotImportedException();
        }
        return new ArrayList<>(Arrays.asList(resourceObjectiveCardArray));
    }
}