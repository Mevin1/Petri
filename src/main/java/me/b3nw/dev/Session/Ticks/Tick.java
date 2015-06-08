package me.b3nw.dev.Session.Ticks;

import lombok.Getter;
import me.b3nw.dev.Session.Blobs.Blob;

import java.util.ArrayList;

public class Tick {

    @Getter
    private final Death[] deaths;
    @Getter
    private final Blob[] blobs;
    @Getter
    private final Blob owner;

    public Tick(ArrayList<Death> deaths, ArrayList<Blob> blobs, Blob owner) {
        this.deaths = deaths.toArray(new Death[deaths.size()]);
        this.blobs = blobs.toArray(new Blob[blobs.size()]);
        this.owner = owner;
    }
}
