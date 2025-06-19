package com.omgservers.service.shard.runtime.impl.operation.handleOutgoingMessages.handlers;

import com.omgservers.schema.message.MessageModel;
import com.omgservers.schema.message.MessageQualifierEnum;
import com.omgservers.schema.message.body.UpgradeConnectionMessageBodyDto;
import com.omgservers.schema.message.body.UpgradeConnectionQualifierEnum;
import com.omgservers.schema.model.runtime.RuntimeModel;
import com.omgservers.schema.model.runtimeAssignment.RuntimeAssignmentModel;
import com.omgservers.service.operation.client.CreateDispatcherConnectionUpgradedClientMessageOperation;
import com.omgservers.service.operation.client.CreateDispatcherClientWebSocketConfigOperation;
import com.omgservers.service.operation.security.IssueJwtTokenOperation;
import com.omgservers.service.operation.server.GetServiceConfigOperation;
import com.omgservers.service.shard.runtime.impl.operation.handleOutgoingMessages.OutgoingMessageHandler;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class UpgradeConnectionMessageHandler implements OutgoingMessageHandler {

    final CreateDispatcherClientWebSocketConfigOperation createDispatcherClientWebSocketConfigOperation;
    final CreateDispatcherConnectionUpgradedClientMessageOperation
            createDispatcherConnectionUpgradedClientMessageOperation;
    final GetServiceConfigOperation getServiceConfigOperation;
    final IssueJwtTokenOperation issueJwtTokenOperation;

    @Override
    public MessageQualifierEnum getQualifier() {
        return MessageQualifierEnum.UPGRADE_CONNECTION;
    }

    @Override
    public Uni<Void> execute(final RuntimeModel runtime,
                             final List<RuntimeAssignmentModel> runtimeAssignments,
                             final MessageModel outgoingMessage) {
        log.debug("Handle, {}", outgoingMessage);

        final var messageBody = (UpgradeConnectionMessageBodyDto) outgoingMessage.getBody();
        final var clientId = messageBody.getClientId();
        final var protocol = messageBody.getProtocol();

        final var clientAssigned = runtimeAssignments.stream()
                .anyMatch(runtimeAssignment -> runtimeAssignment.getClientId().equals(clientId));

        if (clientAssigned) {
            final var runtimeId = runtime.getId();

            log.info("Upgrade client \"{}\" connection -> {}", clientId, protocol);
            return upgradeConnection(runtimeId, clientId, protocol)
                    .replaceWithVoid();

        } else {
            log.warn("Failed, client \"{}\" not assigned", clientId);
            return Uni.createFrom().voidItem();
        }
    }

    Uni<Boolean> upgradeConnection(final Long runtimeId,
                                   final Long clientId,
                                   final UpgradeConnectionQualifierEnum protocol) {
        return switch (protocol) {
            case DISPATCHER -> {
                final var webSocketConfig = createDispatcherClientWebSocketConfigOperation.execute(clientId, runtimeId);
                yield createDispatcherConnectionUpgradedClientMessageOperation
                        .executeFailSafe(webSocketConfig, clientId);
            }
        };
    }
}
