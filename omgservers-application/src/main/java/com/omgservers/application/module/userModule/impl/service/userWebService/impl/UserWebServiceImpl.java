package com.omgservers.application.module.userModule.impl.service.userWebService.impl;

import com.omgservers.application.module.userModule.impl.service.attributeInternalService.AttributeInternalService;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.request.DeleteAttributeInternalRequest;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.request.GetAttributeInternalRequest;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.request.GetPlayerAttributesInternalRequest;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.request.SyncAttributeInternalRequest;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.response.DeleteAttributeInternalResponse;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.response.GetAttributeInternalResponse;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.response.GetPlayerAttributesInternalResponse;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.response.SyncAttributeInternalResponse;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.ClientInternalService;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.request.DeleteClientInternalRequest;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.request.GetClientInternalRequest;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.request.SyncClientInternalRequest;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.response.GetClientInternalResponse;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.response.SyncClientInternalResponse;
import com.omgservers.application.module.userModule.impl.service.objectInternalService.ObjectInternalService;
import com.omgservers.application.module.userModule.impl.service.objectInternalService.request.DeleteObjectInternalRequest;
import com.omgservers.application.module.userModule.impl.service.objectInternalService.request.GetObjectInternalRequest;
import com.omgservers.application.module.userModule.impl.service.objectInternalService.request.SyncObjectInternalRequest;
import com.omgservers.application.module.userModule.impl.service.objectInternalService.response.GetObjectInternalResponse;
import com.omgservers.application.module.userModule.impl.service.playerInternalService.PlayerInternalService;
import com.omgservers.application.module.userModule.impl.service.playerInternalService.request.DeletePlayerInternalRequest;
import com.omgservers.application.module.userModule.impl.service.playerInternalService.request.GetPlayerInternalRequest;
import com.omgservers.application.module.userModule.impl.service.playerInternalService.request.SyncPlayerInternalRequest;
import com.omgservers.application.module.userModule.impl.service.playerInternalService.response.GetPlayerInternalResponse;
import com.omgservers.application.module.userModule.impl.service.playerInternalService.response.SyncPlayerInternalResponse;
import com.omgservers.application.module.userModule.impl.service.tokenInternalService.TokenInternalService;
import com.omgservers.application.module.userModule.impl.service.tokenInternalService.request.CreateTokenInternalRequest;
import com.omgservers.application.module.userModule.impl.service.tokenInternalService.request.IntrospectTokenInternalRequest;
import com.omgservers.application.module.userModule.impl.service.tokenInternalService.response.CreateTokenInternalResponse;
import com.omgservers.application.module.userModule.impl.service.tokenInternalService.response.IntrospectTokenInternalResponse;
import com.omgservers.application.module.userModule.impl.service.userInternalService.UserInternalService;
import com.omgservers.application.module.userModule.impl.service.userInternalService.request.SyncUserInternalRequest;
import com.omgservers.application.module.userModule.impl.service.userInternalService.request.ValidateCredentialsInternalRequest;
import com.omgservers.application.module.userModule.impl.service.userInternalService.response.SyncUserInternalResponse;
import com.omgservers.application.module.userModule.impl.service.userInternalService.response.ValidateCredentialsInternalResponse;
import com.omgservers.application.module.userModule.impl.service.userWebService.UserWebService;
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
    public Uni<SyncUserInternalResponse> syncUser(SyncUserInternalRequest request) {
        return userInternalService.syncUser(request);
    }

    @Override
    public Uni<ValidateCredentialsInternalResponse> validateCredentials(ValidateCredentialsInternalRequest request) {
        return userInternalService.validateCredentials(request);
    }

    @Override
    public Uni<CreateTokenInternalResponse> createToken(CreateTokenInternalRequest request) {
        return tokenInternalService.createToken(request);
    }

    @Override
    public Uni<IntrospectTokenInternalResponse> introspectToken(IntrospectTokenInternalRequest request) {
        return tokenInternalService.introspectToken(request);
    }

    @Override
    public Uni<GetPlayerInternalResponse> getPlayer(GetPlayerInternalRequest request) {
        return playerInternalService.getPlayer(request);
    }

    @Override
    public Uni<SyncPlayerInternalResponse> syncPlayer(SyncPlayerInternalRequest request) {
        return playerInternalService.syncPlayer(request);
    }

    @Override
    public Uni<Void> deletePlayer(DeletePlayerInternalRequest request) {
        return playerInternalService.deletePlayer(request);
    }

    @Override
    public Uni<SyncClientInternalResponse> syncClient(SyncClientInternalRequest request) {
        return internalService.syncClient(request);
    }

    @Override
    public Uni<GetClientInternalResponse> getClient(GetClientInternalRequest request) {
        return internalService.getClient(request);
    }

    @Override
    public Uni<Void> deleteClient(DeleteClientInternalRequest request) {
        return internalService.deleteClient(request);
    }

    @Override
    public Uni<GetAttributeInternalResponse> getAttribute(GetAttributeInternalRequest request) {
        return attributeInternalService.getAttribute(request);
    }

    @Override
    public Uni<GetPlayerAttributesInternalResponse> getPlayerAttributes(GetPlayerAttributesInternalRequest request) {
        return attributeInternalService.getPlayerAttributes(request);
    }

    @Override
    public Uni<SyncAttributeInternalResponse> syncAttribute(SyncAttributeInternalRequest request) {
        return attributeInternalService.syncAttribute(request);
    }

    @Override
    public Uni<DeleteAttributeInternalResponse> deleteAttribute(DeleteAttributeInternalRequest request) {
        return attributeInternalService.deleteAttribute(request);
    }

    @Override
    public Uni<GetObjectInternalResponse> getObject(GetObjectInternalRequest request) {
        return objectInternalService.getObject(request);
    }

    @Override
    public Uni<Void> syncObject(SyncObjectInternalRequest request) {
        return objectInternalService.syncObject(request);
    }

    @Override
    public Uni<Void> deleteObject(DeleteObjectInternalRequest request) {
        return objectInternalService.deleteObject(request);
    }
}