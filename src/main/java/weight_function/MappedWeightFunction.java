package weight_function;

import map.Map;
import map.Tile;

public abstract class MappedWeightFunction implements WeightFunction {

    protected Map map;

    public MappedWeightFunction(Map map) {
        this.map = map;
    }

    @Override
    public abstract float getWeight(int x, int y);

}
