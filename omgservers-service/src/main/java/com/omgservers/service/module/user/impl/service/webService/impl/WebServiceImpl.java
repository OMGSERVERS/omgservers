package com.omgservers.service.module.user.impl.service.webService.impl;

import com.omgservers.model.dto.user.CreateTokenRequest;
import com.omgservers.model.dto.user.CreateTokenResponse;
import com.omgservers.model.dto.user.DeleteClientRequest;
import com.omgservers.model.dto.user.DeleteClientResponse;
import com.omgservers.model.dto.user.DeletePlayerRequest;
import com.omgservers.model.dto.user.DeletePlayerResponse;
import com.omgservers.model.dto.user.FindPlayerRequest;
import com.omgservers.model.dto.user.FindPlayerResponse;
import com.omgservers.model.dto.user.GetClientRequest;
import com.omgservers.model.dto.user.GetClientResponse;
import com.omgservers.model.dto.user.GetPlayerAttributesRequest;
import com.omgservers.model.dto.user.GetPlayerAttributesResponse;
import com.omgservers.model.dto.user.GetPlayerObjectRequest;
import com.omgservers.model.dto.user.GetPlayerObjectResponse;
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
import com.omgservers.model.dto.user.UpdatePlayerObjectRequest;
import com.omgservers.model.dto.user.UpdatePlayerObjectResponse;
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