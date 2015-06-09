package me.b3nw.dev.Packets.Decode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import me.b3nw.dev.Exceptions.PacketRequiresPlayerException;
import me.b3nw.dev.Petri;
import me.b3nw.dev.Session.Blobs.PlayerGroup;

import java.nio.ByteOrder;

public abstract class Decode {

    protected final ByteBuf data;
    protected final ChannelHandlerContext ctx;
    private final boolean playerRequired;
    private PlayerGroup playerGroup;

    protected Decode(ByteBuf data, ChannelHandlerContext ctx, boolean playerRequired) {
        this.data = data.order(ByteOrder.LITTLE_ENDIAN);
        this.ctx = ctx;
        this.playerRequired = playerRequired;

        if (playerRequired) {
            boolean player = ctx.channel().attr(Petri.channelHasPlayer).get();
            if (!player) {
                data.release();
                throw new PacketRequiresPlayerException();
            }
        }
    }

    public boolean decodeAndHandle() {
        boolean decode = decode();
        boolean handle = handle();

        return decode && handle;
    }

    private void addPlayerGroup2(PlayerGroup playerGroup) {
        if (playerRequired) {
            this.playerGroup = playerGroup;
        }
    }

    private PlayerGroup getPlayerGroup2() {
        return playerGroup;
    }

    protected abstract boolean decode();

    protected abstract boolean handle();

    public enum ID {
        NICK(0),
        DEBUG(2),
        CURSOR(16),
        SPACEPRESS(17),
        QPRESS(18),
        WPRESS(21),
        LOSTFOCUS(19),
        HANDSHAKE1(254),
        HANDSHAKE2(255);

        public final int id;

        ID(int id) {
            this.id = id;
        }

        public static ID find(int id) {
            for (ID id1 : ID.values()) {
                if (id1.id == id)
                    return id1;
            }

            return null;
        }
    }

}
