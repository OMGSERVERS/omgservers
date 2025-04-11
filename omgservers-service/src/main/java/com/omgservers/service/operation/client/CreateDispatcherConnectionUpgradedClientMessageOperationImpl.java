package com.omgservers.service.operation.client;

import com.omgservers.schema.message.body.ConnectionUpgradeQualifierEnum;
import com.omgservers.schema.message.body.ConnectionUpgradedMessageBodyDto;
import com.omgservers.schema.module.client.clientMessage.SyncClientMessageRequest;
import com.omgservers.schema.module.client.clientMessage.SyncClientMessageResponse;
import com.omgservers.service.factory.client.ClientMessageModelFactory;
import com.omgservers.service.shard.client.ClientShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CreateDispatcherConnectionUpgradedClientMessageOperationImpl
        implements CreateDispatcherConnectionUpgradedClientMessageOperation {

    final ClientShard clientShard;

    final ClientMessageModelFactory clientMessageModelFactory;

    @Override
    public Uni<Boolean> executeFailSafe(final URI connectionUrl,
                                        final Long clientId) {
        final var dispatcherConfig = new ConnectionUpgradedMessageBodyDto.DispatcherConfig(connectionUrl);
        final var messageBody = ConnectionUpgradedMessageBodyDto.builder()
                .protocol(ConnectionUpgradeQualifierEnum.DISPATCHER)
                .dispatcherConfig(dispatcherConfig)
                .build();
        final var clientMessage = clientMessageModelFactory.create(clientId, messageBody);

        final var request = new SyncClientMessageRequest(clientMessage);
        return clientShard.getService().executeWithIdempotency(request)
                .map(SyncClientMessageResponse::getCreated)
                .onFailure()
                .recoverWithItem(t -> {
                    log.warn("Failed, clientId={}, {}:{}",
                            clientId,
                            t.getClass().getSimpleName(),
                            t.getMessage());
                    return Boolean.FALSE;
                });
    }
}
