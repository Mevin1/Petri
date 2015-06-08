package me.b3nw.dev.Events;

import io.netty.channel.ChannelHandlerContext;
import lombok.Getter;

public class NickAnnounceEvent extends GameEvent {

    @Getter
    private final String nick;

    public NickAnnounceEvent(ChannelHandlerContext ctx, String nick) {
        super(ctx);
        this.nick = nick;
    }

}
