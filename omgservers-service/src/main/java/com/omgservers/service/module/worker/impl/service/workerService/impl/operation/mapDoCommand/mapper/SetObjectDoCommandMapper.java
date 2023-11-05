package com.omgservers.service.module.worker.impl.service.workerService.impl.operation.mapDoCommand.mapper;

import com.omgservers.service.factory.EventModelFactory;
import com.omgservers.model.doCommand.DoCommandModel;
import com.omgservers.model.doCommand.DoCommandQualifierEnum;
import com.omgservers.model.doCommand.body.DoSetObjectCommandBodyModel;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.body.SetObjectCommandReceivedEventBodyModel;
import com.omgservers.service.module.worker.impl.service.workerService.impl.operation.mapDoCommand.DoCommandMapper;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class SetObjectDoCommandMapper implements DoCommandMapper {

    final EventModelFactory eventModelFactory;

    @Override
    public DoCommandQualifierEnum getQualifier() {
        return DoCommandQualifierEnum.DO_SET_OBJECT;
    }

    @Override
    public EventModel map(Long runtimeId, DoCommandModel doCommand) {
        final var commandBody = (DoSetObjectCommandBodyModel) doCommand.getBody();
        final var userId = commandBody.getUserId();
        final var clientId = commandBody.getClientId();
        final var object = commandBody.getObject();

        final var eventBody = new SetObjectCommandReceivedEventBodyModel(runtimeId, userId, clientId, object);
        final var eventModel = eventModelFactory.create(eventBody);
        return eventModel;
    }
}
