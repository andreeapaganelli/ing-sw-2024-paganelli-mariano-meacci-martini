package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.cardFactory.FilePathProvider;
import it.polimi.ingsw.enumerations.CardType;
import it.polimi.ingsw.enumerations.Resource;
import it.polimi.ingsw.enumerations.Side;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import it.polimi.ingsw.model.cards.*;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CardHandlerTest {

    private CardHandler cardHandler;
    private final FilePathProvider mockFilePathProvider = mock(FilePathProvider.class);
    private final Player mockPlayer = mock(Player.class);

    @BeforeEach
    public void setUp() {
        cardHandler = new CardHandler();
    }

    @Test
    public void importGoldCards_returnsNonEmptyList() throws CardNotImportedException {
        System.out.println(mockFilePathProvider.getGoldCardsFilePath());
        ArrayList<GoldCard> goldCards = cardHandler.importGoldCards();
        assertFalse(goldCards.isEmpty());
    }

    @Test
    public void importResourceCards_returnsNonEmptyList() throws CardNotImportedException {
        ArrayList<ResourceCard> resourceCards = cardHandler.importResourceCards();
        assertFalse(resourceCards.isEmpty());
    }

    @Test
    public void importStarterCards_returnsNonEmptyList() throws CardNotImportedException {
        ArrayList<StarterCard> starterCards = cardHandler.importStarterCards();
        assertFalse(starterCards.isEmpty());
    }

    @Test
    public void importPositionalObjectiveCards_returnsNonEmptyList() throws CardNotImportedException {
        ArrayList<ObjectiveCard> objectiveCards = cardHandler.importPositionalObjectiveCards();
        assertFalse(objectiveCards.isEmpty());
    }

    @Test
    public void importResourceObjectiveCards_returnsNonEmptyList() throws CardNotImportedException {
        ArrayList<ObjectiveCard> objectiveCards = cardHandler.importResourceObjectiveCards();
        assertFalse(objectiveCards.isEmpty());
    }

    @Test
    public void importTripleObjectiveCard_returnsNonEmptyList() throws CardNotImportedException {
        ArrayList<ObjectiveCard> objectiveCards = cardHandler.importTripleObjectiveCard();
        assertFalse(objectiveCards.isEmpty());
    }
    @Test
    public void filterResourceCards_removesBackSideCards() throws CardNotImportedException {
        ArrayList<ResourceCard> resourceCards = cardHandler.importResourceCards();
        int initialSize = resourceCards.size();
        resourceCards = cardHandler.filterResourceCards(resourceCards);
        assertTrue(resourceCards.size() < initialSize);
    }

    @Test
    public void filterGoldCards_removesBackSideCards() throws CardNotImportedException {
        ArrayList<GoldCard> goldCards = cardHandler.importGoldCards();
        int initialSize = goldCards.size();
        goldCards = cardHandler.filterGoldCards(goldCards);
        assertTrue(goldCards.size() < initialSize);
    }

    @Test
    public void filterStarterCards_removesBackSideCards() throws CardNotImportedException {
        ArrayList<StarterCard> starterCards = cardHandler.importStarterCards();
        int initialSize = starterCards.size();
        starterCards = cardHandler.filterStarterCards(starterCards);
        assertTrue(starterCards.size() < initialSize);
    }

    @Test
    public void filterObjectiveCards_removesBackSideCards() throws CardNotImportedException {
        ArrayList<ObjectiveCard> objectiveCards = cardHandler.importPositionalObjectiveCards();
        int initialSize = objectiveCards.size();
        objectiveCards = cardHandler.filterObjectiveCards(objectiveCards);
        assertTrue(objectiveCards.size() < initialSize);
    }

    @Test
    public void getOtherSideCard_returnsCorrectResourceCard() throws CardNotImportedException {
        ArrayList<ResourceCard> resourceCards = cardHandler.importResourceCards();
        ResourceCard frontCard = resourceCards.getFirst();
        ResourceCard backCard = cardHandler.getOtherSideCard(frontCard);
        assertEquals(frontCard.getCardId(), backCard.getCardId());
        assertNotEquals(frontCard.getCurrentSide(), backCard.getCurrentSide());
    }

    @Test
    public void getOtherSideCard_returnsCorrectGoldCard() throws CardNotImportedException {
        ArrayList<GoldCard> goldCards = cardHandler.importGoldCards();
        GoldCard frontCard = goldCards.getFirst();
        GoldCard backCard = cardHandler.getOtherSideCard(frontCard);
        assertEquals(frontCard.getCardId(), backCard.getCardId());
        assertNotEquals(frontCard.getCurrentSide(), backCard.getCurrentSide());
    }

    @Test
    public void getOtherSideCard_returnsCorrectStarterCard() throws CardNotImportedException{
        ArrayList<StarterCard> starterCards = cardHandler.importStarterCards();
        StarterCard frontCard = starterCards.getFirst();
        StarterCard backCard = cardHandler.getOtherSideCard(frontCard);
        assertEquals(frontCard.getCardId(), backCard.getCardId());
        assertNotEquals(frontCard.getCurrentSide(), backCard.getCurrentSide());
    }

    @Test
    public void getOtherSideCard_returnsCorrectObjectiveCard() throws CardNotImportedException {
        ArrayList<ObjectiveCard> objectiveCards = cardHandler.importPositionalObjectiveCards();
        ObjectiveCard frontCard = objectiveCards.getFirst();
        ObjectiveCard backCard = cardHandler.getOtherSideCard(frontCard);
        assertEquals(frontCard.getCardId(), backCard.getCardId());
        assertNotEquals(frontCard.getCurrentSide(), backCard.getCurrentSide());
    }

    @Test
    public void checkRequirements_returnsTrueForResourceCard() throws CardTypeMismatchException {
        PlayableCard mockCard = mock(PlayableCard.class);
        CardInfo mockInfo = mock(CardInfo.class);
        when(mockCard.accept(any())).thenReturn(mockInfo);
        when(mockInfo.getCardType()).thenReturn(CardType.RESOURCE);
        assertTrue(cardHandler.checkRequirements(mockCard, mockPlayer));
    }

    @Test
    public void checkRequirements_returnsTrueForGoldCardBack() throws CardTypeMismatchException {
        PlayableCard mockCard = mock(PlayableCard.class);
        CardInfo mockInfo = mock(CardInfo.class);
        when(mockCard.accept(any())).thenReturn(mockInfo);
        when(mockInfo.getCardType()).thenReturn(CardType.GOLD);
        when(mockCard.getCurrentSide()).thenReturn(Side.BACK);

        assertTrue(cardHandler.checkRequirements(mockCard, mockPlayer));
    }

    @Test
    public void checkRequirements_returnsTrueForGoldCardFront() throws CardTypeMismatchException {
        PlayableCard mockCard = mock(PlayableCard.class);
        CardInfo mockInfo = mock(CardInfo.class);
        when(mockCard.accept(any())).thenReturn(mockInfo);
        when(mockInfo.getCardType()).thenReturn(CardType.GOLD);
        when(mockCard.getCurrentSide()).thenReturn(Side.FRONT);
        when(mockPlayer.getResourceAmount(Resource.FUNGI)).thenReturn(1);
        ArrayList<Resource> requirements = new ArrayList<>();
        requirements.add(Resource.FUNGI);
        when(mockInfo.getRequirements()).thenReturn(requirements);

        assertTrue(cardHandler.checkRequirements(mockCard, mockPlayer));
    }

    @Test
    public void checkRequirements_returnFalseForGoldCard() throws CardTypeMismatchException {
        PlayableCard mockCard = mock(PlayableCard.class);
        CardInfo mockInfo = mock(CardInfo.class);
        when(mockCard.accept(any())).thenReturn(mockInfo);
        when(mockInfo.getCardType()).thenReturn(CardType.GOLD);
        when(mockCard.getCurrentSide()).thenReturn(Side.FRONT);
        when(mockPlayer.getResourceAmount(Resource.FUNGI)).thenReturn(0);
        ArrayList<Resource> requirements = new ArrayList<>();
        requirements.add(Resource.FUNGI);
        when(mockInfo.getRequirements()).thenReturn(requirements);

        assertFalse(cardHandler.checkRequirements(mockCard, mockPlayer));
    }

    @Test
    public void checkRequirements_throwsExceptionForTypeMismatch() {
        PlayableCard mockCard = mock(PlayableCard.class);
        CardInfo mockInfo = mock(CardInfo.class);
        when(mockCard.accept(any())).thenReturn(mockInfo);
        when(mockInfo.getCardType()).thenReturn(CardType.STARTER);
        assertThrows(CardTypeMismatchException.class, () -> cardHandler.checkRequirements(mockCard, mockPlayer));
    }

    @Test
    public void getCardInfo_returnsCorrectInfo() {
        Card mockCard = mock(Card.class);
        CardInfo mockInfo = mock(CardInfo.class);
        when(mockCard.accept(any())).thenReturn(mockInfo);
        assertEquals(mockInfo, cardHandler.getCardInfo(mockCard));
    }

    @Test
    public void getOtherSideCard_returnsCorrectCard() throws CardNotImportedException {

        ArrayList<ResourceCard> playableCards = cardHandler.importResourceCards();
        PlayableCard frontCard = playableCards.getFirst();
        PlayableCard backCard = cardHandler.getOtherSideCard(frontCard);
        assertEquals(frontCard.getCardId(), backCard.getCardId());
        assertNotEquals(frontCard.getCurrentSide(), backCard.getCurrentSide());
    }

    @Test
    public void getOtherSideCardCalledOnPlayableCard_throwsRuntimeException() throws CardNotImportedException {
        PlayableCard mockCard = mock(PlayableCard.class);
        cardHandler.importResourceCards();
        when(mockCard.getCardId()).thenReturn(1);
        when(mockCard.getCurrentSide()).thenReturn(null);
        assertThrows(RuntimeException.class, () -> cardHandler.getOtherSideCard(mockCard));
    }

    @Test
    public void getOtherSideCardCalledOnGoldCard_throwsRuntimeException() throws CardNotImportedException {
        GoldCard mockCard = mock(GoldCard.class);
        cardHandler.importGoldCards();
        when(mockCard.getCardId()).thenReturn(41);
        when(mockCard.getCurrentSide()).thenReturn(null);
        assertThrows(RuntimeException.class, () -> cardHandler.getOtherSideCard(mockCard));
    }

    @Test
    public void getOtherSideCardCalledOnResourceCard_throwsRuntimeException() throws CardNotImportedException {
        ResourceCard mockCard = mock(ResourceCard.class);
        cardHandler.importResourceCards();
        when(mockCard.getCardId()).thenReturn(1);
        when(mockCard.getCurrentSide()).thenReturn(null);
        assertThrows(RuntimeException.class, () -> cardHandler.getOtherSideCard(mockCard));
    }

    @Test
    public void getOtherSideCardCalledOnStarterCard_throwsRuntimeException() throws CardNotImportedException {
        StarterCard mockCard = mock(StarterCard.class);
        cardHandler.importStarterCards();
        when(mockCard.getCardId()).thenReturn(81);
        when(mockCard.getCurrentSide()).thenReturn(null);
        assertThrows(RuntimeException.class, () -> cardHandler.getOtherSideCard(mockCard));
    }

    @Test
    public void getOtherSideCardCalledOnObjectiveCard_throwsRuntimeException() throws CardNotImportedException {
        ObjectiveCard mockCard = mock(ObjectiveCard.class);
        cardHandler.importTripleObjectiveCard();
        when(mockCard.getCardId()).thenReturn(99);
        when(mockCard.getCurrentSide()).thenReturn(null);
        assertThrows(RuntimeException.class, () -> cardHandler.getOtherSideCard(mockCard));
    }

    @Test
    public void getOtherSideCardCalledOnPlayableCard_returnsNull() throws CardNotImportedException {
        PlayableCard mockCard = mock(PlayableCard.class);
        cardHandler.importResourceCards();
        when(mockCard.getCardId()).thenReturn(1000);
        assertNull(cardHandler.getOtherSideCard(mockCard));
    }

    @Test
    public void getOtherSideCardCalledOnGoldCard_returnsNull() throws CardNotImportedException {
        GoldCard mockCard = mock(GoldCard.class);
        cardHandler.importGoldCards();
        when(mockCard.getCardId()).thenReturn(1000);
        assertNull(cardHandler.getOtherSideCard(mockCard));
    }

    @Test
    public void getOtherSideCardCalledOnResourceCard_returnsNull() throws CardNotImportedException {
        ResourceCard mockCard = mock(ResourceCard.class);
        cardHandler.importResourceCards();
        when(mockCard.getCardId()).thenReturn(1000);
        assertNull(cardHandler.getOtherSideCard(mockCard));
    }

    @Test
    public void getOtherSideCardCalledOnStarterCard_returnsNull() throws CardNotImportedException {
        StarterCard mockCard = mock(StarterCard.class);
        cardHandler.importStarterCards();
        when(mockCard.getCardId()).thenReturn(1000);
        assertNull(cardHandler.getOtherSideCard(mockCard));
    }

    @Test
    public void getOtherSideCardCalledOnObjectiveCard_returnsNull() throws CardNotImportedException {
        ObjectiveCard mockCard = mock(ObjectiveCard.class);
        cardHandler.importTripleObjectiveCard();
        when(mockCard.getCardId()).thenReturn(1000);
        assertNull(cardHandler.getOtherSideCard(mockCard));
    }

    @Test
    void shouldReturnCorrectCardById() {
        assertThrows(NoSuchElementException.class, () -> cardHandler.getPlayableCardById(0));

        PlayableCard mockCard1 = mock(PlayableCard.class);
        when(mockCard1.getCardId()).thenReturn(1);
        when(mockCard1.getCurrentSide()).thenReturn(Side.FRONT);
        PlayableCard mockCard2 = mock(PlayableCard.class);
        when(mockCard2.getCardId()).thenReturn(1);
        when(mockCard2.getCurrentSide()).thenReturn(Side.BACK);

        ArrayList<PlayableCard> playableCards = new ArrayList<>();
        playableCards.add(mockCard1);
        playableCards.add(mockCard2);

        cardHandler.linkPlayableCards(playableCards);

        assertEquals(mockCard1, cardHandler.getPlayableCardById(1));

    }


}