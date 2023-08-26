package com.omgservers.application.module.internalModule2.handler;

import com.omgservers.base.module.internal.InternalModule;
import com.omgservers.base.module.internal.impl.service.handlerService.impl.EventHandler;
import com.omgservers.dto.internalModule.UnscheduleJobRoutedRequest;
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
        final var request = new UnscheduleJobRoutedRequest(shardKey, entity);
        return internalModule.getJobRoutedService().unscheduleJob(request)
                .replaceWith(true);
    }
}
