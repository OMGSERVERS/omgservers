package com.omgservers.application.module.internalModule2.handler;

import com.omgservers.base.factory.JobModelFactory;
import com.omgservers.base.InternalModule;
import com.omgservers.base.impl.service.handlerHelpService.impl.EventHandler;
import com.omgservers.base.impl.operation.generateIdOperation.GenerateIdOperation;
import com.omgservers.base.impl.operation.getServersOperation.GetServersOperation;
import com.omgservers.dto.internalModule.SyncJobInternalRequest;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.MatchmakerCreatedEventBodyModel;
import com.omgservers.model.job.JobType;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class MatchmakerCreatedEventHandlerImpl implements EventHandler {

    final InternalModule internalModule;

    final GetServersOperation getServersOperation;
    final GenerateIdOperation generateIdOperation;

    final JobModelFactory jobModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.MATCHMAKER_CREATED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (MatchmakerCreatedEventBodyModel) event.getBody();
        final var id = body.getId();
        final var job = jobModelFactory.create(id, id, JobType.MATCHMAKER);
        final var request = new SyncJobInternalRequest(job);
        return internalModule.getJobInternalService().syncJob(request)
                .replaceWith(true);
    }
}
