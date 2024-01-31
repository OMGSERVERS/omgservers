package com.omgservers.service.module.user.impl.service.shortcutService;

import com.omgservers.model.dto.user.CreateTokenRequest;
import com.omgservers.model.dto.user.CreateTokenResponse;
import com.omgservers.model.dto.user.FindPlayerRequest;
import com.omgservers.model.dto.user.FindPlayerResponse;
import com.omgservers.model.dto.user.GetPlayerAttributesRequest;
import com.omgservers.model.dto.user.GetPlayerAttributesResponse;
import com.omgservers.model.dto.user.GetPlayerRequest;
import com.omgservers.model.dto.user.GetPlayerResponse;
import com.omgservers.model.dto.user.GetUserRequest;
import com.omgservers.model.dto.user.GetUserResponse;
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
    public Uni<String> createToken(final Long userId, final String password) {
        final var createTokenRequest = new CreateTokenRequest(userId, password);
        return userModule.getTokenService().createToken(createTokenRequest)
                .map(CreateTokenResponse::getRawToken);
    }

    @Override
    public Uni<UserModel> validateCredentials(final Long userId, final String password) {
        final var request = new ValidateCredentialsRequest(userId, password);
        return userModule.getUserService().validateCredentials(request)
                .map(ValidateCredentialsResponse::getUser);
    }

    @Override
    public Uni<UserModel> getUser(final Long id) {
        final var request = new GetUserRequest(id);
        return userModule.getUserService().getUser(request)
                .map(GetUserResponse::getUser);
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
    public Uni<PlayerAttributesModel> getPlayerAttributes(final Long userId, final Long playerId) {
        final var request = new GetPlayerAttributesRequest(userId, playerId);
        return userModule.getPlayerService().getPlayerAttributes(request)
                .map(GetPlayerAttributesResponse::getAttributes);
    }
}
