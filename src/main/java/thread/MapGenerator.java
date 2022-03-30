package thread;

import map.Map;
import map.Region;
import validity_function.ValidityFunction;
import weight_function.WeightFunction;

public class MapGenerator extends Thread {

    Map map;
    Region region;

    WeightFunction w;
    ValidityFunction v;

    public MapGenerator(Map map, Region region, WeightFunction w, ValidityFunction v) {
        this.map = map;
        this.region = region;

        this.w = w;
        this.v = v;
    }

    public void run() {
        while(map.getLand() < map.getMaxLand()) {
            region.iterate(w, v);
        }
    }

}
