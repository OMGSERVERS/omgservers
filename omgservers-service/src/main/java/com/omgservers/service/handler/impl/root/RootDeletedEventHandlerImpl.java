package com.omgservers.service.handler.impl.root;

import com.omgservers.schema.module.root.root.GetRootRequest;
import com.omgservers.schema.module.root.root.GetRootResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.root.RootDeletedEventBodyModel;
import com.omgservers.schema.model.root.RootModel;
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
public class RootDeletedEventHandlerImpl implements EventHandler {

    final RootModule rootModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.ROOT_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (RootDeletedEventBodyModel) event.getBody();
        final var rootId = body.getId();

        return getRoot(rootId)
                .flatMap(root -> {
                    log.info("Deleted, {}", root);

                    return Uni.createFrom().voidItem();
                })
                .replaceWithVoid();
    }

    Uni<RootModel> getRoot(final Long id) {
        final var request = new GetRootRequest(id);
        return rootModule.getService().getRoot(request)
                .map(GetRootResponse::getRoot);
    }
}
