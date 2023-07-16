package com.omgservers.application.module.internalModule.impl.service.handlerHelpService.impl.handler;

import com.omgservers.application.module.internalModule.InternalModule;
import com.omgservers.application.module.internalModule.impl.service.handlerHelpService.impl.EventHandler;
import com.omgservers.application.module.internalModule.impl.service.jobSchedulerService.request.ScheduleJobInternalRequest;
import com.omgservers.application.module.internalModule.model.event.EventModel;
import com.omgservers.application.module.internalModule.model.event.EventQualifierEnum;
import com.omgservers.application.module.internalModule.model.event.body.JobCreatedEventBodyModel;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class JobCreatedEventHandlerImpl implements EventHandler {

    final InternalModule internalModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.JOB_CREATED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (JobCreatedEventBodyModel) event.getBody();
        final var shardKey = body.getShardKey();
        final var entity = body.getEntity();
        final var type = body.getType();
        final var request = new ScheduleJobInternalRequest(shardKey, entity, type);
        return internalModule.getJobSchedulerService().scheduleJob(request)
                .replaceWith(true);
    }
}
