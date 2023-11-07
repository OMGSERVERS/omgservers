package com.omgservers.service.module.user.impl.service.webService.impl;

import com.omgservers.model.dto.user.CreateTokenRequest;
import com.omgservers.model.dto.user.CreateTokenResponse;
import com.omgservers.model.dto.user.DeleteClientRequest;
import com.omgservers.model.dto.user.DeleteClientResponse;
import com.omgservers.model.dto.user.DeletePlayerRequest;
import com.omgservers.model.dto.user.DeletePlayerResponse;
import com.omgservers.model.dto.user.DeleteUserRequest;
import com.omgservers.model.dto.user.DeleteUserResponse;
import com.omgservers.model.dto.user.FindPlayerRequest;
import com.omgservers.model.dto.user.FindPlayerResponse;
import com.omgservers.model.dto.user.GetClientRequest;
import com.omgservers.model.dto.user.GetClientResponse;
import com.omgservers.model.dto.user.GetPlayerAttributesRequest;
import com.omgservers.model.dto.user.GetPlayerAttributesResponse;
import com.omgservers.model.dto.user.GetPlayerProfileRequest;
import com.omgservers.model.dto.user.GetPlayerProfileResponse;
import com.omgservers.model.dto.user.GetPlayerRequest;
import com.omgservers.model.dto.user.GetPlayerResponse;
import com.omgservers.model.dto.user.IntrospectTokenRequest;
import com.omgservers.model.dto.user.IntrospectTokenResponse;
import com.omgservers.model.dto.user.SyncClientRequest;
import com.omgservers.model.dto.user.SyncClientResponse;
import com.omgservers.model.dto.user.SyncPlayerRequest;
import com.omgservers.model.dto.user.SyncPlayerResponse;
import com.omgservers.model.dto.user.SyncUserRequest;
import com.omgservers.model.dto.user.SyncUserResponse;
import com.omgservers.model.dto.user.UpdatePlayerAttributesRequest;
import com.omgservers.model.dto.user.UpdatePlayerAttributesResponse;
import com.omgservers.model.dto.user.UpdatePlayerProfileRequest;
import com.omgservers.model.dto.user.UpdatePlayerProfileResponse;
import com.omgservers.model.dto.user.ValidateCredentialsRequest;
import com.omgservers.model.dto.user.ValidateCredentialsResponse;
import com.omgservers.service.module.user.impl.service.clientService.ClientService;
import com.omgservers.service.module.user.impl.service.playerService.PlayerService;
import com.omgservers.service.module.user.impl.service.tokenService.TokenService;
import com.omgservers.service.module.user.impl.service.userService.UserService;
import com.omgservers.service.module.user.impl.service.webService.WebService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class WebServiceImpl implements WebService {

    final ClientService internalService;
    final PlayerService playerService;
    final TokenService tokenService;
    final UserService userService;

    @Override
    public Uni<SyncUserResponse> syncUser(final SyncUserRequest request) {
        return userService.syncUser(request);
    }

    @Override
    public Uni<DeleteUserResponse> deleteUser(final DeleteUserRequest request) {
        return userService.deleteUser(request);
    }

    @Override
    public Uni<ValidateCredentialsResponse> validateCredentials(final ValidateCredentialsRequest request) {
        return userService.validateCredentials(request);
    }

    @Override
    public Uni<CreateTokenResponse> createToken(final CreateTokenRequest request) {
        return tokenService.createToken(request);
    }

    @Override
    public Uni<IntrospectTokenResponse> introspectToken(final IntrospectTokenRequest request) {
        return tokenService.introspectToken(request);
    }

    @Override
    public Uni<GetPlayerResponse> getPlayer(final GetPlayerRequest request) {
        return playerService.getPlayer(request);
    }

    @Override
    public Uni<GetPlayerAttributesResponse> getPlayerAttributes(final GetPlayerAttributesRequest request) {
        return playerService.getPlayerAttributes(request);
    }

    @Override
    public Uni<GetPlayerProfileResponse> getPlayerProfile(final GetPlayerProfileRequest request) {
        return playerService.getPlayerProfile(request);
    }

    @Override
    public Uni<FindPlayerResponse> findPlayer(final FindPlayerRequest request) {
        return playerService.findPlayer(request);
    }

    @Override
    public Uni<SyncPlayerResponse> syncPlayer(final SyncPlayerRequest request) {
        return playerService.syncPlayer(request);
    }

    @Override
    public Uni<UpdatePlayerAttributesResponse> updatePlayerAttributes(final UpdatePlayerAttributesRequest request) {
        return playerService.updatePlayerAttributes(request);
    }

    @Override
    public Uni<UpdatePlayerProfileResponse> updatePlayerProfile(final UpdatePlayerProfileRequest request) {
        return playerService.updatePlayerProfile(request);
    }

    @Override
    public Uni<DeletePlayerResponse> deletePlayer(final DeletePlayerRequest request) {
        return playerService.deletePlayer(request);
    }

    @Override
    public Uni<SyncClientResponse> syncClient(final SyncClientRequest request) {
        return internalService.syncClient(request);
    }

    @Override
    public Uni<GetClientResponse> getClient(final GetClientRequest request) {
        return internalService.getClient(request);
    }

    @Override
    public Uni<DeleteClientResponse> deleteClient(final DeleteClientRequest request) {
        return internalService.deleteClient(request);
    }
}