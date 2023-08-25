package com.omgservers.application.module.userModule.impl.service.userWebService.impl;

import com.omgservers.application.module.userModule.impl.service.attributeInternalService.AttributeInternalService;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.ClientInternalService;
import com.omgservers.application.module.userModule.impl.service.objectInternalService.ObjectInternalService;
import com.omgservers.application.module.userModule.impl.service.playerInternalService.PlayerInternalService;
import com.omgservers.application.module.userModule.impl.service.tokenInternalService.TokenInternalService;
import com.omgservers.application.module.userModule.impl.service.userInternalService.UserInternalService;
import com.omgservers.application.module.userModule.impl.service.userWebService.UserWebService;
import com.omgservers.dto.userModule.CreateTokenInternalRequest;
import com.omgservers.dto.userModule.CreateTokenInternalResponse;
import com.omgservers.dto.userModule.DeleteAttributeInternalRequest;
import com.omgservers.dto.userModule.DeleteAttributeInternalResponse;
import com.omgservers.dto.userModule.DeleteClientInternalRequest;
import com.omgservers.dto.userModule.DeleteClientInternalResponse;
import com.omgservers.dto.userModule.DeleteObjectInternalRequest;
import com.omgservers.dto.userModule.DeleteObjectInternalResponse;
import com.omgservers.dto.userModule.DeletePlayerInternalRequest;
import com.omgservers.dto.userModule.DeletePlayerInternalResponse;
import com.omgservers.dto.userModule.GetAttributeInternalRequest;
import com.omgservers.dto.userModule.GetAttributeInternalResponse;
import com.omgservers.dto.userModule.GetClientInternalRequest;
import com.omgservers.dto.userModule.GetClientInternalResponse;
import com.omgservers.dto.userModule.GetObjectInternalRequest;
import com.omgservers.dto.userModule.GetObjectInternalResponse;
import com.omgservers.dto.userModule.GetPlayerAttributesInternalRequest;
import com.omgservers.dto.userModule.GetPlayerAttributesInternalResponse;
import com.omgservers.dto.userModule.GetPlayerInternalRequest;
import com.omgservers.dto.userModule.GetPlayerInternalResponse;
import com.omgservers.dto.userModule.IntrospectTokenInternalRequest;
import com.omgservers.dto.userModule.IntrospectTokenInternalResponse;
import com.omgservers.dto.userModule.SyncAttributeInternalRequest;
import com.omgservers.dto.userModule.SyncAttributeInternalResponse;
import com.omgservers.dto.userModule.SyncClientInternalRequest;
import com.omgservers.dto.userModule.SyncClientInternalResponse;
import com.omgservers.dto.userModule.SyncObjectInternalRequest;
import com.omgservers.dto.userModule.SyncObjectInternalResponse;
import com.omgservers.dto.userModule.SyncPlayerInternalRequest;
import com.omgservers.dto.userModule.SyncPlayerInternalResponse;
import com.omgservers.dto.userModule.SyncUserInternalRequest;
import com.omgservers.dto.userModule.SyncUserInternalResponse;
import com.omgservers.dto.userModule.ValidateCredentialsInternalRequest;
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
    public Uni<DeletePlayerInternalResponse> deletePlayer(DeletePlayerInternalRequest request) {
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
    public Uni<DeleteClientInternalResponse> deleteClient(DeleteClientInternalRequest request) {
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
    public Uni<SyncObjectInternalResponse> syncObject(SyncObjectInternalRequest request) {
        return objectInternalService.syncObject(request);
    }

    @Override
    public Uni<DeleteObjectInternalResponse> deleteObject(DeleteObjectInternalRequest request) {
        return objectInternalService.deleteObject(request);
    }
}