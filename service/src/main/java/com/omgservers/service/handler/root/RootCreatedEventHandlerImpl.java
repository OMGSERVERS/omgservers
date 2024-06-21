package com.omgservers.service.handler.root;

import com.omgservers.model.dto.root.root.GetRootRequest;
import com.omgservers.model.dto.root.root.GetRootResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.module.root.RootCreatedEventBodyModel;
import com.omgservers.model.root.RootModel;
import com.omgservers.service.factory.pool.PoolModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.pool.PoolModule;
import com.omgservers.service.module.root.RootModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class RootCreatedEventHandlerImpl implements EventHandler {

    final RootModule rootModule;
    final PoolModule poolModule;

    final PoolModelFactory poolModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.ROOT_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (RootCreatedEventBodyModel) event.getBody();
        final var rootId = body.getId();

        return getRoot(rootId)
                .flatMap(root -> {
                    log.info("Root was created, root={}", rootId);

                    return Uni.createFrom().voidItem();
                })
                .replaceWithVoid();
    }

    Uni<RootModel> getRoot(final Long id) {
        final var request = new GetRootRequest(id);
        return rootModule.getRootService().getRoot(request)
                .map(GetRootResponse::getRoot);
    }
}
