package map;

import processing.core.PApplet;

public class TestSubregionModification {

    Tile[][] grid = new Tile[100][];

    Tile[][] subregion = new Tile[10][];

    public TestSubregionModification() {
        for(int i = 0; i < 100; i++) {
            grid[i] = new Tile[100];
            for(int n = 0; n < 100; n++) {
                grid[i][n] = new Tile(TileType.EMPTY);
            }
        }

        for(int i = 0; i < 10; i++) {
            subregion[i] = new Tile[10];
            for(int n = 0; n < 10; n++) {
                subregion[i][n] = grid[i][n];
            }
        }
    }

    public void modifySubregion(TileType type) {
        for(int i = 0; i < 10; i++) {
            for(int n = 0; n < 10; n++) {
                subregion[i][n].type = type;
            }
        }
    }

    public void draw(PApplet p) {
        for(int i = 0; i < 100; i++) {
            for(int n = 0; n < 100; n++) {
                p.fill(grid[i][n].type.color(p));
                p.rect(i * 10, n * 10, 10, 10);
            }
        }
    }

}
