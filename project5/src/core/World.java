package core;


import tileengine.TETile;
import tileengine.Tileset;


public class World {

    private final TETile[][] worldGrid;

    public World(TETile[][] worldGrid) {
        this.worldGrid = worldGrid;
    }


    public TETile[][] getTiles() {
        return worldGrid;
    }


}
