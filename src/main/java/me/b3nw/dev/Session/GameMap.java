package me.b3nw.dev.Session;

import lombok.Getter;
import me.b3nw.dev.Session.Blobs.Blob;
import net.sf.jsi.rtree.RTree;

import java.util.ArrayList;
import java.util.Iterator;

public class GameMap {

    @Getter
    private final double minX;
    @Getter
    private final double minY;
    @Getter
    private final double maxX;
    @Getter
    private final double maxY;
    private final ArrayList<Blob> blobList;
    private final RTree nodeTree;

    public GameMap(double minX, double minY, double maxX, double maxY) {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
        this.nodeTree = new RTree();
        this.nodeTree.init(null);
        this.blobList = new ArrayList<>();
    }

    public void addBlob(Blob blob) {
        nodeTree.add(blob, blob.getId());
        blobList.add(blob);
    }

    public void removeBlob(Blob blob) {
        nodeTree.delete(blob, blob.getId());
        blobList.remove(blob);
    }

    public void removeBlob(int id) {
        Iterator<Blob> blobIt = blobList.iterator();

        while (blobIt.hasNext()) {
            Blob blob = blobIt.next();

            if (blob.getId() == id) {
                nodeTree.delete(blob, blob.getId());
                blobIt.remove();
            }
        }
    }

}
