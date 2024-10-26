package it.polimi.ingsw.enumerations;


import java.io.Serializable;

/**
 * This enumeration represents the type of message sent by the client.
 */
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
    OTHER_SIDE_PLAYABLE_CARD_REQUEST, SET_USERNAME, RECONNECT_PLAYER, QUIT_GAME, GET_CARD_BY_ID, GET_READY_STATUS, SEND_CHAT_MESSAGE,
}
