//package it.polimi.ingsw.network.maybeUseful.client;
//
//import it.polimi.ingsw.network.Listener;
//import it.polimi.ingsw.network.messages.userMessages.UserInputEvent;
//import it.polimi.ingsw.network.messages.userMessages.UserMessageWrapper;
//import it.polimi.ingsw.network.rmi.RMIClient;
//
//public class UserInputListener implements Listener<UserInputEvent> {
//    private final RMIClient client;
//
//    public UserInputListener(RMIClient client) {
//        this.client = client;
//    }
//
//    public void update(UserInputEvent event, Object... args) {
//        switch (event) {
//            case USERNAME_INSERTED -> {
//                client.update();
//            }
//        }
//    }
//}