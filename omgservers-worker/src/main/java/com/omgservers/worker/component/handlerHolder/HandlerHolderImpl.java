package com.omgservers.worker.component.handlerHolder;

import com.omgservers.worker.exception.HandlerIsNotReadyYetException;
import com.omgservers.worker.exception.WorkerStartUpException;
import com.omgservers.worker.module.handler.HandlerModule;
import com.omgservers.worker.module.handler.lua.LuaHandlerModuleFactory;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class HandlerHolderImpl implements HandlerHolder {

    final LuaHandlerModuleFactory luaHandlerModuleFactory;
    final HandlerContainer handlerContainer;

    public HandlerModule getHandler() {
        final var handler = handlerContainer.get();
        if (Objects.isNull(handler)) {
            throw new HandlerIsNotReadyYetException("handler is not ready yet");
        }

        return handler;
    }
}
