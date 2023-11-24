package com.omgservers.service.module.gateway.impl.service.gatewayService.impl.method.assignClient;

import com.omgservers.model.dto.gateway.AssignClientRequest;
import com.omgservers.model.dto.gateway.AssignClientResponse;
import com.omgservers.model.dto.gateway.RespondMessageRequest;
import com.omgservers.model.dto.gateway.RespondMessageResponse;
import com.omgservers.model.dto.system.SyncLogRequest;
import com.omgservers.model.dto.system.SyncLogResponse;
import com.omgservers.model.message.MessageQualifierEnum;
import com.omgservers.model.message.body.WelcomeMessageBodyModel;
import com.omgservers.service.module.gateway.GatewayModule;
import com.omgservers.service.factory.MessageModelFactory;
import com.omgservers.service.module.system.SystemModule;
import com.omgservers.service.factory.LogModelFactory;
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
        log.debug("Assign client, request={}", request);

        final var server = request.getServer();
        final var connectionId = request.getConnectionId();
        return Uni.createFrom().voidItem()
                .map(voidItem -> gatewayModule.getConnectionService().assignClient(request))
                .call(assignClientResponse -> {
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
