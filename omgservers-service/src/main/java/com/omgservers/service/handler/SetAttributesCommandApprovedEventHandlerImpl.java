package com.omgservers.service.handler;

import com.omgservers.model.dto.user.GetClientRequest;
import com.omgservers.model.dto.user.GetClientResponse;
import com.omgservers.model.dto.user.UpdatePlayerAttributesRequest;
import com.omgservers.model.dto.user.UpdatePlayerAttributesResponse;
import com.omgservers.model.client.ClientModel;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.SetAttributesCommandApprovedEventBodyModel;
import com.omgservers.model.player.PlayerAttributesModel;
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
public class SetAttributesCommandApprovedEventHandlerImpl implements EventHandler {

    final UserModule userModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.SET_ATTRIBUTES_COMMAND_APPROVED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (SetAttributesCommandApprovedEventBodyModel) event.getBody();
        final var runtimeId = body.getRuntimeId();
        final var userId = body.getUserId();
        final var clientId = body.getClientId();

        return getClient(userId, clientId)
                .flatMap(client -> {
                    final var playerId = client.getPlayerId();
                    final var attributes = body.getAttributes();
                    return updatePlayerAttributes(userId, playerId, attributes);
                })
                .replaceWith(true);
    }

    Uni<ClientModel> getClient(final Long userId, final Long clientId) {
        final var getClientServiceRequest = new GetClientRequest(userId, clientId, false);
        return userModule.getClientService().getClient(getClientServiceRequest)
                .map(GetClientResponse::getClient);
    }

    Uni<Boolean> updatePlayerAttributes(final Long userId,
                                        final Long playerId,
                                        final PlayerAttributesModel attributes) {
        final var request = new UpdatePlayerAttributesRequest(userId, playerId, attributes);
        return userModule.getPlayerService().updatePlayerAttributes(request)
                .map(UpdatePlayerAttributesResponse::getUpdated);
    }
}
