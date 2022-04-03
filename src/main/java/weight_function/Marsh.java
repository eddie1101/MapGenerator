package weight_function;

import map.Map;
import map.TileType;

public class Marsh implements WeightFunction {

    @Override
    public float getWeight(int x, int y, Map map) {
        if(map.numAdjacent(x, y, TileType.LAND) < 2) return 0.2f;
        return 0.01f;
    }

}
