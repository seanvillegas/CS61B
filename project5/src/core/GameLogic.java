package core;

import edu.princeton.cs.algs4.Out;
import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;
import tileengine.TETile;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.List;

import static core.Main.*;

/**
 * This class handles the game logic for single and multiplayer instances
 * It also will render the HUD within the game loop logic
 */
public class GameLogic {
    TETile[][] tiles;
    StringBuilder histFile;
    String seedHist;
    Player playerWorld;
    public Player.playerObj playerA;
    public Player.playerObj playerB;
    Socket socketA;
    Socket socketB;
    boolean loadPressed;

    public GameLogic(TETile[][] worldTiles, String seedHistory, StringBuilder worldHistory, Player playerObj, List<Player.playerObj> players) {
        this.tiles = worldTiles;
        this.histFile = worldHistory;
        this.seedHist = seedHistory;
        this.playerWorld = playerObj;
        // can also access from playerWorld.playerA/B
        this.playerA = players.get(0);
        this.playerB = players.get(1);
    }

    public GameLogic(TETile[][] worldTiles, String seedHistory, StringBuilder worldHistory, Player playerObj, boolean isloadPressed) {
        this.tiles = worldTiles;
        this.histFile = worldHistory;
        this.seedHist = seedHistory;
        this.playerWorld = playerObj;
        this.playerA = playerObj.playerA;
        this.loadPressed = isloadPressed;
    }

    public void runGame() {
        if (loadPressed) {
            loadGame();
        } else {
            // NOTE: this method isnt needed
            //StdDraw.enableDoubleBuffering();
            // first render
            TERenderer ter = new TERenderer();
            ter.initialize(tiles.length, tiles[0].length);
            // initialize has to have same h and w as TETile Obj so above code is safe
            ter.renderFrame(tiles);
            boolean colonPressed = false;

            while (true) {
                if (playerWorld.totalCoinsSinglePlayer()) {
                    break;
                }
                while (StdDraw.hasNextKeyTyped()) {
                    char input = Character.toLowerCase(StdDraw.nextKeyTyped());
                    switch (input) {
                        case 'w':
                            playerWorld.movePlayer(playerA, 0, 1);
                            histFile.append("w");
                            break;
                        case 'a':
                            playerWorld.movePlayer(playerA, -1, 0);
                            histFile.append("a");
                            break;
                        case 's':
                            playerWorld.movePlayer(playerA, 0, -1);
                            histFile.append("s");
                            break;
                        case 'd':
                            playerWorld.movePlayer(playerA, 1, 0);
                            histFile.append("d");
                            break;
                        case ':':
                            colonPressed = true;
                            break;
                        case 'q':
                            if (colonPressed) {
                                Out out = new Out(FILE_NAME);
                                out.println("seed=" + seedHist.toString());
                                out.println("history=" + histFile.toString());
                                System.exit(0);
                            }
                        default:
                            // user does not press a sequence of :q, thus we set it back to false
                            colonPressed = false;
                    }
                }
                // Drawing runs every iteration, even without key input
                ter.renderFrame(tiles);
                drawMouseHUD();
                StdDraw.show();
            }
            // coins are collected, exit
            onePlayerWorldWinMessage();
            System.exit(0);
        }

    }

    /**
     * If the game is loaded and saved correctly then run while loop to render last movement.
     * Then pass to runGame
     */
    public void loadGame() {
        // this method seems to overwrite the loaded game
        // I.e., if you load a game N999swwwww, it wont save the N999. It will just save the input swwwww
        loadPressed = !loadPressed;
        char[] charArray = new char[histFile.length()];
        histFile.getChars(0, histFile.length(), charArray, 0);
        for (char c : charArray) {
            switch (c) {
                case 'w':
                    playerWorld.movePlayer(playerA,0, 1);
                    break;
                case 'a':
                    playerWorld.movePlayer(playerA,-1, 0);
                    break;
                case 's':
                    playerWorld.movePlayer(playerA,0, -1);
                    break;
                case 'd':
                    playerWorld.movePlayer(playerA,1, 0);
                    break;
            }
        }
        /* then run game as normal */
        runGame();
    }

    /**
     * Convert StdDraw mouse coordinates to tile indices.
     */
    public int[] mouseToInt(double x, double y) {
        // Use floor to map continuous mouse position to tile index
        int tileX = (int) Math.floor(x);
        int tileY = (int) Math.floor(y);

        // Clamp to valid indices (defensive)
        tileX = Math.max(0, Math.min(tiles.length - 1, tileX));
        tileY = Math.max(0, Math.min(tiles[0].length - 1, tileY));

        return new int[]{tileX, tileY};
    }

