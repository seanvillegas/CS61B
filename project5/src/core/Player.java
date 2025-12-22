package core;
import static core.Main.*;

import tileengine.TETile;
import tileengine.Tileset;
import java.util.List;

/**
 * Player will be in charge of the avatar and its deterministic position
 * Movement logic defined
 * Prevents avatar from going through walls
 */
public class Player {
    //NOTE: easier accessing (debugging) for checking bounds
    private int[] player1;
    private int[] player2;

    public playerObj playerA;
    public playerObj playerB;
    TETile[][] worldTiles;

    public class playerObj {
        private int[] cords;
        private int coinsCollected = 0;
        public TETile playerRender;
        public String playerDescription;

        public playerObj (int[] playerCord) {
            this.cords = playerCord;
            playerRender =  worldTiles[playerCord[0]][playerCord[1]];
            playerDescription = playerRender.description();
        }

        public String getPlayerDes() {
            return playerDescription;
        }
    }

    /**
     * Multiplayer world constructor
     */
    public Player(TETile[][] worldTiles, int[] player1, int[] player2) {
        // reference copy
        this.worldTiles = worldTiles;
        // deep copy
        this.player1 = new int[]{player1[0], player1[1]};
        this.player2 = new int[]{player2[0], player2[1]};
        // reference passed in
        playerA = new playerObj(player1);
        playerB = new playerObj(player2);

    }

    /**
     * Single player world constructor
     */
    public Player(TETile[][] worldTiles, int[] player1) {
        // reference copy
        this.worldTiles = worldTiles;
        // deep copy
        this.player1 = new int[]{player1[0], player1[1]};
        // reference passed in
        playerA = new playerObj(player1);
    }

    /**
     * @return list of playerObj references for classes to access
     */
    public List<playerObj> getPlayers() {
        return List.of(playerA, playerB);
    }

    /**
     * @return Single playerObj reference for classes to access
     */
    public playerObj getPlayer() {
        return playerA;
    }

    /**
     * X is vertical
     * Y is horizontal
     */
    public void movePlayer(playerObj player, int x, int y) {
        if (x == 0) {
            int tempY = player.cords[1] + y;
            if (!isWallOOB(player.cords[0], tempY)) {
                if (isCoin(player.cords[0], tempY)) {
                    player.coinsCollected++;
                }
                prevPosToFloor(player.cords[0], player.cords[1]);
                player.cords[1] += y;
                worldTiles[player.cords[0]][player.cords[1]] = player.playerRender;
            } // else do nothing
        } else if (y == 0) {
            int tempX = player.cords[0] + x;
            if (!isWallOOB(tempX, player.cords[1])) {
                if (isCoin(tempX, player.cords[1])) {
                    player.coinsCollected++;
                }
                prevPosToFloor(player.cords[0], player.cords[1]);
                player.cords[0] += x;
                worldTiles[player.cords[0]][player.cords[1]] = player.playerRender;
            } // else do nothing
        }
    }

    /**
     * Helper method that prevents invalid user input
     * @param checkX world[x][]
     * @param checkY world[x][y]
     * @return False if the character has hit a wall or is OOB
     */
    private boolean isWallOOB(int checkX, int checkY) {
        if (checkX < 0 || checkX >= worldTiles.length ||
        checkY < 0 || checkY >= worldTiles[0].length) {
            // OOB (negative or too large)
            return true;
        }

        if (worldTiles[checkX][checkY].equals(Tileset.NOTHING)) {
            return true;
        }

        if (worldTiles[checkX][checkY].equals(Tileset.WALL)) {
            return true;
        }
        return false;
    }

    /* Helpers */


    /**
     * NOTE this method can be destructive when accessing reference
     * IT DOES NOT enforce no-OOB or wall checks in world
     * The only enforcing it does it making sure that a player will not overwrite another
     *      players position
     * @param prevPosX The previous position is redrawn to floor
     * @param prevPosY The previous position is redrawn to floor
     */
    private void prevPosToFloor(int prevPosX, int prevPosY) {
        worldTiles[prevPosX][prevPosY] = Tileset.FLOOR;
    }

    /**
     * Enforce player will not overwrite another players position, by making array indexing
     * move twice compared to once with bound checks
     */
    private boolean isPlayerEatingPlayer(int checkX, int checkY) {
        // return true if next position will eat another players avatar
        if (worldTiles[checkX][checkY].equals(worldTiles[player2[0]][player2[1]])) {
            // player 1 jump twice over
            return true;
        }
        // the two players will not share the same tile
        return false;
    }

    /**
     * Keeps track of coins collected for both players, shall not exceed 30
     * If it does, call helper method to calculate who has more coins
     * @return false if the coins are all collected
     */
    public boolean totalCoinsCollected() {
        return playerA.coinsCollected + playerB.coinsCollected == COINS;
    }

    /**
     * Keeps track of coins collected for one player, shall not exceed 30
     * @return false if the coins are all collected
     */
    public boolean totalCoinsSinglePlayer() {
        return playerA.coinsCollected == COINS;
    }

    /**
     * Helper method that will
     * @return 0 if coins are equal, 1 if playerA is larger, -1 if playerB is larger
     */
    public int comparePlayerCoins(playerObj A, playerObj B) {
        return Integer.compare(A.coinsCollected, B.coinsCollected);
    }

    public boolean isCoin(int x, int y) {
        return worldTiles[x][y].equals(Tileset.FLOWER);
    }

}
