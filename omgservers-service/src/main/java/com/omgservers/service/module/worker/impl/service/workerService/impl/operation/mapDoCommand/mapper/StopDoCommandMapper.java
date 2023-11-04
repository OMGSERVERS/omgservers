package com.omgservers.service.module.worker.impl.service.workerService.impl.operation.mapDoCommand.mapper;

import com.omgservers.service.factory.EventModelFactory;
import com.omgservers.model.doCommand.DoCommandModel;
import com.omgservers.model.doCommand.DoCommandQualifierEnum;
import com.omgservers.model.doCommand.body.DoStopCommandBodyModel;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.body.StopRequestedEventBodyModel;
import com.omgservers.service.module.worker.impl.service.workerService.impl.operation.mapDoCommand.DoCommandMapper;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class StopDoCommandMapper implements DoCommandMapper {

    final EventModelFactory eventModelFactory;

    @Override
    public DoCommandQualifierEnum getQualifier() {
        return DoCommandQualifierEnum.DO_STOP;
    }

    @Override
    public EventModel map(Long runtimeId, DoCommandModel doCommand) {
        final var commandBody = (DoStopCommandBodyModel) doCommand.getBody();
        final var reason = commandBody.getReason();

        final var eventBody = new StopRequestedEventBodyModel(runtimeId, reason);
        final var eventModel = eventModelFactory.create(eventBody);
        return eventModel;
    }
}
