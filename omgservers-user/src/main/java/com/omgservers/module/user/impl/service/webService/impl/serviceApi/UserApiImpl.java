package com.omgservers.module.user.impl.service.webService.impl.serviceApi;

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
import com.omgservers.model.internalRole.InternalRoleEnum;
import com.omgservers.module.user.impl.service.webService.WebService;
import com.omgservers.operation.handleApiRequest.HandleApiRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UserApiImpl implements UserApi {

    final HandleApiRequestOperation handleApiRequestOperation;
    final WebService webService;

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncUserResponse> syncUser(final SyncUserRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncUser);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<ValidateCredentialsResponse> validateCredentials(ValidateCredentialsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::validateCredentials);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<CreateTokenResponse> createToken(final CreateTokenRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::createToken);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<IntrospectTokenResponse> introspectToken(final IntrospectTokenRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::introspectToken);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetPlayerResponse> getPlayer(GetPlayerRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getPlayer);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<FindPlayerResponse> findPlayer(FindPlayerRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::findPlayer);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncPlayerResponse> syncPlayer(SyncPlayerRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncPlayer);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeletePlayerResponse> deletePlayer(DeletePlayerRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deletePlayer);
    }

    @Override
    public Uni<SyncClientResponse> syncClient(SyncClientRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncClient);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetClientResponse> getClient(GetClientRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getClient);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteClientResponse> deleteClient(DeleteClientRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteClient);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetAttributeResponse> getAttribute(GetAttributeRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getAttribute);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetPlayerAttributesResponse> getPlayerAttributes(GetPlayerAttributesRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getPlayerAttributes);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncAttributeResponse> syncAttribute(SyncAttributeRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncAttribute);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteAttributeResponse> deleteAttribute(DeleteAttributeRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteAttribute);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetObjectResponse> getObject(GetObjectRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getObject);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncObjectResponse> syncObject(SyncObjectRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncObject);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteObjectResponse> deleteObject(DeleteObjectRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteObject);
    }
}
