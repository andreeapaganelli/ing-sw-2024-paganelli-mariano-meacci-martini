package it.polimi.ingsw.model;

import it.polimi.ingsw.enumerations.AngleOrientation;
import it.polimi.ingsw.enumerations.AngleStatus;
import it.polimi.ingsw.exceptions.AngleAlreadyLinkedException;
import it.polimi.ingsw.exceptions.CardNotFoundException;
import it.polimi.ingsw.exceptions.InvalidCardPositionException;
import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.cards.CardPosition;
import it.polimi.ingsw.model.cards.PlayableCard;
import it.polimi.ingsw.model.cards.StarterCard;

import java.io.Serializable;
import java.util.ArrayList;

import static it.polimi.ingsw.model.GameValues.DEFAULT_MATRIX_SIZE;
/**
 * This class represents a player field. It contains the field of the player, represented by a matrix.
 * This class is responsible for adding cards to the player field.
 */
public class PlayerField implements Serializable {
    /**
     * Matrix of playableCards used to save the playerFiled
     */
    private final PlayableCard[][] matrixField;
    private final ArrayList<CardPosition> playedCards = new ArrayList<>();

    /**
     * Constructor
     */
    public PlayerField()
    {
        this.matrixField = new PlayableCard[DEFAULT_MATRIX_SIZE][DEFAULT_MATRIX_SIZE];
    }

    /**
     * Getter
     * @return the MatrixFiled of the player
     */
    public PlayableCard[][] getMatrixField()
    {
        PlayableCard[][] returnedMatrixField = new PlayableCard[DEFAULT_MATRIX_SIZE][DEFAULT_MATRIX_SIZE];
        for(int i = 0; i<DEFAULT_MATRIX_SIZE; i++)
        {
            System.arraycopy(matrixField[i], 0, returnedMatrixField[i], 0, DEFAULT_MATRIX_SIZE);
        }
        return returnedMatrixField;
    }

    /**
     *
     * @return the card played by the player and their position
     */
    public ArrayList<CardPosition> getPlayedCards()
    {
        return new ArrayList<>(playedCards);
    }


    /**
     * Add a card to the player field
     * @param card is the reference to the selected card
     * @param angleOrientation indicate the selected angle
     * @param cardToAdd is the reference to the card to add to the playerField
     * @throws InvalidCardPositionException when the player try to add a card in an invalid position
     */
    public void addCardToCell(PlayableCard card, AngleOrientation angleOrientation, PlayableCard cardToAdd) throws InvalidCardPositionException, AngleAlreadyLinkedException {
        for(int i = 0; i<DEFAULT_MATRIX_SIZE; i++)
        {
            for(int j = 0; j<DEFAULT_MATRIX_SIZE; j++)
            {
                if(card.equals(matrixField[i][j]))
                {
                    int cardToAddX = i + angleOrientation.mapEnumToX();
                    int cardToAddY = j + angleOrientation.mapEnumToY();
                    if(matrixField[cardToAddX][cardToAddY] != null)
                        throw new InvalidCardPositionException();
                    for(AngleOrientation orientation : AngleOrientation.values())
                    {
                        if(orientation.equals(AngleOrientation.NONE))
                            continue;
                        PlayableCard otherAngle = matrixField[cardToAddX + orientation.mapEnumToX()][cardToAddY + orientation.mapEnumToY()];
                        if(otherAngle != null && !otherAngle.getAngle(orientation.getOpposite()).isPlayable())
                        {
                            throw new InvalidCardPositionException();
                        }
                    }
                    if(card.getAngle(angleOrientation).isPlayable() && card.getAngle(angleOrientation).getAngleStatus().equals(AngleStatus.UNLINKED))
                    {
                        matrixField[cardToAddX][cardToAddY] = cardToAdd;
                        card.getAngle(angleOrientation).setLinkedAngle(cardToAdd.getAngle(angleOrientation.getOpposite()), AngleStatus.UNDER);
                        cardToAdd.getAngle(angleOrientation.getOpposite()).setLinkedAngle(card.getAngle(angleOrientation), AngleStatus.OVER);
                        playedCards.add(new CardPosition(cardToAdd, cardToAddX, cardToAddY));
                    }
                    else {
                        throw new InvalidCardPositionException();
                    }
                    i = DEFAULT_MATRIX_SIZE;
                    break;

                }
            }
        }
    }

    /**
     * Add the passed starterCard in the matrixField
     * @param starterCard is the selected starterCard
     */
    public void addCardToCell(StarterCard starterCard){
        matrixField[DEFAULT_MATRIX_SIZE/2][DEFAULT_MATRIX_SIZE/2] = starterCard;
        playedCards.add(new CardPosition(starterCard, DEFAULT_MATRIX_SIZE/2, DEFAULT_MATRIX_SIZE/2));
    }

    /**
     * Get a card from the playerFiled by its ID
     * @param cardId is the id of the required card
     * @return the card from the filed
     * @throws CardNotFoundException if the card is not found
     */
    public PlayableCard getCardById(int cardId) throws CardNotFoundException {
        for(int i = 0; i<DEFAULT_MATRIX_SIZE; i++)
        {
            for(int j = 0; j<DEFAULT_MATRIX_SIZE; j++) {
                if(matrixField[i][j] != null && matrixField[i][j].getCardId() == cardId)
                {
                    return matrixField[i][j];
                }
            }

        }
        throw new CardNotFoundException();
    }

}
