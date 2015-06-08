package me.b3nw.dev.Handlers;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import me.b3nw.dev.Events.EventHandle;
import me.b3nw.dev.Events.GameEvent;
import me.b3nw.dev.Events.Handler;
import me.b3nw.dev.Events.NickAnnounceEvent;
import me.b3nw.dev.Gamemode.Gamemode;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

@Slf4j
public class GamemodeHandler extends ChannelHandlerAdapter {

    private final Gamemode gamemode;
    private final ArrayList<Handler> handlers = new ArrayList<>();

    public GamemodeHandler(Gamemode gamemode) {
        this.gamemode = gamemode;
        getHandlers();

    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof GameEvent) {
            for (Handler handler : handlers) {
                if (evt instanceof NickAnnounceEvent & handler.getHandle().type() == EventHandle.Type.NICKANNOUNCE) {
                    try {
                        handler.invoke((GameEvent) evt);
                    } catch (Throwable throwable) {
                        log.error("", throwable);
                    }
                }
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("", cause);
    }

    private void getHandlers() {
        ArrayList<Method> classMethods = new ArrayList<>(Arrays.asList(gamemode.getClass().getMethods()));

        classMethods.stream()
                .filter(method -> method.isAnnotationPresent(EventHandle.class))
                .forEach(method -> {
                    log.debug("Found event handler " + method.getName() + " - Type: " + method.getAnnotation(EventHandle.class).type());
                    try {
                        handlers.add(new Handler(method, gamemode, method.getAnnotation(EventHandle.class)));
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

}
