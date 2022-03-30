package validity_function;

import map.Map;
import map.Region;
import map.TileType;

public class RegionalValidity implements ValidityFunction {

    @Override
    public boolean isValid(int x, int y, Region region) {
        return region.getTile(x, y).type != TileType.LAND;
    }

}
