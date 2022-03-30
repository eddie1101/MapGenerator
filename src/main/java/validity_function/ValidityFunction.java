package validity_function;

import map.Map;
import map.Region;

@FunctionalInterface
public interface ValidityFunction {

    boolean isValid(int x, int y, Region region);

}
