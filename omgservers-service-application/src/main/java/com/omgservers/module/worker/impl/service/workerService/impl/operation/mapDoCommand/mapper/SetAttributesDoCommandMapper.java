package com.omgservers.module.worker.impl.service.workerService.impl.operation.mapDoCommand.mapper;

import com.omgservers.factory.EventModelFactory;
import com.omgservers.model.doCommand.DoCommandModel;
import com.omgservers.model.doCommand.DoCommandQualifierEnum;
import com.omgservers.model.doCommand.body.DoSetAttributesCommandBodyModel;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.body.SetAttributesRequestedEventBodyModel;
import com.omgservers.module.worker.impl.service.workerService.impl.operation.mapDoCommand.DoCommandMapper;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class SetAttributesDoCommandMapper implements DoCommandMapper {

    final EventModelFactory eventModelFactory;

    @Override
    public DoCommandQualifierEnum getQualifier() {
        return DoCommandQualifierEnum.DO_SET_ATTRIBUTES;
    }

    @Override
    public EventModel map(Long runtimeId, DoCommandModel doCommand) {
        final var commandBody = (DoSetAttributesCommandBodyModel) doCommand.getBody();
        final var userId = commandBody.getUserId();
        final var clientId = commandBody.getClientId();
        final var attributes = commandBody.getAttributes();

        final var eventBody = new SetAttributesRequestedEventBodyModel(runtimeId, userId, clientId, attributes);
        final var eventModel = eventModelFactory.create(eventBody);
        return eventModel;
    }
}
