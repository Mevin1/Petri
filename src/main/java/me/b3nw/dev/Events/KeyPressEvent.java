package me.b3nw.dev.Events;

import io.netty.channel.ChannelHandlerContext;
import lombok.Getter;

public class KeyPressEvent extends GameEvent {

    @Getter
    private final Key key;

    public KeyPressEvent(ChannelHandlerContext ctx, Key key) {
        super(ctx);
        this.key = key;
    }


    public enum Key {
        SPACE,
        QDOWN,
        QUP,
        W;
    }
}
