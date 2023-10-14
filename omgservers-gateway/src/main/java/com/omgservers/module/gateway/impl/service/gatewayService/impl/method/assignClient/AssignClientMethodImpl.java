package com.omgservers.module.gateway.impl.service.gatewayService.impl.method.assignClient;

import com.omgservers.dto.gateway.AssignClientRequest;
import com.omgservers.dto.gateway.AssignClientResponse;
import com.omgservers.dto.gateway.RespondMessageRequest;
import com.omgservers.dto.gateway.RespondMessageResponse;
import com.omgservers.dto.internal.SyncLogRequest;
import com.omgservers.dto.internal.SyncLogResponse;
import com.omgservers.model.message.MessageQualifierEnum;
import com.omgservers.model.message.body.WelcomeMessageBodyModel;
import com.omgservers.module.gateway.GatewayModule;
import com.omgservers.module.gateway.factory.MessageModelFactory;
import com.omgservers.module.system.SystemModule;
import com.omgservers.module.system.factory.LogModelFactory;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class AssignClientMethodImpl implements AssignClientMethod {

    final SystemModule systemModule;

    final GatewayModule gatewayModule;

    final MessageModelFactory messageModelFactory;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<AssignClientResponse> assignClient(final AssignClientRequest request) {
        final var server = request.getServer();
        final var connectionId = request.getConnectionId();
        return Uni.createFrom().voidItem()
                .map(voidItem -> gatewayModule.getConnectionService().assignClient(request))
                .call(assignClientResponse -> {

                    log.info("Client was assigned, connectionId={}, client={}", connectionId,
                            request.getAssignedClient());

                    if (assignClientResponse.getAssigned()) {
                        return respondWelcomeMessage(server, connectionId)
                                .flatMap(response -> syncLog(request));
                    } else {
                        return Uni.createFrom().voidItem();
                    }
                });
    }

    Uni<RespondMessageResponse> respondWelcomeMessage(final URI server,
                                                      final Long connectionId) {
        final var messageBody = new WelcomeMessageBodyModel();
        final var messageModel = messageModelFactory.create(MessageQualifierEnum.WELCOME_MESSAGE, messageBody);
        final var respondMessageRequest = new RespondMessageRequest(server, connectionId, messageModel);
        return gatewayModule.getGatewayService().respondMessage(respondMessageRequest);
    }

    Uni<SyncLogResponse> syncLog(AssignClientRequest request) {
        final var syncLog = logModelFactory.create("Client was assigned, request=" + request);
        final var syncLogRequest = new SyncLogRequest(syncLog);
        return systemModule.getLogService().syncLog(syncLogRequest);
    }
}
