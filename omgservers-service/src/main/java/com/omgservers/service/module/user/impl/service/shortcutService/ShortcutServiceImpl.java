package com.omgservers.service.module.user.impl.service.shortcutService;

import com.omgservers.model.client.ClientModel;
import com.omgservers.model.dto.user.DeleteClientRequest;
import com.omgservers.model.dto.user.DeleteClientResponse;
import com.omgservers.model.dto.user.FindPlayerRequest;
import com.omgservers.model.dto.user.FindPlayerResponse;
import com.omgservers.model.dto.user.GetClientRequest;
import com.omgservers.model.dto.user.GetClientResponse;
import com.omgservers.model.dto.user.GetPlayerAttributesRequest;
import com.omgservers.model.dto.user.GetPlayerAttributesResponse;
import com.omgservers.model.dto.user.GetPlayerRequest;
import com.omgservers.model.dto.user.GetPlayerResponse;
import com.omgservers.model.dto.user.ValidateCredentialsRequest;
import com.omgservers.model.dto.user.ValidateCredentialsResponse;
import com.omgservers.model.player.PlayerAttributesModel;
import com.omgservers.model.player.PlayerModel;
import com.omgservers.model.user.UserModel;
import com.omgservers.service.module.user.UserModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class ShortcutServiceImpl implements ShortcutService {

    final UserModule userModule;

    @Override
    public Uni<UserModel> validateCredentials(final Long userId, final String password) {
        final var request = new ValidateCredentialsRequest(userId, password);
        return userModule.getUserService().validateCredentials(request)
                .map(ValidateCredentialsResponse::getUser);
    }

    @Override
    public Uni<PlayerModel> getPlayer(final Long userId, final Long id) {
        final var request = new GetPlayerRequest(userId, id);
        return userModule.getPlayerService().getPlayer(request)
                .map(GetPlayerResponse::getPlayer);
    }

    @Override
    public Uni<PlayerModel> findPlayer(final Long userId, final Long stageId) {
        final var request = new FindPlayerRequest(userId, stageId);
        return userModule.getPlayerService().findPlayer(request)
                .map(FindPlayerResponse::getPlayer);
    }

    @Override
    public Uni<ClientModel> getClient(final Long userId, final Long clientId) {
        final var request = new GetClientRequest(userId, clientId);
        return userModule.getClientService().getClient(request)
                .map(GetClientResponse::getClient);
    }

    @Override
    public Uni<Boolean> deleteClient(final Long userId, final Long clientId) {
        final var request = new DeleteClientRequest(userId, clientId);
        return userModule.getClientService().deleteClient(request)
                .map(DeleteClientResponse::getDeleted);
    }

    @Override
    public Uni<PlayerAttributesModel> getPlayerAttributes(final Long userId, final Long playerId) {
        final var request = new GetPlayerAttributesRequest(userId, playerId);
        return userModule.getPlayerService().getPlayerAttributes(request)
                .map(GetPlayerAttributesResponse::getAttributes);
    }
}
