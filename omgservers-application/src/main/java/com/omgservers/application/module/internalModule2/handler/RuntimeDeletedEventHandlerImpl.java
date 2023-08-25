package com.omgservers.application.module.internalModule2.handler;

import com.omgservers.base.InternalModule;
import com.omgservers.base.impl.service.handlerHelpService.impl.EventHandler;
import com.omgservers.base.impl.operation.getServersOperation.GetServersOperation;
import com.omgservers.dto.internalModule.DeleteJobInternalRequest;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.RuntimeCreatedEventBodyModel;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class RuntimeDeletedEventHandlerImpl implements EventHandler {

    final InternalModule internalModule;
    final GetServersOperation getServersOperation;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.RUNTIME_DELETED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (RuntimeCreatedEventBodyModel) event.getBody();
        final var id = body.getId();
        final var request = new DeleteJobInternalRequest(id, id);
        return internalModule.getJobInternalService().deleteJob(request)
                .replaceWith(true);
    }
}
