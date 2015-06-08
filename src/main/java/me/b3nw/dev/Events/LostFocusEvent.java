package me.b3nw.dev.Events;

import io.netty.channel.ChannelHandlerContext;
import lombok.Getter;

public class LostFocusEvent extends GameEvent {

    @Getter
    private final boolean couldBeQ;

    public LostFocusEvent(ChannelHandlerContext ctx, boolean couldBeQ) {
        super(ctx);
        this.couldBeQ = couldBeQ;
    }
}
