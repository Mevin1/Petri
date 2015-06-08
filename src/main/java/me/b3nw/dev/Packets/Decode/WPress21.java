package me.b3nw.dev.Packets.Decode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import me.b3nw.dev.Events.KeyPressEvent;

@Slf4j
public class WPress21 extends Decode {

    public WPress21(ByteBuf data, ChannelHandlerContext ctx) {
        super(data, ctx, true);
    }

    @Override
    protected boolean decode() {
        return true;
    }

    @Override
    protected boolean handle() {
        ctx.fireUserEventTriggered(new KeyPressEvent(ctx, KeyPressEvent.Key.W));
        return true;
    }

}
