package it.polimi.ingsw.enumerations;


import java.io.Serializable;

public enum ClientMessageType implements Serializable {
    HEARTBEAT,
    CREATE_GAME,
    PLAY_CARD,
    DRAW_CARD,
    END_TURN,
    AVAILABLE_GAMES_REQUEST,
    ADD_PLAYER,
    SET_READY,
    SUBSCRIBE,
    CARD_INFO_REQUEST,
    SET_MARKER,
    SET_STARTER_CARD_SIDE,
    SET_SECRET_OBJECTIVE_CARD,
    CHECK_USERNAME,
    OTHER_SIDE_STARTER_CARD_REQUEST,
    OTHER_SIDE_PLAYABLE_CARD_REQUEST,
}
