package it.polimi.ingsw.enumerations;

public enum GUIScene {
    NICKNAME,
    LOBBY,
    JOINGAME;


    public String getPath()
    {
        switch (this){
            case NICKNAME -> {
                return "/fxml/LoginScreen.fxml";
            }
            case LOBBY -> {
                return "/fxml/Lobby.fxml";
            }
            case JOINGAME -> {
                return "/fxml/JoinGameScreen.fxml";
            }

        }
        return null;
    }
}