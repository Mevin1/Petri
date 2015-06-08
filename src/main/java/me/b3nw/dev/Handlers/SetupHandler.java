package me.b3nw.dev.Handlers;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SetupHandler extends ChannelHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt == WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE) {
            ByteBuf buf = ctx.alloc().buffer();
            buf.writeBytes("HelloHelloHello".getBytes());

            ctx.channel().writeAndFlush(new BinaryWebSocketFrame(buf));

            ctx.pipeline().addAfter("wsdecoder", "packetdecoder", new AgarioHandshakeHandler(500));

            ctx.pipeline().remove(this);
        }

    }
}
