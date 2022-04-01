import map.Map;
import map.Region;
import processing.core.PApplet;
import thread.MapGenerator;
import validity_function.ContiguousLandmassValidity;
import validity_function.ValidityFunction;
import weight_function.Continent;
import weight_function.Marsh;
import weight_function.NoisyContinent;
import weight_function.WeightFunction;

public class Main extends PApplet {

    Map map;

    Region[] regions;
    MapGenerator[] threads;

    int mapHeight, mapWidth, mapTiles;
    int dim = 3;

    long timestamp, totalTime = 0;

    WeightFunction w;
    ValidityFunction v;

    public void settings() {
        size(1920, 1080);
    }

    public void setup() {
        frameRate(60);

        mapHeight = height / dim;
        mapWidth = width / dim;
        mapTiles = mapHeight * mapWidth;

        map = new Map(this, dim);

        w = new Continent(map);
        v = new ContiguousLandmassValidity();

        regions = new Region[4];
        regions[0] = map.createSubregion(0, 0, mapWidth / 2, mapHeight / 2);
        regions[1] = map.createSubregion(mapWidth / 2, 0, mapWidth, mapHeight / 2);
        regions[2] = map.createSubregion(0, mapHeight / 2, mapWidth / 2, mapHeight);
        regions[3] = map.createSubregion(mapWidth / 2, mapHeight / 2, mapWidth, mapHeight);

        threads = new MapGenerator[4];
        for(int i = 0; i < 4; i++) {
            threads[i] = new MapGenerator(map, regions[i], w, v);
            threads[i].start();
        }

        map.seed(mapWidth / 2,mapHeight / 2);
        timestamp = System.currentTimeMillis();
        map.generate(w, v);
    }

    public void draw() {

        if(map.getLand() >= map.maxLand) {
            map.shores();
            map.ocean();
            if(totalTime == 0)
                totalTime = System.currentTimeMillis() - timestamp;
            System.out.println(totalTime);
            map.draw(this);
        }
//        else {
//            map.iterate(w, v);
//            map.draw(this);
//        }
//        map.draw(this);


        setTitle();
//        drawOverlay();
    }

    public void setTitle() {
        surface.setTitle(map.getLand() + " / " + map.getMaxLand() + " / " + mapTiles + " | Total Tiles Examined last Frame: " + (map.brx - map.tlx) * (map.bry - map.tly) + " | Completion: " + map.getLandPercent() * 100 + "%");
    }

    public void drawOverlay() {
        stroke(0, 0, 255);
        line(width/2, 0, width/2, height);
        line(0, height/2, width, height/2);
        stroke(0);
    }


    public static void main(String[] args) {
        PApplet.main("Main");
    }

}
