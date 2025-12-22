package core;
import multiplayer.*;
import java.awt.*;

import tileengine.TETile;
import java.util.List;

/**
 * This class should abstract and call helper classes, distributing the workload across classes.
 * Main responsibility:
 *      1. Display Main Menu and options
 *      2. Pass seed to WorldGenerator
 *      3. Handle saving/loading

 * @Authors: Sean V, Marco L
 */
public class Main {
    public static final String FILE_NAME = "save.txt";
    public static final int COINS = 30;
    public static final Font LARGE_FONT  = new Font("Arial", Font.BOLD, 30);
    public static final int CANVAS_WIDTH = 600;
    public static final int CANVAS_HEIGHT = 600;

    public static void main(String[] args) {
        MenuSaveHandler mainMenu = new MenuSaveHandler();
        // will stay in mainMenu if N is pressed
        mainMenu.run();
        boolean loadPressed =  mainMenu.isLoadPressed();
        String seedString = mainMenu.getSeedString();
        StringBuilder historyOfMenu = mainMenu.historySaved();


        /* For singe game basic functionality */
        if (mainMenu.singleGamePressed) {
            WorldGenerator worldGenerator = new WorldGenerator(seedString);
            World world = worldGenerator.generateSinglePlayerWorld();
            int[] avatarPos1 = worldGenerator.getAvatar1Pos();
            TETile[][] tiles = world.getTiles();
            Player playerState = new Player(tiles, avatarPos1);
            GameLogic game = new GameLogic(tiles, seedString, historyOfMenu, playerState, false);
            game.runGame();
        }

        /* For singe load game */
        if (loadPressed) {
            WorldGenerator worldGenerator = new WorldGenerator(seedString);
            World world = worldGenerator.generateSinglePlayerWorld();
            int[] avatarPos1 = worldGenerator.getAvatar1Pos();
            TETile[][] tiles = world.getTiles();
            Player playerState = new Player(tiles, avatarPos1);
            GameLogic game = new GameLogic(tiles, seedString, historyOfMenu, playerState, true);
            game.runGame();
        }

        /* Multiplayer Main Game */
        Server mainServer = mainMenu.getServer();

        WorldGenerator worldGenerator = new WorldGenerator(seedString);
        World world = worldGenerator.generateWorld();
        int[] avatarPos1 = worldGenerator.getAvatar1Pos(); // int[] with first elem as x pos and second as y pos
        int[] avatarPos2 = worldGenerator.getAvatar2Pos(); // int[] with first elem as x pos and second as y pos
        // get tiles for world
        TETile[][] tiles = world.getTiles();
        // Player class directly modifies the word.tiles object
        Player playerState = new Player(tiles, avatarPos1, avatarPos2);
        List<Player.playerObj>players = playerState.getPlayers();
        // Game logic to run
        GameLogic game = new GameLogic(tiles, seedString, historyOfMenu, playerState, players);
        // Pass game into Server to handle
        mainServer.authority(game);
    }
}
