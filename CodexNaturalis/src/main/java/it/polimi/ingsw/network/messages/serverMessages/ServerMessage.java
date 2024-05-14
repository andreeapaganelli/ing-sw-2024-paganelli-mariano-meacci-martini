package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.enumerations.ClientMessageType;
import it.polimi.ingsw.enumerations.ServerMessageType;
import it.polimi.ingsw.enumerations.Side;

import java.io.Serializable;

public class ServerMessage implements Serializable {

    private ServerMessageType messageType;
    private Object[] messageContent;

    public ServerMessage(ServerMessageType messageType, Object ... messageContent)
    {
        this.messageType = messageType;
        this.messageContent = messageContent;
    }
    public ServerMessageType getMessageType()
    {
        return this.messageType;
    }
    public Object[] getMessageContent()
    {
        return this.messageContent;
    }
}

