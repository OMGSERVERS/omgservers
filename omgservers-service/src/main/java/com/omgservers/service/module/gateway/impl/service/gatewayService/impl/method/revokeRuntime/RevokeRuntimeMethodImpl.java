package com.omgservers.service.module.gateway.impl.service.gatewayService.impl.method.revokeRuntime;

import com.omgservers.model.dto.gateway.RespondMessageRequest;
import com.omgservers.model.dto.gateway.RespondMessageResponse;
import com.omgservers.model.dto.gateway.RevokeRuntimeRequest;
import com.omgservers.model.dto.gateway.RevokeRuntimeResponse;
import com.omgservers.model.dto.system.SyncLogRequest;
import com.omgservers.model.dto.system.SyncLogResponse;
import com.omgservers.model.message.MessageQualifierEnum;
import com.omgservers.model.message.body.RevocationMessageBodyModel;
import com.omgservers.service.factory.LogModelFactory;
import com.omgservers.service.factory.MessageModelFactory;
import com.omgservers.service.module.gateway.GatewayModule;
import com.omgservers.service.module.system.SystemModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class RevokeRuntimeMethodImpl implements RevokeRuntimeMethod {

    final SystemModule systemModule;

    final GatewayModule gatewayModule;

    final MessageModelFactory messageModelFactory;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<RevokeRuntimeResponse> revokeRuntime(final RevokeRuntimeRequest request) {
        log.debug("Revoke runtime, request={}", request);

        final var server = request.getServer();
        final var connectionId = request.getConnectionId();
        final var runtimeId = request.getRuntimeId();

        return Uni.createFrom().voidItem()
                .map(voidItem -> gatewayModule.getConnectionService().revokeRuntime(request))
                .call(revokeRuntimeResponse -> {
                    if (revokeRuntimeResponse.getRevoked()) {
                        return respondRevocationMessage(runtimeId, server, connectionId)
                                .flatMap(response -> syncLog(request));
                    } else {
                        return Uni.createFrom().voidItem();
                    }
                });
    }

    Uni<RespondMessageResponse> respondRevocationMessage(final Long runtimeId,
                                                         final URI server,
                                                         final Long connectionId) {
        final var messageBody = new RevocationMessageBodyModel(runtimeId);
        final var messageModel = messageModelFactory.create(MessageQualifierEnum.REVOCATION_MESSAGE, messageBody);
        final var respondMessageRequest = new RespondMessageRequest(server, connectionId, messageModel);
        return gatewayModule.getGatewayService().respondMessage(respondMessageRequest);
    }

    Uni<SyncLogResponse> syncLog(RevokeRuntimeRequest request) {
        final var syncLog = logModelFactory.create("Runtime was revoked, request=" + request);
        final var syncLogRequest = new SyncLogRequest(syncLog);
        return systemModule.getLogService().syncLog(syncLogRequest);
    }
}
