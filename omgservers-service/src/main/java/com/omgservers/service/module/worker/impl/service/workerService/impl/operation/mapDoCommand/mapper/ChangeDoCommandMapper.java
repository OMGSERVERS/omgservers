package com.omgservers.service.module.worker.impl.service.workerService.impl.operation.mapDoCommand.mapper;

import com.omgservers.model.doCommand.DoCommandModel;
import com.omgservers.model.doCommand.DoCommandQualifierEnum;
import com.omgservers.model.doCommand.body.DoChangeCommandBodyModel;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.body.ChangeCommandReceivedEventBodyModel;
import com.omgservers.service.factory.EventModelFactory;
import com.omgservers.service.module.worker.impl.service.workerService.impl.operation.mapDoCommand.DoCommandMapper;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ChangeDoCommandMapper implements DoCommandMapper {

    final EventModelFactory eventModelFactory;

    @Override
    public DoCommandQualifierEnum getQualifier() {
        return DoCommandQualifierEnum.DO_CHANGE;
    }

    @Override
    public EventModel map(Long runtimeId, DoCommandModel doCommand) {
        final var commandBody = (DoChangeCommandBodyModel) doCommand.getBody();
        final var userId = commandBody.getUserId();
        final var clientId = commandBody.getClientId();
        final var message = commandBody.getMessage();

        final var eventBody = new ChangeCommandReceivedEventBodyModel(runtimeId, userId, clientId, message);
        final var eventModel = eventModelFactory.create(eventBody);
        return eventModel;
    }
}
