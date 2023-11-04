package com.omgservers.module.gateway.impl.service.gatewayService.impl.method.assignRuntime;

import com.omgservers.model.dto.gateway.AssignRuntimeRequest;
import com.omgservers.model.dto.gateway.AssignRuntimeResponse;
import com.omgservers.model.dto.gateway.RespondMessageRequest;
import com.omgservers.model.dto.gateway.RespondMessageResponse;
import com.omgservers.model.dto.system.SyncLogRequest;
import com.omgservers.model.dto.system.SyncLogResponse;
import com.omgservers.model.message.MessageQualifierEnum;
import com.omgservers.model.message.body.AssignmentMessageBodyModel;
import com.omgservers.module.gateway.GatewayModule;
import com.omgservers.factory.MessageModelFactory;
import com.omgservers.module.system.SystemModule;
import com.omgservers.factory.LogModelFactory;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class AssignRuntimeMethodImpl implements AssignRuntimeMethod {

    final SystemModule systemModule;

    final GatewayModule gatewayModule;

    final MessageModelFactory messageModelFactory;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<AssignRuntimeResponse> assignRuntime(AssignRuntimeRequest request) {
        final var server = request.getServer();
        final var connectionId = request.getConnectionId();
        final var runtimeId = request.getAssignedRuntime().getRuntimeId();

        return Uni.createFrom().voidItem()
                .map(voidItem -> gatewayModule.getConnectionService().assignRuntime(request))
                .call(assignRuntimeResponse -> {

                    log.info("Runtime was assigned, runtimeId={}, connectionId={}", runtimeId, connectionId);

                    if (assignRuntimeResponse.getAssigned()) {
                        return respondAssignmentMessage(runtimeId, server, connectionId)
                                .flatMap(response -> syncLog(request));
                    } else {
                        return Uni.createFrom().voidItem();
                    }
                });
    }

    Uni<RespondMessageResponse> respondAssignmentMessage(final Long runtimeId,
                                                         final URI server,
                                                         final Long connectionId) {
        final var messageBody = new AssignmentMessageBodyModel(runtimeId);
        final var messageModel = messageModelFactory.create(MessageQualifierEnum.ASSIGNMENT_MESSAGE, messageBody);
        final var respondMessageRequest = new RespondMessageRequest(server, connectionId, messageModel);
        return gatewayModule.getGatewayService().respondMessage(respondMessageRequest);
    }

    Uni<SyncLogResponse> syncLog(AssignRuntimeRequest request) {
        final var syncLog = logModelFactory.create("Runtime was assigned, request=" + request);
        final var syncLogRequest = new SyncLogRequest(syncLog);
        return systemModule.getLogService().syncLog(syncLogRequest);
    }
}
