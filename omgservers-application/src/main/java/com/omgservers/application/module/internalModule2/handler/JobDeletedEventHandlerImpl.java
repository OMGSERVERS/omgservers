package com.omgservers.application.module.internalModule2.handler;

import com.omgservers.base.InternalModule;
import com.omgservers.base.impl.service.handlerHelpService.impl.EventHandler;
import com.omgservers.dto.internalModule.UnscheduleJobInternalRequest;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.JobDeletedEventBodyModel;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class JobDeletedEventHandlerImpl implements EventHandler {

    final InternalModule internalModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.JOB_DELETED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (JobDeletedEventBodyModel) event.getBody();
        final var shardKey = body.getShardKey();
        final var entity = body.getEntity();
        final var request = new UnscheduleJobInternalRequest(shardKey, entity);
        return internalModule.getJobSchedulerService().unscheduleJob(request)
                .replaceWith(true);
    }
}
