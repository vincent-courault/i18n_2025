package i18n.utils;

public class Direction extends Coord {
    public static final Direction N = new Direction(-1, 0);
    public static final Direction S = new Direction(1, 0);
    public static final Direction E = new Direction(0, 1);
    public static final Direction W = new Direction(0, -1);
    public static final Direction NE = new Direction(-1, 1);
    public static final Direction NW = new Direction(-1, -1);
    public static final Direction SW = new Direction(1, -1);
    public static final Direction SE = new Direction(1, 1);

    public static final Direction[] ALL_DIRECTIONS = {N, NE, E, SE, S, SW, W, NW};
    public static final Direction[] CARDINAL_DIRECTIONS = {N, E, S, W};
    public static final Direction[] ORDINAL_DIRECTIONS = {NE, SE, SW, NW};

    public Direction(int diffLigne, int diffColonne) {
        super(diffLigne, diffColonne);
    }

    public int diffLigne() {
        return this.ligne();
    }

    public int diffColonne() {
        return this.colonne();
    }
}