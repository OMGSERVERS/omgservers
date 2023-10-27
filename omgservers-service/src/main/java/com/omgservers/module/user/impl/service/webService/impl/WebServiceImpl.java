package com.omgservers.module.user.impl.service.webService.impl;

import com.omgservers.dto.user.CreateTokenRequest;
import com.omgservers.dto.user.CreateTokenResponse;
import com.omgservers.dto.user.DeleteClientRequest;
import com.omgservers.dto.user.DeleteClientResponse;
import com.omgservers.dto.user.DeletePlayerRequest;
import com.omgservers.dto.user.DeletePlayerResponse;
import com.omgservers.dto.user.FindPlayerRequest;
import com.omgservers.dto.user.FindPlayerResponse;
import com.omgservers.dto.user.GetClientRequest;
import com.omgservers.dto.user.GetClientResponse;
import com.omgservers.dto.user.GetPlayerAttributesRequest;
import com.omgservers.dto.user.GetPlayerAttributesResponse;
import com.omgservers.dto.user.GetPlayerObjectRequest;
import com.omgservers.dto.user.GetPlayerObjectResponse;
import com.omgservers.dto.user.GetPlayerRequest;
import com.omgservers.dto.user.GetPlayerResponse;
import com.omgservers.dto.user.IntrospectTokenRequest;
import com.omgservers.dto.user.IntrospectTokenResponse;
import com.omgservers.dto.user.SyncClientRequest;
import com.omgservers.dto.user.SyncClientResponse;
import com.omgservers.dto.user.SyncPlayerRequest;
import com.omgservers.dto.user.SyncPlayerResponse;
import com.omgservers.dto.user.SyncUserRequest;
import com.omgservers.dto.user.SyncUserResponse;
import com.omgservers.dto.user.UpdatePlayerAttributesRequest;
import com.omgservers.dto.user.UpdatePlayerAttributesResponse;
import com.omgservers.dto.user.UpdatePlayerObjectRequest;
import com.omgservers.dto.user.UpdatePlayerObjectResponse;
import com.omgservers.dto.user.ValidateCredentialsRequest;
import com.omgservers.dto.user.ValidateCredentialsResponse;
import com.omgservers.module.user.impl.service.clientService.ClientService;
import com.omgservers.module.user.impl.service.playerService.PlayerService;
import com.omgservers.module.user.impl.service.tokenService.TokenService;
import com.omgservers.module.user.impl.service.userService.UserService;
import com.omgservers.module.user.impl.service.webService.WebService;
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
    public Uni<SyncUserResponse> syncUser(SyncUserRequest request) {
        return userService.syncUser(request);
    }

    @Override
    public Uni<ValidateCredentialsResponse> validateCredentials(ValidateCredentialsRequest request) {
        return userService.validateCredentials(request);
    }

    @Override
    public Uni<CreateTokenResponse> createToken(CreateTokenRequest request) {
        return tokenService.createToken(request);
    }

    @Override
    public Uni<IntrospectTokenResponse> introspectToken(IntrospectTokenRequest request) {
        return tokenService.introspectToken(request);
    }

    @Override
    public Uni<GetPlayerResponse> getPlayer(GetPlayerRequest request) {
        return playerService.getPlayer(request);
    }

    @Override
    public Uni<GetPlayerAttributesResponse> getPlayerAttributes(GetPlayerAttributesRequest request) {
        return playerService.getPlayerAttributes(request);
    }

    @Override
    public Uni<GetPlayerObjectResponse> getPlayerObject(GetPlayerObjectRequest request) {
        return playerService.getPlayerObject(request);
    }

    @Override
    public Uni<FindPlayerResponse> findPlayer(FindPlayerRequest request) {
        return playerService.findPlayer(request);
    }

    @Override
    public Uni<SyncPlayerResponse> syncPlayer(SyncPlayerRequest request) {
        return playerService.syncPlayer(request);
    }

    @Override
    public Uni<UpdatePlayerAttributesResponse> updatePlayerAttributes(UpdatePlayerAttributesRequest request) {
        return playerService.updatePlayerAttributes(request);
    }

    @Override
    public Uni<UpdatePlayerObjectResponse> updatePlayerObject(UpdatePlayerObjectRequest request) {
        return playerService.updatePlayerObject(request);
    }

    @Override
    public Uni<DeletePlayerResponse> deletePlayer(DeletePlayerRequest request) {
        return playerService.deletePlayer(request);
    }

    @Override
    public Uni<SyncClientResponse> syncClient(SyncClientRequest request) {
        return internalService.syncClient(request);
    }

    @Override
    public Uni<GetClientResponse> getClient(GetClientRequest request) {
        return internalService.getClient(request);
    }

    @Override
    public Uni<DeleteClientResponse> deleteClient(DeleteClientRequest request) {
        return internalService.deleteClient(request);
    }
}