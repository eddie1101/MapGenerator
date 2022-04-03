package map;

import processing.core.PApplet;
import validity_function.ValidityFunction;
import weight_function.WeightFunction;

import java.util.ArrayList;
import java.util.Random;

public class Region {

     Tile[][] grid;

     Map map;

     int cols, rows, origX, origY, land = 0;

     Random random;

    public Region(Tile[][] grid, Map map, int oX, int oY) {
        this.grid = grid;
        this.map = map;
        origX = oX;
        origY = oY;
        cols = this.grid.length;
        rows = this.grid[0].length;
        random = new Random();

        for(int c = 0; c < cols; c++) {
            for(int r = 0; r < rows; r++) {
                if(grid[c][r].type == TileType.LAND) land++;
            }
        }

    }

    public Tile[][] getGrid() {
        return grid;
    }

    public Tile getTile(int c, int r) {
        return grid[c][r];
    }

    public void setTile(int x, int y, TileType type) {
        grid[x][y].type = type;
    }
    public void setTile(int x, int y, Tile tile) {
        grid[x][y] = tile;
    }

    public Region createSubregion(int x, int y, int xbound, int ybound) {
        if(xbound > cols) xbound = cols;
        if(ybound > rows) ybound = rows;
        Tile[][] subregion = new Tile[xbound - x][ybound - y];

        for (int c = x; c < xbound; c++) {
            for (int r = y; r < ybound; r++) {
                subregion[c - x][r - y] = grid[c][r];
            }
        }

        return new Region(subregion, map, x, y);
    }

    public boolean adjacent(int tilex, int tiley, TileType compare) {

        Region parent = map.getMainRegion();

        for(int c = tilex - 1; c <= tilex + 1; c++) {
            for(int r = tiley - 1; r <= tiley + 1; r++) {
                if(!(c < 0 || c + origX >= parent.cols || r < 0 || r + origY >= parent.rows)) {
                    if (!(c == tilex && r == tiley) && parent.grid[c + origX][r + origY].type == compare)
                        return true;
                }
            }
        }

        return false;

    }

    public int numAdjacent(int tilex, int tiley, TileType compare) {

        int count = 0;
        for(int c = tilex - 1; c <= tilex + 1; c++) {
            for(int r = tiley - 1; r <= tiley + 1; r++) {
                if(c < 0 || c >= cols || r < 0 || r >= rows)
                    break;
                if(!(c == tilex && r == tiley) && grid[c][r].type == compare)
                    count++;
            }
        }
        return count;
    }

    public int iterate(WeightFunction w, ValidityFunction v) {

        int land = 0;

        ArrayList<Tile> validTiles = this.gatherValidTiles(v);
        for(int i = 0; i < validTiles.size(); i++) {
            Tile tile = validTiles.get(i);
            tile.setWeight(w.getWeight(tile.x, tile.y, map));
            if(random.nextFloat() < tile.weight) {
                tile.type = TileType.LAND;
                land++;
            }
        }
        map.addLand(land);
        return land;
    }

    public ArrayList<Tile> gatherValidTiles(ValidityFunction validityFunction) {
        ArrayList<Tile> validTiles = new ArrayList<>();

        for(int c = 0; c < cols; c++) {
            if(c < grid.length) {
                for (int r = 0; r < rows; r++) {
                    if (r < grid[0].length && validityFunction.isValid(c, r, this)) {
                        validTiles.add(grid[c][r]);
                    }
                }
            }
        }

        return validTiles;
    }

    public int getLand() {
        return land;
    }

    public void drawBorders(PApplet p, int size) {
        p.stroke(255, 0, 0);
        p.fill(0, 0);
        p.rect(origX * size, origY * size, cols * size, rows * size);
        p.stroke(0);
    }

}
