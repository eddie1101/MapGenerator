package weight_function;

import map.Tile;

@FunctionalInterface
public interface WeightFunction {
    float getWeight(int x, int y);
}
