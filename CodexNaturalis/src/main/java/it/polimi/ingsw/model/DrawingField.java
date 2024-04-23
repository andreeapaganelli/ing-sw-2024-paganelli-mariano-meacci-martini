package it.polimi.ingsw.model;

import it.polimi.ingsw.enumerations.DrawPosition;
import it.polimi.ingsw.exceptions.CardTypeMismatchException;
import it.polimi.ingsw.exceptions.DeckIsEmptyException;
import it.polimi.ingsw.model.cards.GoldCard;
import it.polimi.ingsw.model.cards.ResourceCard;

import java.util.HashMap;
/**
 * This class represents the drawing field in the game. It contains two types of decks: gold card deck and resource card deck.
 * It also maintains the discovered gold cards and discovered resource cards.
 * The drawing field is responsible for drawing cards from the decks and managing the discovered cards.
 */
public class DrawingField {
    private final Deck<GoldCard> goldCardDeck;
    private final Deck<ResourceCard> resourceCardDeck;
    private final HashMap<DrawPosition, GoldCard> discoveredGoldCards;
    private final HashMap<DrawPosition, ResourceCard> discoveredResourceCards;

    //@requires controller has to create the decks with correct types
    /**
     * Constructs a new DrawingField with the given gold card deck and resource card deck.
     * It also initializes the discovered cards.
     *
     * @param goldCardDeck the deck of gold cards
     * @param resourceCardDeck the deck of resource cards
     * @throws CardTypeMismatchException if the type of card does not match the expected type
     * @throws DeckIsEmptyException if the deck is empty
     */
    public DrawingField(Deck<GoldCard> goldCardDeck, Deck<ResourceCard> resourceCardDeck) throws CardTypeMismatchException, DeckIsEmptyException {
        this.goldCardDeck = goldCardDeck;
        this.resourceCardDeck = resourceCardDeck;
        discoveredGoldCards = new HashMap<>();
        discoveredResourceCards = new HashMap<>();

    }
    /**
     * Draws a card from the gold card deck.
     *
     * @param drawPosition the position from which to draw the card
     * @return the drawn card
     * @throws DeckIsEmptyException if the deck is empty
     */
    public GoldCard drawCardFromGoldCardDeck(DrawPosition drawPosition) throws DeckIsEmptyException
    {
        if(drawPosition == DrawPosition.FROMDECK) {
            return goldCardDeck.getTopCard();
        }
        else {
            GoldCard chosenCard = discoveredGoldCards.get(drawPosition);
            if(chosenCard == null)
                throw new DeckIsEmptyException();
            //Change discovered gold card chosen by the player
            if (!goldCardDeck.isEmpty())
                discoveredGoldCards.put(drawPosition, goldCardDeck.getTopCard());
            else {
                discoveredGoldCards.put(drawPosition, null);
            }

            return chosenCard;
        }

    }
    /**
     * Draws a card from the resource card deck.
     *
     * @param drawPosition the position from which to draw the card
     * @return the drawn card
     * @throws DeckIsEmptyException if the deck is empty
     */
    public ResourceCard drawCardFromResourceCardDeck(DrawPosition drawPosition) throws DeckIsEmptyException
    {
        if(drawPosition == DrawPosition.FROMDECK) {
            return resourceCardDeck.getTopCard();
        }
        else {
            ResourceCard chosenCard = discoveredResourceCards.get(drawPosition);
            if(chosenCard == null)
                throw new DeckIsEmptyException();
            //Change discovered resource card chosen by the player
            if(!resourceCardDeck.isEmpty())
                discoveredResourceCards.put(drawPosition, resourceCardDeck.getTopCard());
            else {
                discoveredResourceCards.put(drawPosition, null);
            }
            return chosenCard;
        }

    }
    public void setDiscoveredCards() throws DeckIsEmptyException {
        discoveredGoldCards.put(DrawPosition.LEFT, drawCardFromGoldCardDeck(DrawPosition.FROMDECK));
        discoveredGoldCards.put(DrawPosition.RIGHT, drawCardFromGoldCardDeck(DrawPosition.FROMDECK));

        discoveredResourceCards.put(DrawPosition.LEFT, drawCardFromResourceCardDeck(DrawPosition.FROMDECK));
        discoveredResourceCards.put(DrawPosition.RIGHT, drawCardFromResourceCardDeck(DrawPosition.FROMDECK));
    }
}
