package me.b3nw.dev.Packets;

import io.netty.buffer.ByteBuf;
import lombok.NonNull;

public class PacketUtil {

    public static void writeName(@NonNull String name, ByteBuf buffer, boolean writeZero) {
        String name8 = convertToUTF8(name);

        for (char c : name8.toCharArray()) {
            buffer.writeChar(c);
        }

        if (writeZero) {
            buffer.writeChar((char) 0);
        }
    }

    public static String convertToUTF8(String s) {
        String out = null;
        try {
            out = new String(s.getBytes("UTF-8"), "ISO-8859-1");
        } catch (java.io.UnsupportedEncodingException e) {
            return null;
        }
        return out;
    }

}
