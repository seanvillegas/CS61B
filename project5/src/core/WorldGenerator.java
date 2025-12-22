package core;

import tileengine.TETile;
import tileengine.Tileset;

import java.util.*;

import static utils.RandomUtils.uniform;

public class WorldGenerator {
    // create constructor that can take in a seed
    private int seed; // takes String seed input and gets converted and stored as int
    private Random random;
    private static final int WORLD_WIDTH = 80;
    private static final int WORLD_HEIGHT = 45;
    private TETile[][] world;
    private List<Room> rooms = new ArrayList<>();
    private int[] avatar1Pos;
    private int[] avatar2Pos;


    WorldGenerator(String seedString) {
        this.seed = seedString.hashCode(); // hashcode has a 1-1 mapping w seed (string)
        this.random = new Random(this.seed);
        this.world = new TETile[WORLD_WIDTH][WORLD_HEIGHT];
    }

    /**
     * Multiplayer world generator
     * @return a world that adheres to the positions of two player
     */
    public World generateWorld() {
        tileAllBlank();
        generateRooms();
        connectRooms();
        tileWallsBFS();

        int[] p1 = getAvatar1Pos();
        int[] p2 = getAvatar2Pos();
        world[p1[0]][p1[1]] = Tileset.AVATAR_A;
        world[p2[0]][p2[1]] = Tileset.AVATAR_B;

        placeCoins();
        return new World(world);
    }

    /**
     * Single player world generator
     * @return a world that adheres to the positions of one player
     */
    public World generateSinglePlayerWorld() {
        tileAllBlank();
        generateRooms();
        connectRooms();
        tileWallsBFS();

        int[] p1 = getAvatar1Pos();
        world[p1[0]][p1[1]] = Tileset.AVATAR_A;

        placeCoins();
        return new World(world);
    }

    public TETile[][] getWorldGrid() {
        return world;
    }

    public void placeCoins() {
        int totalCoins = 30;
        int numCoins = 0;
        while (totalCoins > numCoins) {
            int randX = uniform(random, 0, WORLD_WIDTH);
            int randY = uniform(random, 0, WORLD_HEIGHT);
            int avatar1X = getAvatar1Pos()[0];
            int avatar1Y = getAvatar1Pos()[1];
            int avatar2X = getAvatar2Pos()[0];
            int avatar2Y = getAvatar2Pos()[1];
            if (world[randX][randY] == Tileset.FLOOR && !(randX == avatar1X && randY == avatar1Y)
                                                        && !(randX == avatar2X && randY == avatar2Y)) {
                world[randX][randY] = Tileset.FLOWER;
                numCoins++;
            }
        }
    }

    public void generateRooms() {
        int desiredNumRooms = uniform(random, 15, 16);
        int numRooms = 0;
        int attempts = 0; // to prevent infinite loop if can't place enough rooms
        while (attempts < 5000 && desiredNumRooms > numRooms) {
            int width = uniform(random, 7, 15);
            int height = uniform(random, 5, 10);
            int x = uniform(random, 0, WORLD_WIDTH - width); // maybe change bounds here later to be more eff (hardcode)
            int y = uniform(random, 0, WORLD_HEIGHT - height);
            attempts++;
            if (validRoom(x, y, width, height)) {
                numRooms++;
                rooms.add(new Room(x, y, width, height));
                for (int i = 0; i < width; i++) {
                    for (int j = 0; j < height; j++) {
                        world[x + i][y + j] = Tileset.FLOOR;
                    }
                }
            }
        }
    }

