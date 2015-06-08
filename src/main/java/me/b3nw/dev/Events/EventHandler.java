package me.b3nw.dev.Events;

@FunctionalInterface
public interface EventHandler {

    void handle(GameEvent evt);

}
