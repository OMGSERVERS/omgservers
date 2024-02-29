package com.omgservers.service.module.worker.impl.service.workerService.impl.operation.executeDoCommand.executors;

import com.omgservers.model.doCommand.DoCommandModel;
import com.omgservers.model.doCommand.DoCommandQualifierEnum;
import com.omgservers.model.doCommand.body.DoBroadcastMessageCommandBodyModel;
import com.omgservers.model.dto.runtime.DoBroadcastMessageRequest;
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
public class BroadcastMessageDoCommandExecutor implements DoCommandExecutor {

    final RuntimeModule runtimeModule;

    @Override
    public DoCommandQualifierEnum getQualifier() {
        return DoCommandQualifierEnum.DO_BROADCAST_MESSAGE;
    }

    @Override
    public Uni<Void> execute(final Long runtimeId, final DoCommandModel doCommand) {
        final var commandBody = (DoBroadcastMessageCommandBodyModel) doCommand.getBody();
        final var message = commandBody.getMessage();

        final var request = new DoBroadcastMessageRequest(runtimeId, message);
        return runtimeModule.getDoService().doBroadcastMessage(request)
                .replaceWithVoid();
    }
}
