package me.b3nw.dev.Gamemode;

import lombok.extern.slf4j.Slf4j;
import me.b3nw.dev.Events.EventHandle;
import me.b3nw.dev.Events.GameEvent;
import me.b3nw.dev.Events.NickAnnounceEvent;
import me.b3nw.dev.Session.Blobs.Blob;
import me.b3nw.dev.Session.GameMap;
import net.sf.jsi.rtree.RTree;

import java.util.HashMap;

@Slf4j
public class Vanilla implements Gamemode {

    private final RTree blobTree;
    private final GameMap map;
    private HashMap<Integer, Blob> blobMap = new HashMap<>();
    private int lastId = 0;

    public Vanilla() {
        this.blobTree = new RTree();
        blobTree.init(null);
        this.map = new GameMap(0, 0, 1000, 1000);

    }

    @EventHandle(type = EventHandle.Type.NICKANNOUNCE)
    public void nickAnnounce(GameEvent evt) {
        NickAnnounceEvent nickAnnounce = (NickAnnounceEvent) evt;

        log.info("[" + nickAnnounce.getCtx().channel().remoteAddress() + "] using name " + nickAnnounce.getNick());
    }

    @Override
    public void runTick() {

    }

    private void addBlob(Blob blob) {
        blobTree.add(blob, blob.getId());
        blobMap.put(blob.getId(), blob);
    }

    private void removeBlob(Blob blob) {
        blobMap.remove(blob.getId());
        blobTree.delete(blob, blob.getId());
    }

    private class Player extends Blob {
        public Player(int id, double x, double y, double size, String name) {
            super(id, x, y, size);
            this.setVirus(false);
            this.setVisible(true);
            this.setName(name);
        }
    }
}
