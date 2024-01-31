package com.omgservers.service.module.worker.impl.service.workerService.impl.operation.executeDoCommand.executors;

import com.omgservers.model.doCommand.DoCommandModel;
import com.omgservers.model.doCommand.DoCommandQualifierEnum;
import com.omgservers.model.doCommand.body.DoKickCommandBodyModel;
import com.omgservers.model.dto.runtime.DoKickClientRequest;
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
public class KickDoCommandExecutor implements DoCommandExecutor {

    final RuntimeModule runtimeModule;

    @Override
    public DoCommandQualifierEnum getQualifier() {
        return DoCommandQualifierEnum.DO_KICK;
    }

    @Override
    public Uni<Void> execute(Long runtimeId, DoCommandModel doCommand) {
        final var commandBody = (DoKickCommandBodyModel) doCommand.getBody();
        final var clientId = commandBody.getClientId();

        final var request = new DoKickClientRequest(runtimeId, clientId);
        return runtimeModule.getDoService().doKickClient(request)
                .replaceWithVoid();
    }
}
