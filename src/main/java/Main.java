import map.Map;
import map.Region;
import processing.core.PApplet;
import thread.MapGenerator;
import validity_function.ContiguousLandmassValidity;
import validity_function.RegionalValidity;
import validity_function.ValidityFunction;
import weight_function.Continent;
import weight_function.WeightFunction;


public class Main extends PApplet {

    Map map;

    Region[] regions;
    MapGenerator[] threads;

    //SETTINGS
    int targetFramerate = 165;

    int tileSizePx = 2;
    int numThreads = 4;

    boolean showDraw = true;
    boolean smoothingThread = true;

    WeightFunction w = new Continent();
    ValidityFunction v = new ContiguousLandmassValidity();
    //SETTINGS

    int mapHeight, mapWidth, mapTiles;
    long timestamp, totalTime = 0;
    boolean generationComplete = false;

    public void settings() {
        size(1920, 1080);
    }

    public void setup() {
        frameRate(targetFramerate);

        mapHeight = height / tileSizePx;
        mapWidth = width / tileSizePx;
        mapTiles = mapHeight * mapWidth;

        map = new Map(this, tileSizePx, w);

        regions = map.createSubregions(numThreads);

        map.seed(mapWidth / 2,mapHeight / 2);

        timestamp = System.currentTimeMillis();

        threads = new MapGenerator[regions.length];
        for(int i = 0; i < regions.length; i++) {
            threads[i] = new MapGenerator(map, regions[i], w, v);
            threads[i].start();
        }

        if(smoothingThread) {
            new MapGenerator(map, map.getMainRegion(), w, v).start();
        }
    }

    public void draw() {

        if(showDraw) {
            map.draw(this);
        }

        if(map.getLand() >= map.getMaxLand()) {
            map.shores();
            map.ocean();
            generationComplete = true;
        }

        if(!showDraw && generationComplete) {
            map.draw(this);
        }

        if(generationComplete && totalTime == 0) {
            totalTime = System.currentTimeMillis() - timestamp;
            System.out.println(totalTime);
        }

        setInfoTitle();
        drawRegionBorders();
    }

    public void setInfoTitle() {
        surface.setTitle(map.getLand() + " / " + map.getMaxLand() + " / " + mapTiles + /*" | Total Tiles Examined last Frame: " + (map.brx - map.tlx) * (map.bry - map.tly) + */ " | Completion: " + map.getLandPercent() * 100 + "%");
    }

    public void drawOverlay() {
        stroke(0, 0, 255);
        line(width/2, 0, width/2, height);
        line(0, height/2, width, height/2);
        stroke(0);
    }

    public void drawRegionBorders() {
        for(Region region: regions) {
            region.drawBorders(this, tileSizePx);
        }
    }

    public static void main(String[] args) {
        PApplet.main("Main");
    }

}
