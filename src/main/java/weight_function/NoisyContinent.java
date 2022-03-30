package weight_function;

import map.Map;
import map.TileType;

public class NoisyContinent extends MappedWeightFunction {

    public NoisyContinent(Map map){
        super(map);
    }

    public float getWeight(int x, int y) {
        return (float) Math.abs(0.1 * (map.numAdjacent(x, y, TileType.LAND) - 4));
    }
}
