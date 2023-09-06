package com.omgservers.module.user.impl.service.userWebService.impl;

import com.omgservers.dto.user.CreateTokenShardedResponse;
import com.omgservers.dto.user.CreateTokenShardedRequest;
import com.omgservers.dto.user.DeleteAttributeShardedResponse;
import com.omgservers.dto.user.DeleteAttributeShardedRequest;
import com.omgservers.dto.user.DeleteClientShardedResponse;
import com.omgservers.dto.user.DeleteClientShardedRequest;
import com.omgservers.dto.user.DeleteObjectShardedResponse;
import com.omgservers.dto.user.DeleteObjectShardedRequest;
import com.omgservers.dto.user.DeletePlayerShardedResponse;
import com.omgservers.dto.user.DeletePlayerShardedRequest;
import com.omgservers.dto.user.GetAttributeShardedResponse;
import com.omgservers.dto.user.GetAttributeShardedRequest;
import com.omgservers.dto.user.GetClientShardedResponse;
import com.omgservers.dto.user.GetClientShardedRequest;
import com.omgservers.dto.user.GetObjectShardedResponse;
import com.omgservers.dto.user.GetObjectShardedRequest;
import com.omgservers.dto.user.GetPlayerAttributesShardedResponse;
import com.omgservers.dto.user.GetPlayerAttributesShardedRequest;
import com.omgservers.dto.user.GetPlayerShardedResponse;
import com.omgservers.dto.user.GetPlayerShardedRequest;
import com.omgservers.dto.user.IntrospectTokenShardedRequest;
import com.omgservers.dto.user.IntrospectTokenShardedResponse;
import com.omgservers.dto.user.SyncAttributeShardedResponse;
import com.omgservers.dto.user.SyncAttributeShardedRequest;
import com.omgservers.dto.user.SyncClientShardedResponse;
import com.omgservers.dto.user.SyncClientShardedRequest;
import com.omgservers.dto.user.SyncObjectShardedResponse;
import com.omgservers.dto.user.SyncObjectShardedRequest;
import com.omgservers.dto.user.SyncPlayerShardedResponse;
import com.omgservers.dto.user.SyncPlayerShardedRequest;
import com.omgservers.dto.user.SyncUserShardedResponse;
import com.omgservers.dto.user.SyncUserShardedRequest;
import com.omgservers.dto.user.ValidateCredentialsShardedResponse;
import com.omgservers.dto.user.ValidateCredentialsShardedRequest;
import com.omgservers.module.user.impl.service.attributeService.AttributeService;
import com.omgservers.module.user.impl.service.clientService.ClientService;
import com.omgservers.module.user.impl.service.objectService.ObjectService;
import com.omgservers.module.user.impl.service.playerService.PlayerService;
import com.omgservers.module.user.impl.service.tokenShardedService.TokenShardedService;
import com.omgservers.module.user.impl.service.userService.UserService;
import com.omgservers.module.user.impl.service.userWebService.UserWebService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class UserWebServiceImpl implements UserWebService {

    final AttributeService attributeService;
    final PlayerService playerService;
    final ObjectService objectService;
    final TokenShardedService tokenShardedService;
    final UserService userService;
    final ClientService internalService;

    @Override
    public Uni<SyncUserShardedResponse> syncUser(SyncUserShardedRequest request) {
        return userService.syncUser(request);
    }

    @Override
    public Uni<ValidateCredentialsShardedResponse> validateCredentials(ValidateCredentialsShardedRequest request) {
        return userService.validateCredentials(request);
    }

    @Override
    public Uni<CreateTokenShardedResponse> createToken(CreateTokenShardedRequest request) {
        return tokenShardedService.createToken(request);
    }

    @Override
    public Uni<IntrospectTokenShardedResponse> introspectToken(IntrospectTokenShardedRequest request) {
        return tokenShardedService.introspectToken(request);
    }

    @Override
    public Uni<GetPlayerShardedResponse> getPlayer(GetPlayerShardedRequest request) {
        return playerService.getPlayer(request);
    }

    @Override
    public Uni<SyncPlayerShardedResponse> syncPlayer(SyncPlayerShardedRequest request) {
        return playerService.syncPlayer(request);
    }

    @Override
    public Uni<DeletePlayerShardedResponse> deletePlayer(DeletePlayerShardedRequest request) {
        return playerService.deletePlayer(request);
    }

    @Override
    public Uni<SyncClientShardedResponse> syncClient(SyncClientShardedRequest request) {
        return internalService.syncClient(request);
    }

    @Override
    public Uni<GetClientShardedResponse> getClient(GetClientShardedRequest request) {
        return internalService.getClient(request);
    }

    @Override
    public Uni<DeleteClientShardedResponse> deleteClient(DeleteClientShardedRequest request) {
        return internalService.deleteClient(request);
    }

    @Override
    public Uni<GetAttributeShardedResponse> getAttribute(GetAttributeShardedRequest request) {
        return attributeService.getAttribute(request);
    }

    @Override
    public Uni<GetPlayerAttributesShardedResponse> getPlayerAttributes(GetPlayerAttributesShardedRequest request) {
        return attributeService.getPlayerAttributes(request);
    }

    @Override
    public Uni<SyncAttributeShardedResponse> syncAttribute(SyncAttributeShardedRequest request) {
        return attributeService.syncAttribute(request);
    }

    @Override
    public Uni<DeleteAttributeShardedResponse> deleteAttribute(DeleteAttributeShardedRequest request) {
        return attributeService.deleteAttribute(request);
    }

    @Override
    public Uni<GetObjectShardedResponse> getObject(GetObjectShardedRequest request) {
        return objectService.getObject(request);
    }

    @Override
    public Uni<SyncObjectShardedResponse> syncObject(SyncObjectShardedRequest request) {
        return objectService.syncObject(request);
    }

    @Override
    public Uni<DeleteObjectShardedResponse> deleteObject(DeleteObjectShardedRequest request) {
        return objectService.deleteObject(request);
    }
}