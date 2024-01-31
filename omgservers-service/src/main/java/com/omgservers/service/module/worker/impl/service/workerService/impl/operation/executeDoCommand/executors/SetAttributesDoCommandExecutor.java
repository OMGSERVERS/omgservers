package com.omgservers.service.module.worker.impl.service.workerService.impl.operation.executeDoCommand.executors;

import com.omgservers.model.doCommand.DoCommandModel;
import com.omgservers.model.doCommand.DoCommandQualifierEnum;
import com.omgservers.model.doCommand.body.DoSetAttributesCommandBodyModel;
import com.omgservers.model.dto.runtime.DoSetAttributesRequest;
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
public class SetAttributesDoCommandExecutor implements DoCommandExecutor {

    final RuntimeModule runtimeModule;

    @Override
    public DoCommandQualifierEnum getQualifier() {
        return DoCommandQualifierEnum.DO_SET_ATTRIBUTES;
    }

    @Override
    public Uni<Void> execute(Long runtimeId, DoCommandModel doCommand) {
        final var commandBody = (DoSetAttributesCommandBodyModel) doCommand.getBody();
        final var clientId = commandBody.getClientId();
        final var attributes = commandBody.getAttributes();

        final var request = new DoSetAttributesRequest(runtimeId, clientId, attributes);
        return runtimeModule.getDoService().doSetAttributes(request)
                .replaceWithVoid();
    }
}
