package me.b3nw.dev.Gamemode;

import lombok.extern.slf4j.Slf4j;
import me.b3nw.dev.Events.EventHandle;
import me.b3nw.dev.Events.GameEvent;
import me.b3nw.dev.Events.NickAnnounceEvent;

@Slf4j
public class Vanilla implements Gamemode {

    @EventHandle(type = EventHandle.Type.NICKANNOUNCE)
    public void nickAnnounce(GameEvent evt) {
        NickAnnounceEvent nickAnnounce = (NickAnnounceEvent) evt;

        log.info("[" + nickAnnounce.getCtx().channel().remoteAddress() + "] using name " + nickAnnounce.getNick());
    }

    @Override
    public void runTick() {

    }
}