    /**
     * Responsible for checking where the mouse is, and then rendering text to screen based on position
     */
    private void drawMouseHUD() {
        double mouseX = StdDraw.mouseX();
        double mouseY = StdDraw.mouseY();

        // If mouse is outside the world extents, bail out early
        if (Double.isNaN(mouseX) || Double.isNaN(mouseY)) return;

        // Convert to tile coords using the same coordinate system TERenderer uses
        int[] tile = mouseToInt(mouseX, mouseY);
        int tileX = tile[0], tileY = tile[1];

        // enforce that tile exists
        if (tileX < 0 || tileY < 0 || tileX >= tiles.length || tileY >= tiles[0].length) return;
        String text = tiles[tileX][tileY].description();

        // Draw HUD on top of the world at a fixed screen position (left-top).
        StdDraw.setFont();
        StdDraw.setPenColor(Color.CYAN);
        // Draw near top-left. Y coordinate: top row is tiles[0].length - 0.5 (center of tile)
        double hudX = 1.0;
        double hudY = tiles[0].length - 0.5;
        StdDraw.textLeft(hudX, hudY, text);
        //NOTE: for debugging
        //System.out.printf("mouse=%.2f,%.2f -> tile=%d,%d%n", mouseX, mouseY, tileX, tileY);
    }

    /**
     * Method for testing single world input
     */
    public void onePlayerWorldWinMessage() {
        StdDraw.setCanvasSize(512, 512);
        StdDraw.setXscale(0, 100);
        StdDraw.setYscale(0, 100);

        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(StdDraw.MAGENTA);
        StdDraw.setFont(LARGE_FONT);
        StdDraw.text(50, 50, "You Won!");

        // this could break something, but since its the last call I say its okay.
        StdDraw.show();
        StdDraw.pause(2000);
    }

    public void multiplayerWinMessage() {
        StdDraw.setCanvasSize(512, 512);
        StdDraw.setXscale(0, 100);
        StdDraw.setYscale(0, 100);

        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(StdDraw.MAGENTA);
        StdDraw.setFont(LARGE_FONT);

        int winner = playerWorld.comparePlayerCoins(playerA, playerB);
        if (winner < 0) {
            StdDraw.text(50, 50, playerB.getPlayerDes() + " won!");
        } else {
            StdDraw.text(50, 50, playerA.getPlayerDes() + " won!");
        }
        // SEAN: I think this could break something, but since its the last call I say its okay.
        StdDraw.show();
        StdDraw.pause(3000);
    }

    public void mapFunc(List<Socket> sockets) {
        socketA = sockets.get(0);
        socketB = sockets.get(1);
    }


    public void runMultiGame() throws IOException {
        TERenderer ter = new TERenderer();
        ter.initialize(tiles.length, tiles[0].length);
        ter.renderFrame(tiles);

        while (true) {
            if (playerWorld.totalCoinsCollected()) {
                break;
            }
            if (socketA.isConnected()) {
                InputStream in = socketA.getInputStream();

                if (in.available() > 0) {
                    int bte = in.read();
                    if (bte != -1) {
                        char key = (char) bte;
                        if (key != '\n' && key != '\r') {
                            gameLoop(key, playerA);
                        }
                    }
                }

            }
            if (socketB.isConnected()) {
                InputStream in = socketB.getInputStream();

                if (in.available() > 0) {
                    int bte = in.read();
                    if (bte != -1) {
                        char key = (char) bte;
                        if (key != '\n' && key != '\r') {
                            gameLoop(key, playerB);
                        }
                    }
                }
            }
            // rerender
            ter.renderFrame(tiles);
            drawMouseHUD();
            StdDraw.show();
        }
        // display win message
        multiplayerWinMessage();
        System.exit(0);
    }

    public void gameLoop(char c, Player.playerObj player) {
        switch (c) {
            case 'w':
                playerWorld.movePlayer(player, 0, 1);
                break;
            case 'a':
                playerWorld.movePlayer(player, -1, 0);
                break;
            case 's':
                playerWorld.movePlayer(player, 0, -1);
                break;
            case 'd':
                playerWorld.movePlayer(player, 1, 0);
                break;
            case 'q':
                System.exit(0);
        }
    }

}
