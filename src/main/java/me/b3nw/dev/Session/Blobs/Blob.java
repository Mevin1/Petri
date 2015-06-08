package me.b3nw.dev.Session.Blobs;

import lombok.Getter;
import lombok.Setter;
import net.sf.jsi.Rectangle;

import java.util.ArrayList;

public abstract class Blob extends Rectangle {

    private final ArrayList<Blob> knownBlobs;
    @Getter
    private int id;
    @Getter
    private double x;
    @Getter
    private double y;
    @Getter
    private double size;
    @Getter
    private String name;
    @Getter
    @Setter
    private boolean isVisible = true;
    @Getter
    @Setter
    private boolean isVirus;
    @Getter
    @Setter
    private int r = 0, g = 0, b = 0;

    public Blob(int id, double x, double y, double size) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.size = size;
        this.name = "";
        this.knownBlobs = new ArrayList<>();
        this.isVirus = false;
        calcBounds();
    }

    public void setX(double x) {
        this.x = x;
        calcBounds();
    }

    public void setY(double y) {
        this.y = y;
        calcBounds();
    }

    public void setSize(double size) {
        this.size = size;
        calcBounds();
    }

    public void setName(String name) {
        this.name = name;
        clearKnownBlobs(false);
    }

    public void addKnownBlob(Blob blob) {
        knownBlobs.add(blob);
    }

    public void removeKnownBlob(Blob blob) {
        knownBlobs.remove(blob);
    }

    public void clearKnownBlobs(boolean addSelf) {
        knownBlobs.clear();

        if (addSelf)
            addKnownBlob(this);
    }

    public boolean knowsBlob(Blob blob) {
        return knownBlobs.contains(blob);
    }

    private void calcBounds() {
        double x1 = x - (size / 2);
        double y1 = y - (size / 2);
        double x2 = x + (size / 2);
        double y2 = y + (size / 2);

        set((float) x1, (float) y1, (float) x2, (float) y2);
    }

    public Blob[] calcBlobDeaths(Blob[] toCompare) {
        ArrayList<Blob> deathList = new ArrayList<>();

        deathList.addAll(knownBlobs);
        deathList.removeIf(blob -> {
            for (int i = 0; i < toCompare.length; i++) {
                if (blob == toCompare[i]) {
                    return true;
                }
            }

            return false;
        });

        return deathList.toArray(new Blob[deathList.size()]);
    }
}
