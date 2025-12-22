package core;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import multiplayer.Server;
import tileengine.TETile;

import java.awt.*;
import java.io.File;
import java.util.List;

import static core.Main.*;


/**
 * Handles main menu, seed input, and starting/loading worlds.
 * Fully visual using StdDraw.
 */
public class MenuSaveHandler {
    private StringBuilder histFile = new StringBuilder();
    //FIXME two methods that do the same thing confusing.
    private StringBuilder userSeed = new StringBuilder();
    private StringBuilder seedString;
    private String filename = "save.txt";
    private File file = new File(filename);
    private boolean loadPressed = false;
    public boolean singleGamePressed = false;
    public Server setUpServer;
    Font largeFont = new Font("Arial", Font.BOLD, 30);

    public MenuSaveHandler() {
        setupCanvas();
    }

    /** Configure the StdDraw canvas */
    private void setupCanvas() {
        StdDraw.setCanvasSize(CANVAS_WIDTH, CANVAS_HEIGHT);
        StdDraw.setXscale(0, 100);
        StdDraw.setYscale(0, 100);
    }

    /** Main menu loop */
    public void run() {
        while (true) {
            drawMenu();
            char cmd = waitForKey();

            switch (Character.toUpperCase(cmd)) {
                case 'N':
                    captureSeedInput();
                    seedString = userSeed;
                    singleGamePressed = true;
                    return;
                case 'L':
                    seedString = new StringBuilder();
                    loadHelper();
                    loadPressed = true;
                    return;
                case 'M':
                    multiPlayerLobby();
                    seedString = userSeed;
                    return;
                case 'Q':
                    System.exit(0);
                    break;
                default:
                    showMessage("Invalid input! Press N, L, or Q", 1000);
            }
        }
    }

    /**
     * This method fails if the user presses L and there is no saved file. That is okay.
     * @return users typed in string
     */
    public String getSeedString() {
        return seedString.toString();
    }

    /** Draw main menu */
    private void drawMenu() {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setFont(largeFont);
        StdDraw.text(50, 80, "CS61B: BYOW");
        StdDraw.setFont();
        StdDraw.text(50, 60, "(N) New Game");
        StdDraw.text(50, 50, "(L) Load Game");
        StdDraw.text(50, 40, "(M) Multiplayer Game");
        StdDraw.text(50, 30, "(Q) Quit Game");
        StdDraw.show();
    }

    /** Wait for a keypress */
    private char waitForKey() {
        while (!StdDraw.hasNextKeyTyped()) { }
        return StdDraw.nextKeyTyped();
    }

    public boolean isLoadPressed() {
        return loadPressed;
    }
    /** Capture seed input from user with live display */
    private void captureSeedInput() {
        userSeed.setLength(0);
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(50, 80, "Enter seed (digits) followed by S:");
        StdDraw.show();

        while (true) {
            while (!StdDraw.hasNextKeyTyped()) { }
            char key = StdDraw.nextKeyTyped();

            if (Character.isDigit(key)) {
                userSeed.append(key);
            } else if (key == 'S' || key == 's') {
                userSeed.append(key);
                break;
            }

            // redraw seed input
            StdDraw.clear(Color.BLACK);
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.text(50, 80, "Enter seed (digits) followed by S:");
            StdDraw.setPenColor(StdDraw.YELLOW);
            StdDraw.text(50, 60, userSeed.toString());
            StdDraw.show();
        }
    }

    /** Simple helper to display a message for a short time */
    private void showMessage(String msg, int ms) {
        StdDraw.clear();
        StdDraw.text(50, 50, msg);
        StdDraw.show();
        try { Thread.sleep(ms); } catch (InterruptedException ignored) {}
    }

    /** Returns history of menu/seed input */
    public StringBuilder historySaved() {
        if (histFile.length() > 0) {
            return histFile;
        } else {
            return new StringBuilder();
        }
    }

    public void loadHelper() {
        if (!file.exists()) {
            StdDraw.clear(Color.BLACK);
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.text(50, 80, "There was no loaded file. Please close window and restart game");
            StdDraw.show();
            return;
        }

        In in = new In(file);

        if (in.isEmpty()) {
            StdDraw.clear(Color.BLACK);
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.text(50, 80, "There was nothing captured in your loaded file");
            StdDraw.show();
            return;
        }

        String seedLine = in.readLine();
        String histLine = in.readLine();

        // parse seed safely
        if (seedLine.contains("=")) {
            int eq = seedLine.indexOf('=') + 1;
            String savedSeed = seedLine.substring(eq);
            seedString = new StringBuilder(savedSeed);
        } else {
            seedString = new StringBuilder(); // empty
        }
        if (histLine.contains("=")) {
            int eq = histLine.indexOf('=') + 1;
            String userInput = histLine.substring(eq);
            histFile = new StringBuilder(userInput);
        } else {
            histFile = new StringBuilder();
        }
    }

    private void multiPlayerLobby() {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(StdDraw.BOOK_BLUE);
        StdDraw.setFont(largeFont);
        StdDraw.text(50, 80, "MultiPlayer Start");
        StdDraw.pause(1500);
        // waits until 2 people connect. First person to connect is player A
        setUpServer = new Server(25565);
        StdDraw.setFont();
        captureSeedInput();
        StdDraw.show();
    }

    public Server getServer() {
        return setUpServer;
    }
}

