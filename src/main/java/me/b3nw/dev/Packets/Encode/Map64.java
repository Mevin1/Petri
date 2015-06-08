package me.b3nw.dev.Packets.Encode;

import me.b3nw.dev.Session.GameMap;

public class Map64 extends Encode {

    private final GameMap map;

    public Map64(GameMap map) {
        super(ID.MAP.id);
        this.map = map;
    }

    @Override
    protected boolean Encoder() {
        data.writeDouble(map.getMinX());
        data.writeDouble(map.getMaxY());
        data.writeDouble(map.getMaxX());
        data.writeDouble(map.getMaxY());

        return true;
    }
}
