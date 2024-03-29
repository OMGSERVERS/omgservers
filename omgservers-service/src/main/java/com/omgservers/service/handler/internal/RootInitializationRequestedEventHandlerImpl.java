package com.omgservers.service.handler.internal;

import com.omgservers.model.dto.root.GetRootRequest;
import com.omgservers.model.dto.root.GetRootResponse;
import com.omgservers.model.dto.root.SyncRootRequest;
import com.omgservers.model.dto.root.SyncRootResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.internal.RootInitializationRequestedEventBodyModel;
import com.omgservers.model.root.RootModel;
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.RootModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.root.RootModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class RootInitializationRequestedEventHandlerImpl implements EventHandler {

    final RootModule rootModule;

    final RootModelFactory rootModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.ROOT_INITIALIZATION_REQUESTED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (RootInitializationRequestedEventBodyModel) event.getBody();
        final var rootId = body.getRootId();

        return getRoot(rootId)
                .invoke(root -> log.info("Root was already initialized, skip operation, root={}", rootId))
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithUni(t -> {
                    // Use the same idempotencyKey within all service instances
                    final var root = rootModelFactory.create(rootId, "initialization");
                    return syncRoot(root)
                            .replaceWith(root);
                })
                .replaceWithVoid();
    }

    Uni<RootModel> getRoot(final Long id) {
        final var getRootRequest = new GetRootRequest(id);
        return rootModule.getRootService().getRoot(getRootRequest)
                .map(GetRootResponse::getRoot);
    }

    Uni<Boolean> syncRoot(final RootModel root) {
        final var syncRootRequest = new SyncRootRequest(root);
        return rootModule.getRootService().syncRoot(syncRootRequest)
                .map(SyncRootResponse::getCreated)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATION)) {
                            log.warn("Idempotency was violated, object={}, {}", root, t.getMessage());
                            return Uni.createFrom().item(Boolean.FALSE);
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }
}
