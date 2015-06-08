package me.b3nw.dev.Packets.Decode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import me.b3nw.dev.Events.CursorEvent;
import mikera.vectorz.Vector2;

public class Cursor16 extends Decode {

    private double x;
    private double y;

    public Cursor16(ByteBuf data, ChannelHandlerContext ctx) {
        super(data, ctx, false);
    }

    @Override
    protected boolean decode() {
        x = data.readDouble();
        y = data.readDouble();

        return true;
    }

    @Override
    protected boolean handle() {
        ctx.fireUserEventTriggered(new CursorEvent(ctx, new Vector2(x, y)));
        return true;
    }
}
