package com.omgservers.application.module.internalModule.impl.service.handlerHelpService.impl.handler;

import com.omgservers.application.module.internalModule.InternalModule;
import com.omgservers.application.module.internalModule.impl.service.handlerHelpService.impl.EventHandler;
import com.omgservers.application.module.internalModule.impl.service.jobInternalService.request.DeleteJobInternalRequest;
import com.omgservers.application.module.internalModule.model.event.EventModel;
import com.omgservers.application.module.internalModule.model.event.EventQualifierEnum;
import com.omgservers.application.module.internalModule.model.event.body.MatchmakerDeletedEventBodyModel;
import com.omgservers.application.operation.getServersOperation.GetServersOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class MatchmakerDeletedEventHandlerImpl implements EventHandler {

    final InternalModule internalModule;
    final GetServersOperation getServersOperation;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.MATCHMAKER_DELETED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (MatchmakerDeletedEventBodyModel) event.getBody();
        final var uuid = body.getUuid();
        final var request = new DeleteJobInternalRequest(uuid, uuid);
        return internalModule.getJobInternalService().deleteJob(request)
                .replaceWith(true);
    }
}
