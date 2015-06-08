package me.b3nw.dev.Events;

import io.netty.channel.ChannelHandlerContext;
import lombok.Getter;
import mikera.vectorz.Vector2;

public class CursorEvent extends GameEvent {

    @Getter
    private final Vector2 coords;

    public CursorEvent(ChannelHandlerContext ctx, Vector2 coords) {
        super(ctx);
        this.coords = coords;
    }

}
