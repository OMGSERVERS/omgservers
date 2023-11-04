package com.omgservers.module.worker.impl.service.workerService.impl.operation.mapDoCommand.mapper;

import com.omgservers.factory.EventModelFactory;
import com.omgservers.model.doCommand.DoCommandModel;
import com.omgservers.model.doCommand.DoCommandQualifierEnum;
import com.omgservers.model.doCommand.body.DoMulticastCommandBodyModel;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.body.MulticastRequestedEventBodyModel;
import com.omgservers.module.worker.impl.service.workerService.impl.operation.mapDoCommand.DoCommandMapper;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class MulticastDoCommandMapper implements DoCommandMapper {

    final EventModelFactory eventModelFactory;

    @Override
    public DoCommandQualifierEnum getQualifier() {
        return DoCommandQualifierEnum.DO_MULTICAST;
    }

    @Override
    public EventModel map(Long runtimeId, DoCommandModel doCommand) {
        final var commandBody = (DoMulticastCommandBodyModel) doCommand.getBody();
        final var recipients = commandBody.getRecipients();
        final var message = commandBody.getMessage();

        final var eventBody = new MulticastRequestedEventBodyModel(runtimeId, recipients, message);
        final var eventModel = eventModelFactory.create(eventBody);
        return eventModel;
    }
}
