package me.b3nw.dev.Session.Ticks;

import lombok.Getter;
import me.b3nw.dev.Session.Blobs.Blob;

public class Death {

    @Getter
    private final Blob eaten;
    @Getter
    private final Blob eater;

    public Death(Blob eaten, Blob eater) {
        this.eaten = eaten;
        this.eater = eater;
    }
}
