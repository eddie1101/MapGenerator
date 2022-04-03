package weight_function;

import map.Map;
import map.Tile;
import map.TileType;

public class Continent implements WeightFunction{

    @Override
    public float getWeight(int x, int y, Map map) {
        return 0.1f * map.numAdjacent(x, y, TileType.LAND) + 0.1f;
    }

}
