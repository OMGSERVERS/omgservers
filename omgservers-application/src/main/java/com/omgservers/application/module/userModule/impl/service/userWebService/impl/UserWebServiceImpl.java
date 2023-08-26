package com.omgservers.application.module.userModule.impl.service.userWebService.impl;

import com.omgservers.application.module.userModule.impl.service.attributeInternalService.AttributeInternalService;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.ClientInternalService;
import com.omgservers.application.module.userModule.impl.service.objectInternalService.ObjectInternalService;
import com.omgservers.application.module.userModule.impl.service.playerInternalService.PlayerInternalService;
import com.omgservers.application.module.userModule.impl.service.tokenInternalService.TokenInternalService;
import com.omgservers.application.module.userModule.impl.service.userInternalService.UserInternalService;
import com.omgservers.application.module.userModule.impl.service.userWebService.UserWebService;
import com.omgservers.dto.userModule.CreateTokenRoutedRequest;
import com.omgservers.dto.userModule.CreateTokenInternalResponse;
import com.omgservers.dto.userModule.DeleteAttributeRoutedRequest;
import com.omgservers.dto.userModule.DeleteAttributeInternalResponse;
import com.omgservers.dto.userModule.DeleteClientRoutedRequest;
import com.omgservers.dto.userModule.DeleteClientInternalResponse;
import com.omgservers.dto.userModule.DeleteObjectRoutedRequest;
import com.omgservers.dto.userModule.DeleteObjectInternalResponse;
import com.omgservers.dto.userModule.DeletePlayerRoutedRequest;
import com.omgservers.dto.userModule.DeletePlayerInternalResponse;
import com.omgservers.dto.userModule.GetAttributeRoutedRequest;
import com.omgservers.dto.userModule.GetAttributeInternalResponse;
import com.omgservers.dto.userModule.GetClientRoutedRequest;
import com.omgservers.dto.userModule.GetClientInternalResponse;
import com.omgservers.dto.userModule.GetObjectRoutedRequest;
import com.omgservers.dto.userModule.GetObjectInternalResponse;
import com.omgservers.dto.userModule.GetPlayerAttributesRoutedRequest;
import com.omgservers.dto.userModule.GetPlayerAttributesInternalResponse;
import com.omgservers.dto.userModule.GetPlayerRoutedRequest;
import com.omgservers.dto.userModule.GetPlayerInternalResponse;
import com.omgservers.dto.userModule.IntrospectTokenInternalRequest;
import com.omgservers.dto.userModule.IntrospectTokenInternalResponse;
import com.omgservers.dto.userModule.SyncAttributeRoutedRequest;
import com.omgservers.dto.userModule.SyncAttributeInternalResponse;
import com.omgservers.dto.userModule.SyncClientRoutedRequest;
import com.omgservers.dto.userModule.SyncClientInternalResponse;
import com.omgservers.dto.userModule.SyncObjectRoutedRequest;
import com.omgservers.dto.userModule.SyncObjectInternalResponse;
import com.omgservers.dto.userModule.SyncPlayerRoutedRequest;
import com.omgservers.dto.userModule.SyncPlayerInternalResponse;
import com.omgservers.dto.userModule.SyncUserRoutedRequest;
import com.omgservers.dto.userModule.SyncUserInternalResponse;
import com.omgservers.dto.userModule.ValidateCredentialsRoutedRequest;
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
    public Uni<SyncUserInternalResponse> syncUser(SyncUserRoutedRequest request) {
        return userInternalService.syncUser(request);
    }

    @Override
    public Uni<ValidateCredentialsInternalResponse> validateCredentials(ValidateCredentialsRoutedRequest request) {
        return userInternalService.validateCredentials(request);
    }

    @Override
    public Uni<CreateTokenInternalResponse> createToken(CreateTokenRoutedRequest request) {
        return tokenInternalService.createToken(request);
    }

    @Override
    public Uni<IntrospectTokenInternalResponse> introspectToken(IntrospectTokenInternalRequest request) {
        return tokenInternalService.introspectToken(request);
    }

    @Override
    public Uni<GetPlayerInternalResponse> getPlayer(GetPlayerRoutedRequest request) {
        return playerInternalService.getPlayer(request);
    }

    @Override
    public Uni<SyncPlayerInternalResponse> syncPlayer(SyncPlayerRoutedRequest request) {
        return playerInternalService.syncPlayer(request);
    }

    @Override
    public Uni<DeletePlayerInternalResponse> deletePlayer(DeletePlayerRoutedRequest request) {
        return playerInternalService.deletePlayer(request);
    }

    @Override
    public Uni<SyncClientInternalResponse> syncClient(SyncClientRoutedRequest request) {
        return internalService.syncClient(request);
    }

    @Override
    public Uni<GetClientInternalResponse> getClient(GetClientRoutedRequest request) {
        return internalService.getClient(request);
    }

    @Override
    public Uni<DeleteClientInternalResponse> deleteClient(DeleteClientRoutedRequest request) {
        return internalService.deleteClient(request);
    }

    @Override
    public Uni<GetAttributeInternalResponse> getAttribute(GetAttributeRoutedRequest request) {
        return attributeInternalService.getAttribute(request);
    }

    @Override
    public Uni<GetPlayerAttributesInternalResponse> getPlayerAttributes(GetPlayerAttributesRoutedRequest request) {
        return attributeInternalService.getPlayerAttributes(request);
    }

    @Override
    public Uni<SyncAttributeInternalResponse> syncAttribute(SyncAttributeRoutedRequest request) {
        return attributeInternalService.syncAttribute(request);
    }

    @Override
    public Uni<DeleteAttributeInternalResponse> deleteAttribute(DeleteAttributeRoutedRequest request) {
        return attributeInternalService.deleteAttribute(request);
    }

    @Override
    public Uni<GetObjectInternalResponse> getObject(GetObjectRoutedRequest request) {
        return objectInternalService.getObject(request);
    }

    @Override
    public Uni<SyncObjectInternalResponse> syncObject(SyncObjectRoutedRequest request) {
        return objectInternalService.syncObject(request);
    }

    @Override
    public Uni<DeleteObjectInternalResponse> deleteObject(DeleteObjectRoutedRequest request) {
        return objectInternalService.deleteObject(request);
    }
}