package com.omgservers.service.module.worker.impl.service.workerService.impl.operation.executeDoCommand.executors;

import com.omgservers.model.doCommand.DoCommandModel;
import com.omgservers.model.doCommand.DoCommandQualifierEnum;
import com.omgservers.model.doCommand.body.DoStopMatchmakingCommandBodyModel;
import com.omgservers.model.dto.runtime.DoStopMatchmakingRequest;
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
public class StopMatchmakingDoCommandExecutor implements DoCommandExecutor {

    final RuntimeModule runtimeModule;

    @Override
    public DoCommandQualifierEnum getQualifier() {
        return DoCommandQualifierEnum.DO_STOP_MATCHMAKING;
    }

    @Override
    public Uni<Void> execute(Long runtimeId, DoCommandModel doCommand) {
        final var commandBody = (DoStopMatchmakingCommandBodyModel) doCommand.getBody();
        final var reason = commandBody.getReason();

        final var request = new DoStopMatchmakingRequest(runtimeId, reason);
        return runtimeModule.getDoService().doStopRuntime(request)
                .replaceWithVoid();
    }
}
