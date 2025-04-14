package com.omgservers.service.handler.impl.root;

import com.omgservers.schema.model.root.RootModel;
import com.omgservers.schema.shard.root.root.GetRootRequest;
import com.omgservers.schema.shard.root.root.GetRootResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.root.RootCreatedEventBodyModel;
import com.omgservers.service.factory.pool.PoolModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.shard.pool.PoolShard;
import com.omgservers.service.shard.root.RootShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class RootCreatedEventHandlerImpl implements EventHandler {

    final RootShard rootShard;
    final PoolShard poolShard;

    final PoolModelFactory poolModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.ROOT_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.trace("Handle event, {}", event);

        final var body = (RootCreatedEventBodyModel) event.getBody();
        final var rootId = body.getId();

        return getRoot(rootId)
                .flatMap(root -> {
                    log.debug("Created, {}", root);

                    return Uni.createFrom().voidItem();
                })
                .replaceWithVoid();
    }

    Uni<RootModel> getRoot(final Long id) {
        final var request = new GetRootRequest(id);
        return rootShard.getService().execute(request)
                .map(GetRootResponse::getRoot);
    }
}
