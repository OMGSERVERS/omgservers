package com.omgservers.service.module.system.impl.bootstrap;

import com.omgservers.model.dto.system.HandleEventsRequest;
import com.omgservers.model.dto.system.SyncHandlerRequest;
import com.omgservers.model.dto.system.ViewHandlersRequest;
import com.omgservers.model.dto.system.ViewHandlersResponse;
import com.omgservers.model.handler.HandlerModel;
import com.omgservers.service.ServiceConfiguration;
import com.omgservers.service.factory.HandlerModelFactory;
import com.omgservers.service.module.system.SystemModule;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import io.quarkus.runtime.StartupEvent;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class BootstrapHandlers {

    final SystemModule systemModule;

    final GetConfigOperation getConfigOperation;

    final HandlerModelFactory handlerModelFactory;

    @WithSpan
    void startup(@Observes @Priority(ServiceConfiguration.START_UP_HANDLERS_PRIORITY) StartupEvent event) {
        final var handlerCount = getConfigOperation.getServiceConfig().handlerCount();
        if (handlerCount == 0) {
            log.warn("Handlers were disabled(==0), skip operation");
        } else {
            viewHandlers()
                    .flatMap(handlers -> {
                        if (handlers.size() == 0) {
                            return Multi.createFrom().range(0, handlerCount)
                                    .onItem().transformToUniAndConcatenate(handlerIndex -> createHandler())
                                    .collect().asList()
                                    .invoke(newHandlers -> log.info("Handlers were created, count={}",
                                            newHandlers.size()));
                        } else {
                            log.warn("Handlers were created before, skip operation, count={}", handlers.size());
                            return Uni.createFrom().voidItem();
                        }
                    })
                    .await().indefinitely();

            // Handle first events manually
            handleEvents();
            handleEvents();
        }
    }

    Uni<List<HandlerModel>> viewHandlers() {
        final var request = new ViewHandlersRequest();
        return systemModule.getHandlerService().viewHandlers(request)
                .map(ViewHandlersResponse::getHandlers);
    }

    Uni<HandlerModel> createHandler() {
        final var handler = handlerModelFactory.create();
        final var request = new SyncHandlerRequest(handler);
        return systemModule.getHandlerService().syncHandler(request)
                .replaceWith(handler);
    }

    void handleEvents() {
        final var request = new HandleEventsRequest(128);
        systemModule.getHandlerService().handleEvents(request)
                .await().indefinitely();
    }
}
