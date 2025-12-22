package multiplayer;
import core.GameLogic;

import java.io.IOException;
import java.net.*;
import java.util.List;

/**
 * Server knows:
     * TETile[][] worldTiles
     * Player positions
     * WorldGeneration
     * GameLogic
     * Anything that mutates the world
 *
 */
public class Server {
    private GameLogic game;
    private ServerSocket ss; // ServerSocket only .accept(), listens to wait for clients
    Socket a;
    Socket b;

    public Server(int port) {
        try {
            this.ss = new ServerSocket(port); // lsof -i TCP:25565
        } catch (Exception e) {
            System.out.println(e);
        }
        // runs until there is a connection
        run();
    }

    /**
     * Runs until a user will connect via TCP
     */
    public void run() {
        try {
            a = ss.accept();
            b = ss.accept();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /*
    public boolean isConnected() {
        return ss.accept() != null;
    }

     */
    public void authority(GameLogic game) {
        this.game = game;

        List<Socket> sockets = List.of(a, b);

        game.mapFunc(sockets);
        //Thread.sleep(60000); // debugging, makes program not immediately exit

        try {
            game.runMultiGame();
        } catch (IOException e) {
            System.out.println(e);
        }

    }

}
