package com.omgservers.application.module.userModule.impl.service.userWebService.impl.serviceApi;

import com.omgservers.application.module.userModule.impl.service.userWebService.UserWebService;
import com.omgservers.base.impl.operation.handleApiRequestOperation.HandleApiRequestOperation;
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
import com.omgservers.model.internalRole.InternalRoleEnum;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
    public Uni<DeletePlayerInternalResponse> deletePlayer(DeletePlayerInternalRequest request) {
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
    public Uni<DeleteClientInternalResponse> deleteClient(DeleteClientInternalRequest request) {
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
    public Uni<SyncObjectInternalResponse> syncObject(SyncObjectInternalRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::syncObject);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteObjectInternalResponse> deleteObject(DeleteObjectInternalRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::deleteObject);
    }
}
