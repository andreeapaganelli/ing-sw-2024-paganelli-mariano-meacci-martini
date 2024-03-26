package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.enumerations.AngleOrientation;
import it.polimi.ingsw.enumerations.Resource;
import it.polimi.ingsw.enumerations.Side;
import it.polimi.ingsw.exceptions.InvalidConstructorDataException;

import java.util.HashMap;
import java.util.ArrayList;

/**
 * Class that represents a started card
 */
public class ResourceCard extends StarterCard {

    private final Resource cardColor;
    private final int points;

    /**
     * Constructor
     * @param cardId id of the card
     * @param currentSide one of the two side of the card (front or back)
     * @param centralResource list of the resources in the middle of the card
     * @param angles maps the position of the angle into the angle object
     * @param cardColor one of the four colors, each associated with a resource
     * @param points points given by a card by positioning it
     * @throws InvalidConstructorDataException if an invalid parameter is given
     */
    public ResourceCard(int cardId, Side currentSide, ArrayList<Resource> centralResource, HashMap<AngleOrientation, Angle> angles, Resource cardColor, int points) throws InvalidConstructorDataException {
        super(cardId, currentSide, centralResource, angles);
        this.cardColor = cardColor;
        this.points = points;
    }

    /**
     *
     * @return the color of the card, associated with a resource
     */
    public Resource getCardColor()
    {
        return cardColor;
    }

    /**
     *
     * @return points given by a card by positioning it
     */
    public int getPoints()
    {
        return points;
    }
}
