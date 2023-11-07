package com.omgservers.service.handler;

import com.omgservers.model.client.ClientModel;
import com.omgservers.model.dto.user.GetClientRequest;
import com.omgservers.model.dto.user.GetClientResponse;
import com.omgservers.model.dto.user.UpdatePlayerProfileRequest;
import com.omgservers.model.dto.user.UpdatePlayerProfileResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.SetProfileCommandApprovedEventBodyModel;
import com.omgservers.service.module.system.impl.service.handlerService.impl.EventHandler;
import com.omgservers.service.module.user.UserModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class SetProfileCommandApprovedEventHandlerImpl implements EventHandler {

    final UserModule userModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.SET_PROFILE_COMMAND_APPROVED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (SetProfileCommandApprovedEventBodyModel) event.getBody();
        final var runtimeId = body.getRuntimeId();
        final var userId = body.getUserId();
        final var clientId = body.getClientId();

        return getClient(userId, clientId)
                .flatMap(client -> {
                    final var playerId = client.getPlayerId();
                    final var profile = body.getProfile();
                    return updatePlayerProfile(userId, playerId, profile);
                })
                .replaceWith(true);
    }

    Uni<ClientModel> getClient(final Long userId, final Long clientId) {
        final var getClientServiceRequest = new GetClientRequest(userId, clientId, false);
        return userModule.getClientService().getClient(getClientServiceRequest)
                .map(GetClientResponse::getClient);
    }

    Uni<Boolean> updatePlayerProfile(final Long userId,
                                     final Long playerId,
                                     final Object profile) {
        final var request = new UpdatePlayerProfileRequest(userId, playerId, profile);
        return userModule.getPlayerService().updatePlayerProfile(request)
                .map(UpdatePlayerProfileResponse::getUpdated);
    }
}
