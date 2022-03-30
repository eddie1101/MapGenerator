package map;

import processing.core.PApplet;

import java.util.Random;

public enum TileType {

    EMPTY (255, 255, 255),
    SHORE (0, 100, 250),
    OCEAN (0, 0, 200),
    LAND (0, 200, 0);

    int r, g, b;

    TileType(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public int color(PApplet p) {
        return p.color(r, g, b);
    }

    public static TileType random() {
        return TileType.values()[new Random().nextInt(4)];
    }

}
