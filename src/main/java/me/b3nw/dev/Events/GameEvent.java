package me.b3nw.dev.Events;

import io.netty.channel.ChannelHandlerContext;
import lombok.Getter;

public abstract class GameEvent {

    @Getter
    private final ChannelHandlerContext ctx;

    public GameEvent(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

}
