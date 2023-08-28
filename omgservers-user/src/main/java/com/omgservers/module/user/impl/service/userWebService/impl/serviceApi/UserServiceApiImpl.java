package com.omgservers.module.user.impl.service.userWebService.impl.serviceApi;

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
import com.omgservers.model.internalRole.InternalRoleEnum;
import com.omgservers.module.user.impl.service.userWebService.UserWebService;
import com.omgservers.operation.handleApiRequest.HandleApiRequestOperation;
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
    public Uni<SyncUserShardedResponse> syncUser(final SyncUserShardedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::syncUser);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<ValidateCredentialsShardedResponse> validateCredentials(ValidateCredentialsShardedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::validateCredentials);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<CreateTokenShardedResponse> createToken(final CreateTokenShardedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::createToken);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<IntrospectTokenShardedResponse> introspectToken(final IntrospectTokenShardedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::introspectToken);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetPlayerShardedResponse> getPlayer(GetPlayerShardedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::getPlayer);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncPlayerShardedResponse> syncPlayer(SyncPlayerShardedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::syncPlayer);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeletePlayerShardedResponse> deletePlayer(DeletePlayerShardedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::deletePlayer);
    }

    @Override
    public Uni<SyncClientShardedResponse> syncClient(SyncClientShardedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::syncClient);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetClientShardedResponse> getClient(GetClientShardedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::getClient);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteClientShardedResponse> deleteClient(DeleteClientShardedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::deleteClient);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetAttributeShardedResponse> getAttribute(GetAttributeShardedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::getAttribute);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetPlayerAttributesShardedResponse> getPlayerAttributes(GetPlayerAttributesShardedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::getPlayerAttributes);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncAttributeShardedResponse> syncAttribute(SyncAttributeShardedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::syncAttribute);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteAttributeShardedResponse> deleteAttribute(DeleteAttributeShardedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::deleteAttribute);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetObjectShardedResponse> getObject(GetObjectShardedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::getObject);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncObjectShardedResponse> syncObject(SyncObjectShardedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::syncObject);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteObjectShardedResponse> deleteObject(DeleteObjectShardedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, userWebService::deleteObject);
    }
}
