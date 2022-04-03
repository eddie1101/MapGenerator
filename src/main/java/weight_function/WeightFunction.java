package weight_function;

import map.Map;

@FunctionalInterface
public interface WeightFunction {
    float getWeight(int x, int y, Map map);
}
