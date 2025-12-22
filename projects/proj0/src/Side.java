// Side.java
public enum Side {
    NORTH, EAST, SOUTH, WEST;

    /** Returns the side opposite of this side. */
    public Side opposite() {
        switch (this) {
            case NORTH: return SOUTH;
            case SOUTH: return NORTH;
            case EAST:  return WEST;
            case WEST:  return EAST;
            default: throw new IllegalArgumentException("Invalid side: " + this);
        }
    }

    /** Returns the index of this side, useful for rotation logic. */
    public int toInt() {
        switch (this) {
            case NORTH: return 0;
            case EAST:  return 1;
            case SOUTH: return 2;
            case WEST:  return 3;
            default: throw new IllegalArgumentException("Invalid side: " + this);
        }
    }
}
