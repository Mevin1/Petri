package me.b3nw.dev.Handlers;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import me.b3nw.dev.Events.GameEvent;

@Slf4j
public class TestHandler extends ChannelHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof GameEvent) {
            log.info(evt + "");
        }
    }

}
