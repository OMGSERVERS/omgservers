package com.omgservers.worker.component;

import com.omgservers.worker.WorkerConfiguration;
import com.omgservers.worker.exception.WorkerStartUpException;
import com.omgservers.worker.module.handler.HandlerModule;
import com.omgservers.worker.module.handler.lua.LuaHandlerModuleFactory;
import com.omgservers.worker.operation.getVersion.GetVersionOperation;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import io.quarkus.runtime.StartupEvent;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class HandlerHolder {

    final GetVersionOperation getVersionOperation;

    final LuaHandlerModuleFactory luaHandlerModuleFactory;

    final HandlerContainer handlerContainer;

    public HandlerModule getHandler() {
        final var handler = handlerContainer.get();
        if (Objects.isNull(handler)) {
            throw new WorkerStartUpException("Handler is not ready yet");
        }

        return handler;
    }

    @WithSpan
    void startUp(@Observes @Priority(WorkerConfiguration.START_UP_HANDLER_HOLDER_PRIORITY) StartupEvent event) {
        getVersionOperation.getVersion()
                .invoke(version -> {
                    // TODO: detect type of worker by version fields
                    final var workerModule = luaHandlerModuleFactory.build(version);
                    handlerContainer.set(workerModule);
                    log.info("Lua handler was built for versionId={}", version.getId());
                })
                .await().indefinitely();
    }
}
