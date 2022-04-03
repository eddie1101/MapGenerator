package thread;

import map.Map;
import map.Region;
import validity_function.ValidityFunction;
import weight_function.Marsh;
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
//            if(map.getLand() < map.getMaxLand() / 2)
//                region.iterate(new Marsh(), v);
//            else
                region.iterate(w, v);
        }
    }

}
