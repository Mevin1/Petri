package me.b3nw.dev.Packets.Encode;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import me.b3nw.dev.Exceptions.PacketEncodeException;

import java.nio.ByteOrder;

public abstract class Encode {

    final ByteBuf data;

    Encode(int id) {
        this.data = Unpooled.buffer().order(ByteOrder.LITTLE_ENDIAN);
        this.data.writeByte(id);
    }

    public BinaryWebSocketFrame encode() {
        if (Encoder()) {
            return new BinaryWebSocketFrame(data);
        } else {
            throw new PacketEncodeException();
        }
    }

    protected abstract boolean Encoder();

    public enum ID {
        TICK(16),
        SPECTATEFOLLOW(17),
        CLEARBALLS(20),
        LINE(21),
        BLOBID(32),
        FFALEADERBOARD(49),
        TEAMLEADERBOARD(50),
        MAP(64),
        DEBUG(2);

        final int id;

        ID(int id) {
            this.id = id;
        }
    }
}
