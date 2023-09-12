package com.omgservers.module.user.impl.service.webService.impl;

import com.omgservers.dto.user.CreateTokenRequest;
import com.omgservers.dto.user.CreateTokenResponse;
import com.omgservers.dto.user.DeleteAttributeRequest;
import com.omgservers.dto.user.DeleteAttributeResponse;
import com.omgservers.dto.user.DeleteClientRequest;
import com.omgservers.dto.user.DeleteClientResponse;
import com.omgservers.dto.user.DeleteObjectRequest;
import com.omgservers.dto.user.DeleteObjectResponse;
import com.omgservers.dto.user.DeletePlayerRequest;
import com.omgservers.dto.user.DeletePlayerResponse;
import com.omgservers.dto.user.FindPlayerRequest;
import com.omgservers.dto.user.FindPlayerResponse;
import com.omgservers.dto.user.GetAttributeRequest;
import com.omgservers.dto.user.GetAttributeResponse;
import com.omgservers.dto.user.GetClientRequest;
import com.omgservers.dto.user.GetClientResponse;
import com.omgservers.dto.user.GetObjectRequest;
import com.omgservers.dto.user.GetObjectResponse;
import com.omgservers.dto.user.GetPlayerAttributesRequest;
import com.omgservers.dto.user.GetPlayerAttributesResponse;
import com.omgservers.dto.user.GetPlayerRequest;
import com.omgservers.dto.user.GetPlayerResponse;
import com.omgservers.dto.user.IntrospectTokenRequest;
import com.omgservers.dto.user.IntrospectTokenResponse;
import com.omgservers.dto.user.SyncAttributeRequest;
import com.omgservers.dto.user.SyncAttributeResponse;
import com.omgservers.dto.user.SyncClientRequest;
import com.omgservers.dto.user.SyncClientResponse;
import com.omgservers.dto.user.SyncObjectRequest;
import com.omgservers.dto.user.SyncObjectResponse;
import com.omgservers.dto.user.SyncPlayerRequest;
import com.omgservers.dto.user.SyncPlayerResponse;
import com.omgservers.dto.user.SyncUserRequest;
import com.omgservers.dto.user.SyncUserResponse;
import com.omgservers.dto.user.ValidateCredentialsRequest;
import com.omgservers.dto.user.ValidateCredentialsResponse;
import com.omgservers.module.user.impl.service.attributeService.AttributeService;
import com.omgservers.module.user.impl.service.clientService.ClientService;
import com.omgservers.module.user.impl.service.objectService.ObjectService;
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

    final AttributeService attributeService;
    final ClientService internalService;
    final PlayerService playerService;
    final ObjectService objectService;
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
    public Uni<FindPlayerResponse> findPlayer(FindPlayerRequest request) {
        return playerService.findPlayer(request);
    }

    @Override
    public Uni<SyncPlayerResponse> syncPlayer(SyncPlayerRequest request) {
        return playerService.syncPlayer(request);
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

    @Override
    public Uni<GetAttributeResponse> getAttribute(GetAttributeRequest request) {
        return attributeService.getAttribute(request);
    }

    @Override
    public Uni<GetPlayerAttributesResponse> getPlayerAttributes(GetPlayerAttributesRequest request) {
        return attributeService.getPlayerAttributes(request);
    }

    @Override
    public Uni<SyncAttributeResponse> syncAttribute(SyncAttributeRequest request) {
        return attributeService.syncAttribute(request);
    }

    @Override
    public Uni<DeleteAttributeResponse> deleteAttribute(DeleteAttributeRequest request) {
        return attributeService.deleteAttribute(request);
    }

    @Override
    public Uni<GetObjectResponse> getObject(GetObjectRequest request) {
        return objectService.getObject(request);
    }

    @Override
    public Uni<SyncObjectResponse> syncObject(SyncObjectRequest request) {
        return objectService.syncObject(request);
    }

    @Override
    public Uni<DeleteObjectResponse> deleteObject(DeleteObjectRequest request) {
        return objectService.deleteObject(request);
    }
}