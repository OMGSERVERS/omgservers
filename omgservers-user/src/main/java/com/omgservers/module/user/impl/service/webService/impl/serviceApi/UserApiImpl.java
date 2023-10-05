package com.omgservers.module.user.impl.service.webService.impl.serviceApi;

import com.omgservers.dto.user.CreateTokenRequest;
import com.omgservers.dto.user.CreateTokenResponse;
import com.omgservers.dto.user.DeleteClientRequest;
import com.omgservers.dto.user.DeleteClientResponse;
import com.omgservers.dto.user.DeletePlayerRequest;
import com.omgservers.dto.user.DeletePlayerResponse;
import com.omgservers.dto.user.FindPlayerRequest;
import com.omgservers.dto.user.FindPlayerResponse;
import com.omgservers.dto.user.GetClientRequest;
import com.omgservers.dto.user.GetClientResponse;
import com.omgservers.dto.user.GetPlayerAttributesRequest;
import com.omgservers.dto.user.GetPlayerAttributesResponse;
import com.omgservers.dto.user.GetPlayerObjectRequest;
import com.omgservers.dto.user.GetPlayerObjectResponse;
import com.omgservers.dto.user.GetPlayerRequest;
import com.omgservers.dto.user.GetPlayerResponse;
import com.omgservers.dto.user.IntrospectTokenRequest;
import com.omgservers.dto.user.IntrospectTokenResponse;
import com.omgservers.dto.user.SyncClientRequest;
import com.omgservers.dto.user.SyncClientResponse;
import com.omgservers.dto.user.SyncPlayerRequest;
import com.omgservers.dto.user.SyncPlayerResponse;
import com.omgservers.dto.user.SyncUserRequest;
import com.omgservers.dto.user.SyncUserResponse;
import com.omgservers.dto.user.UpdatePlayerAttributesRequest;
import com.omgservers.dto.user.UpdatePlayerAttributesResponse;
import com.omgservers.dto.user.UpdatePlayerObjectRequest;
import com.omgservers.dto.user.UpdatePlayerObjectResponse;
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
    public Uni<UpdatePlayerAttributesResponse> updatePlayerAttributes(UpdatePlayerAttributesRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::updatePlayerAttributes);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<UpdatePlayerObjectResponse> updatePlayerObject(UpdatePlayerObjectRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::updatePlayerObject);
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
    public Uni<GetPlayerAttributesResponse> getPlayerAttributes(GetPlayerAttributesRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getPlayerAttributes);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetPlayerObjectResponse> getPlayerObject(GetPlayerObjectRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getPlayerObject);
    }
}
