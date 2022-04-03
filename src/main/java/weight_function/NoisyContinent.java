package weight_function;

import map.Map;
import map.TileType;

public class NoisyContinent implements WeightFunction {

    @Override
    public float getWeight(int x, int y, Map map) {
        return (float) Math.abs(0.1 * (map.numAdjacent(x, y, TileType.LAND) - 4));
    }
}
