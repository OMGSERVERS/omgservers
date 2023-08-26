package com.omgservers.application.module.internalModule2.handler;

import com.omgservers.base.factory.JobModelFactory;
import com.omgservers.base.module.internal.InternalModule;
import com.omgservers.base.module.internal.impl.service.handlerService.impl.EventHandler;
import com.omgservers.base.operation.generateId.GenerateIdOperation;
import com.omgservers.base.operation.getServers.GetServersOperation;
import com.omgservers.dto.internalModule.SyncJobRoutedRequest;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.RuntimeCreatedEventBodyModel;
import com.omgservers.model.job.JobType;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class RuntimeCreatedEventHandlerImpl implements EventHandler {

    final InternalModule internalModule;

    final GetServersOperation getServersOperation;
    final GenerateIdOperation generateIdOperation;

    final JobModelFactory jobModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.RUNTIME_CREATED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (RuntimeCreatedEventBodyModel) event.getBody();
        final var id = body.getId();
        final var job = jobModelFactory.create(id, id, JobType.RUNTIME);
        final var request = new SyncJobRoutedRequest(job);
        return internalModule.getJobRoutedService().syncJob(request)
                .replaceWith(true);
    }
}
