package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.enumerations.AngleOrientation;
import it.polimi.ingsw.enumerations.GoldPointCondition;
import it.polimi.ingsw.enumerations.Resource;
import it.polimi.ingsw.enumerations.Side;
import it.polimi.ingsw.exceptions.InvalidConstructorDataException;

import java.util.ArrayList;
import java.util.HashMap;

public class GoldCard extends ResourceCard {

    private final ArrayList<Resource> requirements;
    private final GoldPointCondition goldPointCondition;

    //if the card has no requirements, controller has to pass requirements with NONE value
    public GoldCard(int cardId, Side currentSide, ArrayList<Resource> centralResource, HashMap<AngleOrientation, Angle> angles, Resource cardColor, ArrayList<Resource> requirements, GoldPointCondition goldPointCondition, int points) throws InvalidConstructorDataException {
        super(cardId, currentSide, centralResource, angles, cardColor, points);
        try {
            this.requirements = new ArrayList<>(requirements);
            this.goldPointCondition = goldPointCondition;
        }
        catch(Exception e)
        {
            throw new InvalidConstructorDataException();
        }
    }

    public ArrayList<Resource> getRequirements()
    {
        return new ArrayList<Resource>(requirements);
    }
    public GoldPointCondition getGoldPointCondition()
    {
        return goldPointCondition;
    }


}
