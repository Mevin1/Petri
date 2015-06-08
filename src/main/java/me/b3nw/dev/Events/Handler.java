package me.b3nw.dev.Events;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;

@Slf4j
public class Handler {

    @Getter
    private final Method method;
    @Getter
    private final EventHandle handle;
    private final MethodHandles.Lookup lookup;
    private final MethodHandle methodHandle;
    private final EventHandler invoker;

    public Handler(Method method, Object instance, EventHandle handle) throws Throwable {
        this.method = method;
        this.handle = handle;
        this.lookup = MethodHandles.lookup();
        this.methodHandle = lookup.unreflect(method);
        MethodType type = methodHandle.type();
        MethodType factoryType = MethodType.methodType(EventHandler.class, type.parameterType(0));
        type = type.dropParameterTypes(0, 1);
        this.invoker = (EventHandler) LambdaMetafactory.metafactory(lookup,
                "handle", factoryType, type, methodHandle, type).getTarget()
                .invoke(instance);
    }

    public void invoke(GameEvent evt) throws Throwable {
        invoker.handle(evt);
    }

}
