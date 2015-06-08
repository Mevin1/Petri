package me.b3nw.dev.Handlers;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import me.b3nw.dev.Events.KeyPressEvent;
import me.b3nw.dev.Events.LostFocusEvent;
import me.b3nw.dev.Exceptions.HandshakeException;
import me.b3nw.dev.Exceptions.PacketIdException;
import me.b3nw.dev.Exceptions.PacketRequiresPlayerException;
import me.b3nw.dev.Packets.Decode.*;
import me.b3nw.dev.Petri;

import java.nio.ByteOrder;

@Slf4j
public class PacketDecodeHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof BinaryWebSocketFrame) {
            ByteBuf buf = ((BinaryWebSocketFrame) msg).content();
            buf.order(ByteOrder.LITTLE_ENDIAN);

            short id = buf.readUnsignedByte();

            Decode.ID decodedId = Decode.ID.find(id);

            if (decodedId == null)
                throw new PacketIdException("" + id);

            Decode decode = null;
            buf.discardReadBytes();

            switch (decodedId) {
                case HANDSHAKE1:
                    throw new HandshakeException();
                case HANDSHAKE2:
                    throw new HandshakeException();
                case CURSOR:
                    decode = new Cursor16(buf, ctx);
                    break;
                case SPACEPRESS:
                    new QPress18(buf, ctx).decodeAndHandle();
                    break;
                case QPRESS:
                    new QPress18(buf, ctx).decodeAndHandle();
                    break;
                case WPRESS:
                    new WPress21(buf, ctx).decodeAndHandle();
                    break;
                case NICK:
                    decode = new Nick0(buf, ctx);
                    break;
                case LOSTFOCUS:
                    boolean player = ctx.channel().attr(Petri.channelHasPlayer).get();

                    if (!player) {
                        ctx.fireUserEventTriggered(new LostFocusEvent(ctx, false));
                    } else {
                        ctx.fireUserEventTriggered(new LostFocusEvent(ctx, true));
                        ctx.fireUserEventTriggered(new KeyPressEvent(ctx, KeyPressEvent.Key.QUP));
                    }
                    break;
                default:
                    log.error("Something dun goofed..");
                    break;
            }

            if (decode != null) {
                if (!decode.decodeAndHandle()) {
                    //TODO Throw exception
                }
            }

            buf.release();

        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof PacketIdException) {
            log.info("[" + ctx.channel().remoteAddress() + "] sent a malformed packet (" + cause.getMessage() + "). Disconnecting!");
        } else if (cause instanceof PacketRequiresPlayerException) {
            log.info("[" + ctx.channel().remoteAddress() + "] sent packet specific to players. Disconnecting!");
        } else if (cause instanceof HandshakeException) {
            log.info("[" + ctx.channel().remoteAddress() + "] sent a bad handshake. Disconnecting!");
        } else {
            log.error("", cause);
        }

        ctx.channel().close();
    }

}