    /* tile all walls that are needed through BFS */
    public void tileWallsBFS() {
        boolean[][] visited = new boolean[WORLD_WIDTH][WORLD_HEIGHT];
        Deque<int[]> fringe = new ArrayDeque<>(); // fringe stores int arrays which are len = 2 and contain x, y coords
        Room start = rooms.get(0); // arbitrarily starting at first room
        int startX = start.centerX();
        int startY = start.centerY();
        fringe.addLast(new int[]{startX, startY});
        visited[startX][startY] = true;
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}}; // iterating over directions for each tile BFS
        while (!fringe.isEmpty()) {
            int[] tile = fringe.removeFirst();
            int x = tile[0];
            int y = tile[1];
            for (int[] dir : directions) {
                int nx = x + dir[0]; // dir[0] == x
                int ny = y + dir[1]; // dir[0] == y
                if (isFloor(nx, ny) && !visited[nx][ny]) { // add floors to fringe for BFS
                    visited[nx][ny] = true;
                    fringe.addLast(new int[]{nx, ny});
                }
                else if (isNothing(nx, ny)) { // turns all Nothing tiles touching floor to wall
                    world[nx][ny] = Tileset.WALL;
                }
            }
        }
    }

    /* calculate distance for choosing which rooms to connect first */
    public double distanceTo(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    /* Randomly generates hallways to connect rooms */
    public void connectRooms() {
        List<Room> unconnectedRooms = new ArrayList<>(rooms);
        int roomIndex = uniform(random, 0, rooms.size());
        Room r1 = rooms.get(roomIndex);
        unconnectedRooms.remove(r1);
        while (unconnectedRooms.size() > 0) {
            Room r2 = findClosestRoom(r1, unconnectedRooms);
            buildHallway(r1, r2);
            unconnectedRooms.remove(r2);
            r1 = r2;
        }
        // randomly makes more hallways
        int extras = uniform(random, 1, 4);
        for (int i = 0; i < extras; i++) {
            roomIndex = uniform(random, 0, rooms.size());
            int roomIndex2 = uniform(random, 0, rooms.size());
            if (roomIndex2 != roomIndex) {
                Room a = rooms.get(roomIndex);
                Room b = rooms.get(roomIndex2);
                buildHallway(a, b);
            }
        }
    }

    /* makes a hallway between two given rooms */
    private void buildHallway(Room r1, Room r2) {
        int x1 = r1.centerX();
        int y1 = r1.centerY();
        int x2 = r2.centerX();
        int y2 = r2.centerY();
        if (random.nextBoolean()) { // choosing whether to minimize x or y first
            xBuildHallway(x1, y1, y2);
            yBuildHallway(y2, x1, x2);
        } else {
            yBuildHallway(y1, x1, x2);
            xBuildHallway(x2, y1, y2);
        }
    }

    /* makes a hallway in the x direction */
    private void xBuildHallway(int x, int y1, int y2) {
        int start = Math.min(y1, y2);
        int end = Math.max(y1, y2);
        for (int y = start; y <= end; y++) {
            world[x][y] = Tileset.FLOOR;
        }
    }

    /* makes a hallway in the y direction */
    private void yBuildHallway(int y, int x1, int x2) {
        int start = Math.min(x1, x2);
        int end = Math.max(x1, x2);
        for (int x = start; x <= end; x++) {
            world[x][y] = Tileset.FLOOR;
        }
    }




    /* uses the distance formula to find the closest room to a given room */
    public Room findClosestRoom(Room r1, List<Room> unconnectedRooms) {
        Room closestRoom = null;
        double minDist = WORLD_WIDTH * WORLD_HEIGHT;
        for (Room r2 : unconnectedRooms) {
            double currDist = distanceTo(r1.centerX(), r1.centerY(), r2.centerX(), r2.centerY());
            if (!r2.equals(r1) && currDist < minDist) {
                minDist = currDist;
                closestRoom = r2;
            }
        }
        return closestRoom;
    }



    /* This function returns true if the randomly generated room is in bounds and not overlapping with another room
       Goal is to create a random amount of rooms and use this to check if they are valid
       x, y are bottom left corner. Also makes sure there is space for 1 tile margin for walls all around.
     */
    boolean validRoom(int x, int y, int roomWidth, int roomHeight) {
        for (int i = -1; i < roomWidth + 1; i ++) {
            for (int j = -1; j < roomHeight + 1; j++) {
                if (!inBounds(x + i, y + j)) {
                    return false;
                }
                if (!isNothing(x + i, y + j)) {
                    return false;
                }
            }
        }
        return true;
    }

    /* This helper function checks if a tile is in the bounds of the world.
       It is used to check if randomly generated rooms are valid.  */
    boolean inBounds(int x, int y) {
        return x >= 0 && x < WORLD_WIDTH && y >= 0 && y < WORLD_HEIGHT;
    }


    /* This helper checks if a tile is a floor tile.
       It is used for checking if a room is valid and for tiling the walls. */
    boolean isFloor(int x, int y) {
        return world[x][y] == Tileset.FLOOR;
    }

    /* Checks if a tile is a NOTHING tile.
       A tile needs to be nothing to add tile instead. Used for validRoom. */
    boolean isNothing(int x, int y) {
        return world[x][y] == Tileset.NOTHING;
    }


    /* This function tiles everything in the world with NOTHING tiles */
    public void tileAllBlank() {
        for (int x = 0; x < WORLD_WIDTH; x++) {
            for (int y = 0; y < WORLD_HEIGHT; y++) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

    /* To store rooms in order to connect rooms with hallways */
    private class Room {
        int x;
        int y;
        int w;
        int h;

        Room(int x, int y, int w, int h) {
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
        }

        /* for getting center of a room in terms of x*/
        int centerX() {
            return x + w / 2;
        }

        /* for getting center of a room in terms of y*/
        int centerY() {
            return y + h / 2;
        }
    }

    public int[] getAvatar1Pos() {
        if (rooms.isEmpty()) {
            throw new IllegalStateException("Rooms not generated before avatar position requested.");
        }
        if (avatar1Pos == null) {
            int roomIndex = uniform(random, 0, rooms.size()); // get pseudorandom room index
            Room start = rooms.get(roomIndex); // get room
            int startX = start.centerX();
            int startY = start.centerY(); // getting x,y coords to put the avatar on.
            avatar1Pos = new int[]{startX, startY};
        }
        return avatar1Pos;
    }

    public int[] getAvatar2Pos() {
        if (rooms.isEmpty()) {
            throw new IllegalStateException("Rooms not generated before avatar position requested.");
        }
        if (avatar2Pos == null) {
            int[] p1 = getAvatar1Pos();
            while (avatar2Pos == null) {
                int roomIndex = uniform(random, 0, rooms.size());
                Room start = rooms.get(roomIndex);
                int x = start.centerX();
                int y = start.centerY();
                if (x != p1[0] || y != p1[1]) { // only set avatar2 if not in same pos of avatar1
                    avatar2Pos = new int[]{x, y};
                }
            }
        }
        return avatar2Pos;
    }

    public World dummyWorldGenerator(){
        this.world = new TETile[WORLD_WIDTH][WORLD_HEIGHT];

        tileAllBlank();

        // tiles floor
        for (int x = 20; x < 35; x++) {
            for (int y = 5; y < 10; y++) {
                world[x][y] = Tileset.FLOOR;
            }
        }

        /* tiles walls */
        for (int x = 20; x < 35; x++) {
            world[x][4] = Tileset.WALL;
            world[x][10] = Tileset.WALL;
        }
        for (int y = 5; y < 10; y++) {
            world[19][y] = Tileset.WALL;
            world[35][y] = Tileset.WALL;
        }

        // draws the world to the screen
        //ter.renderFrame(world);
        return new World(world);
    }
}
