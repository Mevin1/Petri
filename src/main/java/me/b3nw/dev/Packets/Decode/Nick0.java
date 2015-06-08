package me.b3nw.dev.Packets.Decode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import me.b3nw.dev.Events.NickAnnounceEvent;

public class Nick0 extends Decode {

    private String nick;

    public Nick0(ByteBuf data, ChannelHandlerContext ctx) {
        super(data, ctx, false);
    }

    @Override
    protected boolean decode() {
        StringBuilder builder = new StringBuilder();

        while (data.isReadable(2)) {
            builder.append(data.readChar());
        }

        nick = builder.toString();

        return true;
    }

    @Override
    protected boolean handle() {
        ctx.fireUserEventTriggered(new NickAnnounceEvent(ctx, nick));
        return true;
    }
}
