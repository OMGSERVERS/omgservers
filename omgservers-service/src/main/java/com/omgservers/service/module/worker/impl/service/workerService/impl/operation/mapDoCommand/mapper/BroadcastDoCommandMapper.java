package com.omgservers.service.module.worker.impl.service.workerService.impl.operation.mapDoCommand.mapper;

import com.omgservers.service.factory.EventModelFactory;
import com.omgservers.model.doCommand.DoCommandModel;
import com.omgservers.model.doCommand.DoCommandQualifierEnum;
import com.omgservers.model.doCommand.body.DoBroadcastCommandBodyModel;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.body.BroadcastCommandReceivedEventBodyModel;
import com.omgservers.service.module.worker.impl.service.workerService.impl.operation.mapDoCommand.DoCommandMapper;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class BroadcastDoCommandMapper implements DoCommandMapper {

    final EventModelFactory eventModelFactory;

    @Override
    public DoCommandQualifierEnum getQualifier() {
        return DoCommandQualifierEnum.DO_BROADCAST;
    }

    @Override
    public EventModel map(final Long runtimeId, final DoCommandModel doCommand) {
        final var commandBody = (DoBroadcastCommandBodyModel) doCommand.getBody();
        final var message = commandBody.getMessage();

        final var eventBody = new BroadcastCommandReceivedEventBodyModel(runtimeId, message);
        final var eventModel = eventModelFactory.create(eventBody);
        return eventModel;
    }
}
