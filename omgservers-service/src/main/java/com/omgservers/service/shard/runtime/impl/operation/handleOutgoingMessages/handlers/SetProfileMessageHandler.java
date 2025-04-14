package com.omgservers.service.shard.runtime.impl.operation.handleOutgoingMessages.handlers;

import com.omgservers.schema.message.MessageModel;
import com.omgservers.schema.message.MessageQualifierEnum;
import com.omgservers.schema.message.body.SetProfileMessageBodyDto;
import com.omgservers.schema.model.client.ClientModel;
import com.omgservers.schema.model.runtime.RuntimeModel;
import com.omgservers.schema.model.runtimeAssignment.RuntimeAssignmentModel;
import com.omgservers.schema.shard.client.client.GetClientRequest;
import com.omgservers.schema.shard.client.client.GetClientResponse;
import com.omgservers.schema.shard.user.UpdatePlayerProfileRequest;
import com.omgservers.schema.shard.user.UpdatePlayerProfileResponse;
import com.omgservers.service.shard.client.ClientShard;
import com.omgservers.service.shard.runtime.impl.operation.handleOutgoingMessages.OutgoingMessageHandler;
import com.omgservers.service.shard.user.UserShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class SetProfileMessageHandler implements OutgoingMessageHandler {

    final ClientShard clientShard;
    final UserShard userShard;

    @Override
    public MessageQualifierEnum getQualifier() {
        return MessageQualifierEnum.SET_PROFILE;
    }

    @Override
    public Uni<Void> execute(final RuntimeModel runtime,
                             final List<RuntimeAssignmentModel> runtimeAssignments,
                             final MessageModel outgoingMessage) {
        log.debug("Handle, {}", outgoingMessage);

        final var messageBody = (SetProfileMessageBodyDto) outgoingMessage.getBody();
        final var clientId = messageBody.getClientId();
        final var profile = messageBody.getProfile();

        final var clientAssigned = runtimeAssignments.stream()
                .anyMatch(runtimeAssignment -> runtimeAssignment.getClientId().equals(clientId));

        if (clientAssigned) {
            log.info("Set client \"{}\" profile", clientId);

            return setProfile(clientId, profile)
                    .replaceWithVoid();
        } else {
            log.warn("Failed, client \"{}\" not assigned", clientId);
            return Uni.createFrom().voidItem();
        }
    }

    Uni<Boolean> setProfile(final Long clientId,
                            final Object profile) {
        return getClient(clientId)
                .flatMap(client -> {
                    final var userId = client.getUserId();
                    final var playerId = client.getPlayerId();
                    return updatePlayerProfile(userId, playerId, profile);
                });
    }

    Uni<ClientModel> getClient(final Long clientId) {
        final var request = new GetClientRequest(clientId);
        return clientShard.getService().execute(request)
                .map(GetClientResponse::getClient);
    }

    Uni<Boolean> updatePlayerProfile(final Long userId,
                                     final Long playerId,
                                     final Object profile) {
        final var request = new UpdatePlayerProfileRequest(userId, playerId, profile);
        return userShard.getService().execute(request)
                .map(UpdatePlayerProfileResponse::getUpdated);
    }
}
