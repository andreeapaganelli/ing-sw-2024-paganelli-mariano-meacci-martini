package it.polimi.ingsw.network.socket;

import it.polimi.ingsw.network.server.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class is used by the server to accept SocketConnections
 */
public class SocketConnectionHandler implements Runnable{
    private final ServerSocket serverSocket;
    private final Server server;

    /**
     * Constructor of the SocketConnectionHandler class
     * @param serverSocket the ServerSocket of the server
     * @param server a reference to the server
     */
    public SocketConnectionHandler(ServerSocket serverSocket, Server server)
    {
        this.serverSocket = serverSocket;
        this.server = server;
    }

    /**
     * This method runs the SocketConnectionHandler and waits for incoming socket connections
     * It then starts a new thread of socketClientHandler
     */
    @Override
    public void run() {
        try (ExecutorService executor = Executors.newCachedThreadPool()) {
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    SocketClientHandler socketClientHandler = new SocketClientHandler(socket, server);
                    server.connect(socketClientHandler);
                    executor.submit(socketClientHandler);
                } catch (IOException e) {
                    break;
                }
            }
            executor.shutdown();
        }
    }
}
