package com.omgservers.handler;

import com.omgservers.dto.internal.SyncJobShardedRequest;
import com.omgservers.factory.JobModelFactory;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.MatchmakerCreatedEventBodyModel;
import com.omgservers.model.job.JobType;
import com.omgservers.module.internal.InternalModule;
import com.omgservers.module.internal.impl.service.handlerService.impl.EventHandler;
import com.omgservers.operation.generateId.GenerateIdOperation;
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
        final var request = new SyncJobShardedRequest(job);
        return internalModule.getJobShardedService().syncJob(request)
                .replaceWith(true);
    }
}