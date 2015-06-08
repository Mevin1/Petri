package me.b3nw.dev.Handlers;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import me.b3nw.dev.Exceptions.HandshakeException;
import me.b3nw.dev.Petri;

import java.nio.ByteOrder;

@Slf4j
public class AgarioHandshakeHandler extends ChannelHandlerAdapter {

    private final long timeout;
    private boolean shake1 = false;
    private boolean shake2 = false;
    private long timeConnected;

    public AgarioHandshakeHandler(long timeout) {
        this.timeout = timeout;
        this.timeConnected = System.currentTimeMillis();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (timeConnected + timeout < System.currentTimeMillis()) {
            throw new HandshakeException();
        }

        if (msg instanceof BinaryWebSocketFrame) {
            ByteBuf buf = ((BinaryWebSocketFrame) msg).content();
            buf.order(ByteOrder.LITTLE_ENDIAN);

            short id = buf.readUnsignedByte();

            if (id == 254) {
                if (shake1) {
                    throw new HandshakeException();
                } else {
                    shake1 = true;
                    log.debug("Received handshake 1");
                }
            }

            if (id == 255) {
                if (shake2) {
                    throw new HandshakeException();
                } else {
                    shake2 = true;

                    log.debug("Received handshake 2");
                }
            }

            if (shake1 && shake2) {
                ctx.channel().pipeline().replace(this, "PacketDecoder", new PacketDecodeHandler());
                ctx.channel().pipeline().addAfter("PacketDecoder", "GamemodeHandler", new GamemodeHandler(ctx.attr(Petri.channelGamemode).get()));

                //TODO Loading of gamelogic can go here

            }

            buf.release();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof HandshakeException) {
            log.info("[" + ctx.channel().remoteAddress() + "] did not send a handshake in time!");
            ctx.channel().close();
        }
    }
}
