package me.b3nw.dev.Packets.Encode;

public class PlayerId32 extends Encode {

    private final int playerId;

    public PlayerId32(int playerId) {
        super(ID.BLOBID.id);
        this.playerId = playerId;
    }

    @Override
    protected boolean Encoder() {
        data.writeInt(playerId);
        return true;
    }
}
