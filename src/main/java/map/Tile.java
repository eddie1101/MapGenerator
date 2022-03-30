package map;

public class Tile {

    public float weight = 0;
    public int x = 0, y = 0;
    public TileType type;

    public Tile(TileType type) {
        this.type = type;
    }

    public Tile setPos(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

}
