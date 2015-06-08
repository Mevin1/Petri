package me.b3nw.dev.Events;

@FunctionalInterface
public interface EventHandler {

    boolean handle(GameEvent evt);

}
