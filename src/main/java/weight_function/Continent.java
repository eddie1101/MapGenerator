package weight_function;

import map.Map;
import map.Tile;
import map.TileType;

public class Continent extends MappedWeightFunction {

    public Continent(Map map) {
        super(map);
    }

    @Override
    public float getWeight(int x, int y) {
        return 0.1f * map.numAdjacent(x, y, TileType.LAND);
    }

}
