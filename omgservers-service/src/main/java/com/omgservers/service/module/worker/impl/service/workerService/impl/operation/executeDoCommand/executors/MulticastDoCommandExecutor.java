package com.omgservers.service.module.worker.impl.service.workerService.impl.operation.executeDoCommand.executors;

import com.omgservers.model.doCommand.DoCommandModel;
import com.omgservers.model.doCommand.DoCommandQualifierEnum;
import com.omgservers.model.doCommand.body.DoMulticastCommandBodyModel;
import com.omgservers.model.dto.runtime.DoMulticastMessageRequest;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.worker.impl.service.workerService.impl.operation.executeDoCommand.DoCommandExecutor;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class MulticastDoCommandExecutor implements DoCommandExecutor {

    final RuntimeModule runtimeModule;

    @Override
    public DoCommandQualifierEnum getQualifier() {
        return DoCommandQualifierEnum.DO_MULTICAST;
    }

    @Override
    public Uni<Void> execute(Long runtimeId, DoCommandModel doCommand) {
        final var commandBody = (DoMulticastCommandBodyModel) doCommand.getBody();
        final var clients = commandBody.getClients();
        final var message = commandBody.getMessage();

        final var request = new DoMulticastMessageRequest(runtimeId, clients, message);
        return runtimeModule.getDoService().doMulticastMessage(request)
                .replaceWithVoid();
    }
}
