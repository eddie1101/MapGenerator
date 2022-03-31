package map;

import processing.core.PApplet;
import processing.core.PConstants;
import validity_function.ValidityFunction;
import weight_function.WeightFunction;

import java.util.Random;

public class Map {

    Random random = new Random();

    Region mainRegion;
    Region executionRegion;

    int tileSize, cols, rows;
    public int land = 0, maxLand;
    public int tlx, tly, brx, bry;

    boolean executionRegionUpdate = false;

    public Map(PApplet p, int tileSize) {

        this.tileSize = tileSize;

        cols = p.width / tileSize;
        rows = p.height / tileSize;

        Tile[][] grid = new Tile[cols][rows];

        for (int c = 0; c < cols; c++) {
            for (int r = 0; r < rows; r++) {
                grid[c][r] = new Tile(TileType.EMPTY).setPos(c, r);
            }
        }

        this.mainRegion = new Region(grid, this, 0, 0);
        maxLand = (int) (0.3 * cols * rows);
        tlx = 0;
        tly = 0;
        brx = 0;
        bry = 0;
    }

    public Region getExecutionRegion() {
        return executionRegion;
    }

    public Region getMainRegion() {
        return mainRegion;
    }

    public Region createSubregion(int x, int y, int xbound, int ybound) {
        return mainRegion.createSubregion(x, y, xbound, ybound);
    }

    public Region[] createSubregions(int numRegions) {
        Region[] regions = new Region[numRegions];
        for(int i = 0; i < numRegions; i++) {
            int x1 = 0; //
            int y1 = 0; // SOLVE THESE COORDINATES
            int x2 = 0; //
            int y2 = 0; //
            regions[i] = createSubregion(x1, y1, x2, y2);
        }
        return regions;
    }

    public float getLandPercent() {
        return (float) land / maxLand;
    }

    public synchronized void addLand(int land) {
        this.land += land;
    }

    public synchronized int getLand() {
        return land;
    }

    public synchronized int getMaxLand() {
        return maxLand;
    }

    public void seed(int tilex, int tiley) {
        mainRegion.setTile(tilex, tiley, TileType.LAND);

        tlx = tilex - 1;
        tly = tiley - 1;
        brx = tilex + 2;
        bry = tiley + 2;
        land++;
        executionRegion = createSubregion(tlx, tly, brx, bry);
    }

    public boolean adjacent(int tilex, int tiley, TileType compare) {
        return mainRegion.adjacent(tilex, tiley, compare);
    }

    public int numAdjacent(int tilex, int tiley, TileType compare) {
        return mainRegion.numAdjacent(tilex, tiley, compare);
    }

    public void shores() {
        Tile[][] grid = mainRegion.getGrid();
        for(int c = 0; c < cols; c++) {
            for(int r = 0; r < rows; r++) {
                if(adjacent(c, r, TileType.LAND) && grid[c][r].type == TileType.EMPTY) {
                    mainRegion.setTile(c, r, TileType.SHORE);
                }
            }
        }
    }

    public void ocean() {
        Tile[][] grid = mainRegion.getGrid();
        for(int c = 0; c < cols; c++) {
            for(int r = 0; r < rows; r++) {
                if(grid[c][r].type == TileType.EMPTY) {
                    grid[c][r].type = TileType.OCEAN;
                }
            }
        }
    }

    public int iterate(WeightFunction w, ValidityFunction v) {
        return mainRegion.iterate(w, v);
    }

    public void generate(WeightFunction w, ValidityFunction v) {
        while(land < maxLand) iterate(w, v);
    }

    public void updateExecutionBounds(int x, int y) {

        if(x > tlx && x < brx - 1 && y > tly && y < bry - 1) return;
        if(x == 0 || x == cols || y == 0 || y == rows) return;

        if(x == tlx) tlx--;
        else if(x == brx - 1) brx++;
        if(y == tly) tly--;
        else if(y == bry - 1) bry++;

        executionRegionUpdate = true;
    }

    public void updateExecutionRegion() {
        if(executionRegionUpdate) {
            executionRegion = mainRegion.createSubregion(tlx, tly, brx, bry);
            executionRegionUpdate = false;
        }
    }

    public void draw(PApplet p) {
        Tile[][] grid = mainRegion.getGrid();

        for(int c = 0; c < grid.length; c++) {
            for(int r = 0; r < grid[0].length; r++) {
                p.rectMode(PConstants.CORNER);
                p.noStroke();
                p.fill(grid[c][r].type.color(p));
                p.rect(c * tileSize, r * tileSize, tileSize, tileSize);
            }
        }

//        p.stroke(255, 0, 0);
//        p.line(tlx * tileSize, tly * tileSize, brx * tileSize, tly * tileSize);
//        p.line(tlx * tileSize, tly * tileSize, tlx * tileSize, bry * tileSize);
//        p.line(tlx * tileSize, bry * tileSize, brx * tileSize, bry * tileSize);
//        p.line(brx * tileSize, tly * tileSize, brx * tileSize, bry * tileSize);
//        p.stroke(0);
    }
}
