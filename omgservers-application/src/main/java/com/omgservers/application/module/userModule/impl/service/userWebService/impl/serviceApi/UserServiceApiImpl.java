package com.omgservers.application.module.userModule.impl.service.userWebService.impl.serviceApi;

import com.omgservers.application.module.securityModule.model.InternalRoleEnum;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.request.*;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.response.DeleteAttributeInternalResponse;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.response.GetAttributeInternalResponse;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.response.GetPlayerAttributesInternalResponse;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.response.SyncAttributeInternalResponse;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.request.SyncClientInternalRequest;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.request.DeleteClientInternalRequest;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.request.GetClientInternalRequest;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.response.SyncClientInternalResponse;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.response.GetClientInternalResponse;
import com.omgservers.application.module.userModule.impl.service.objectInternalService.request.DeleteObjectInternalRequest;
import com.omgservers.application.module.userModule.impl.service.objectInternalService.request.GetObjectInternalRequest;
import com.omgservers.application.module.userModule.impl.service.objectInternalService.request.SyncObjectInternalRequest;
import com.omgservers.application.module.userModule.impl.service.objectInternalService.response.GetObjectInternalResponse;
import com.omgservers.application.module.userModule.impl.service.playerInternalService.request.DeletePlayerInternalRequest;
import com.omgservers.application.module.userModule.impl.service.playerInternalService.request.GetPlayerInternalRequest;
import com.omgservers.application.module.userModule.impl.service.playerInternalService.request.SyncPlayerInternalRequest;
import com.omgservers.application.module.userModule.impl.service.playerInternalService.response.GetPlayerInternalResponse;
import com.omgservers.application.module.userModule.impl.service.playerInternalService.response.SyncPlayerInternalResponse;
import com.omgservers.application.module.userModule.impl.service.tokenInternalService.request.CreateTokenInternalRequest;
import com.omgservers.application.module.userModule.impl.service.tokenInternalService.request.IntrospectTokenInternalRequest;
import com.omgservers.application.module.userModule.impl.service.tokenInternalService.response.CreateTokenInternalResponse;
import com.omgservers.application.module.userModule.impl.service.tokenInternalService.response.IntrospectTokenInternalResponse;
import com.omgservers.application.module.userModule.impl.service.userInternalService.request.SyncUserInternalRequest;
import com.omgservers.application.module.userModule.impl.service.userInternalService.request.ValidateCredentialsInternalRequest;
import com.omgservers.application.module.userModule.impl.service.userInternalService.response.SyncUserInternalResponse;
import com.omgservers.application.module.userModule.impl.service.userInternalService.response.ValidateCredentialsInternalResponse;
import com.omgservers.application.module.userModule.impl.service.userWebService.UserWebService;
import com.omgservers.application.operation.handleApiRequestOperation.HandleApiRequestOperation;
import io.smallrye.mutiny.Uni;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UserServiceApiImpl implements UserServiceApi {

    final HandleApiRequestOperation handleApiRequestOperation;
    final UserWebService userWebService;

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncUserInternalResponse> syncUser(final SyncUserInternalRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::syncUser);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<ValidateCredentialsInternalResponse> validateCredentials(ValidateCredentialsInternalRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::validateCredentials);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<CreateTokenInternalResponse> createToken(final CreateTokenInternalRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::createToken);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<IntrospectTokenInternalResponse> introspectToken(final IntrospectTokenInternalRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::introspectToken);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetPlayerInternalResponse> getPlayer(GetPlayerInternalRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::getPlayer);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncPlayerInternalResponse> syncPlayer(SyncPlayerInternalRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::syncPlayer);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<Void> deletePlayer(DeletePlayerInternalRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::deletePlayer);
    }

    @Override
    public Uni<SyncClientInternalResponse> syncClient(SyncClientInternalRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::syncClient);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetClientInternalResponse> getClient(GetClientInternalRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::getClient);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<Void> deleteClient(DeleteClientInternalRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::deleteClient);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetAttributeInternalResponse> getAttribute(GetAttributeInternalRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::getAttribute);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetPlayerAttributesInternalResponse> getPlayerAttributes(GetPlayerAttributesInternalRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::getPlayerAttributes);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncAttributeInternalResponse> syncAttribute(SyncAttributeInternalRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::syncAttribute);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteAttributeInternalResponse> deleteAttribute(DeleteAttributeInternalRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::deleteAttribute);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetObjectInternalResponse> getObject(GetObjectInternalRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::getObject);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<Void> syncObject(SyncObjectInternalRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::syncObject);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<Void> deleteObject(DeleteObjectInternalRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::deleteObject);
    }
}
