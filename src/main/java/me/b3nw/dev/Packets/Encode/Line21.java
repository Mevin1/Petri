package me.b3nw.dev.Packets.Encode;

import mikera.vectorz.Vector2;

public class Line21 extends Encode {

    private final Vector2 point;

    public Line21(Vector2 point) {
        super(ID.LINE.id);

        this.point = point;
    }

    @Override
    protected boolean Encoder() {
        data.writeShort((int) Math.round(point.getX()));
        data.writeShort((int) Math.round(point.getY()));

        return true;
    }
}
