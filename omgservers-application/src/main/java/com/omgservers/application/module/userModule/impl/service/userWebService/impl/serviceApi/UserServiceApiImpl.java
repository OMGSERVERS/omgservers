package com.omgservers.application.module.userModule.impl.service.userWebService.impl.serviceApi;

import com.omgservers.application.module.userModule.impl.service.userWebService.UserWebService;
import com.omgservers.operation.handleApiRequest.HandleApiRequestOperation;
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
    public Uni<SyncUserInternalResponse> syncUser(final SyncUserShardRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::syncUser);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<ValidateCredentialsInternalResponse> validateCredentials(ValidateCredentialsShardRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::validateCredentials);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<CreateTokenInternalResponse> createToken(final CreateTokenShardRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::createToken);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<IntrospectTokenInternalResponse> introspectToken(final IntrospectTokenInternalRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::introspectToken);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetPlayerInternalResponse> getPlayer(GetPlayerShardRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::getPlayer);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncPlayerInternalResponse> syncPlayer(SyncPlayerShardRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::syncPlayer);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeletePlayerInternalResponse> deletePlayer(DeletePlayerShardRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::deletePlayer);
    }

    @Override
    public Uni<SyncClientInternalResponse> syncClient(SyncClientShardRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::syncClient);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetClientInternalResponse> getClient(GetClientShardRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::getClient);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteClientInternalResponse> deleteClient(DeleteClientShardRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::deleteClient);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetAttributeInternalResponse> getAttribute(GetAttributeShardRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::getAttribute);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetPlayerAttributesInternalResponse> getPlayerAttributes(GetPlayerAttributesShardRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::getPlayerAttributes);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncAttributeInternalResponse> syncAttribute(SyncAttributeShardRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::syncAttribute);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteAttributeInternalResponse> deleteAttribute(DeleteAttributeShardRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::deleteAttribute);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetObjectInternalResponse> getObject(GetObjectShardRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::getObject);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncObjectInternalResponse> syncObject(SyncObjectShardRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::syncObject);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteObjectInternalResponse> deleteObject(DeleteObjectShardRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::deleteObject);
    }
}
