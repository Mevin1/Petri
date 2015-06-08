package me.b3nw.dev.Exceptions;

public class PacketIdException extends RuntimeException {

    public PacketIdException(String id) {
        super(id);
    }
}
