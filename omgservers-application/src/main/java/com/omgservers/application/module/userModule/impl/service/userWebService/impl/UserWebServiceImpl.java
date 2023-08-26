package com.omgservers.application.module.userModule.impl.service.userWebService.impl;

import com.omgservers.application.module.userModule.impl.service.attributeInternalService.AttributeInternalService;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.ClientInternalService;
import com.omgservers.application.module.userModule.impl.service.objectInternalService.ObjectInternalService;
import com.omgservers.application.module.userModule.impl.service.playerInternalService.PlayerInternalService;
import com.omgservers.application.module.userModule.impl.service.tokenInternalService.TokenInternalService;
import com.omgservers.application.module.userModule.impl.service.userInternalService.UserInternalService;
import com.omgservers.application.module.userModule.impl.service.userWebService.UserWebService;
import com.omgservers.dto.userModule.CreateTokenShardRequest;
import com.omgservers.dto.userModule.CreateTokenInternalResponse;
import com.omgservers.dto.userModule.DeleteAttributeShardRequest;
import com.omgservers.dto.userModule.DeleteAttributeInternalResponse;
import com.omgservers.dto.userModule.DeleteClientShardRequest;
import com.omgservers.dto.userModule.DeleteClientInternalResponse;
import com.omgservers.dto.userModule.DeleteObjectShardRequest;
import com.omgservers.dto.userModule.DeleteObjectInternalResponse;
import com.omgservers.dto.userModule.DeletePlayerShardRequest;
import com.omgservers.dto.userModule.DeletePlayerInternalResponse;
import com.omgservers.dto.userModule.GetAttributeShardRequest;
import com.omgservers.dto.userModule.GetAttributeInternalResponse;
import com.omgservers.dto.userModule.GetClientShardRequest;
import com.omgservers.dto.userModule.GetClientInternalResponse;
import com.omgservers.dto.userModule.GetObjectShardRequest;
import com.omgservers.dto.userModule.GetObjectInternalResponse;
import com.omgservers.dto.userModule.GetPlayerAttributesShardRequest;
import com.omgservers.dto.userModule.GetPlayerAttributesInternalResponse;
import com.omgservers.dto.userModule.GetPlayerShardRequest;
import com.omgservers.dto.userModule.GetPlayerInternalResponse;
import com.omgservers.dto.userModule.IntrospectTokenInternalRequest;
import com.omgservers.dto.userModule.IntrospectTokenInternalResponse;
import com.omgservers.dto.userModule.SyncAttributeShardRequest;
import com.omgservers.dto.userModule.SyncAttributeInternalResponse;
import com.omgservers.dto.userModule.SyncClientShardRequest;
import com.omgservers.dto.userModule.SyncClientInternalResponse;
import com.omgservers.dto.userModule.SyncObjectShardRequest;
import com.omgservers.dto.userModule.SyncObjectInternalResponse;
import com.omgservers.dto.userModule.SyncPlayerShardRequest;
import com.omgservers.dto.userModule.SyncPlayerInternalResponse;
import com.omgservers.dto.userModule.SyncUserShardRequest;
import com.omgservers.dto.userModule.SyncUserInternalResponse;
import com.omgservers.dto.userModule.ValidateCredentialsShardRequest;
import com.omgservers.dto.userModule.ValidateCredentialsInternalResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class UserWebServiceImpl implements UserWebService {

    final AttributeInternalService attributeInternalService;
    final PlayerInternalService playerInternalService;
    final ObjectInternalService objectInternalService;
    final TokenInternalService tokenInternalService;
    final UserInternalService userInternalService;
    final ClientInternalService internalService;

    @Override
    public Uni<SyncUserInternalResponse> syncUser(SyncUserShardRequest request) {
        return userInternalService.syncUser(request);
    }

    @Override
    public Uni<ValidateCredentialsInternalResponse> validateCredentials(ValidateCredentialsShardRequest request) {
        return userInternalService.validateCredentials(request);
    }

    @Override
    public Uni<CreateTokenInternalResponse> createToken(CreateTokenShardRequest request) {
        return tokenInternalService.createToken(request);
    }

    @Override
    public Uni<IntrospectTokenInternalResponse> introspectToken(IntrospectTokenInternalRequest request) {
        return tokenInternalService.introspectToken(request);
    }

    @Override
    public Uni<GetPlayerInternalResponse> getPlayer(GetPlayerShardRequest request) {
        return playerInternalService.getPlayer(request);
    }

    @Override
    public Uni<SyncPlayerInternalResponse> syncPlayer(SyncPlayerShardRequest request) {
        return playerInternalService.syncPlayer(request);
    }

    @Override
    public Uni<DeletePlayerInternalResponse> deletePlayer(DeletePlayerShardRequest request) {
        return playerInternalService.deletePlayer(request);
    }

    @Override
    public Uni<SyncClientInternalResponse> syncClient(SyncClientShardRequest request) {
        return internalService.syncClient(request);
    }

    @Override
    public Uni<GetClientInternalResponse> getClient(GetClientShardRequest request) {
        return internalService.getClient(request);
    }

    @Override
    public Uni<DeleteClientInternalResponse> deleteClient(DeleteClientShardRequest request) {
        return internalService.deleteClient(request);
    }

    @Override
    public Uni<GetAttributeInternalResponse> getAttribute(GetAttributeShardRequest request) {
        return attributeInternalService.getAttribute(request);
    }

    @Override
    public Uni<GetPlayerAttributesInternalResponse> getPlayerAttributes(GetPlayerAttributesShardRequest request) {
        return attributeInternalService.getPlayerAttributes(request);
    }

    @Override
    public Uni<SyncAttributeInternalResponse> syncAttribute(SyncAttributeShardRequest request) {
        return attributeInternalService.syncAttribute(request);
    }

    @Override
    public Uni<DeleteAttributeInternalResponse> deleteAttribute(DeleteAttributeShardRequest request) {
        return attributeInternalService.deleteAttribute(request);
    }

    @Override
    public Uni<GetObjectInternalResponse> getObject(GetObjectShardRequest request) {
        return objectInternalService.getObject(request);
    }

    @Override
    public Uni<SyncObjectInternalResponse> syncObject(SyncObjectShardRequest request) {
        return objectInternalService.syncObject(request);
    }

    @Override
    public Uni<DeleteObjectInternalResponse> deleteObject(DeleteObjectShardRequest request) {
        return objectInternalService.deleteObject(request);
    }
}