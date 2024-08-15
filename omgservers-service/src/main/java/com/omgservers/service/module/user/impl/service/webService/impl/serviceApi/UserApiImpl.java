package com.omgservers.service.module.user.impl.service.webService.impl.serviceApi;

import com.omgservers.schema.module.user.CreateTokenRequest;
import com.omgservers.schema.module.user.CreateTokenResponse;
import com.omgservers.schema.module.user.DeletePlayerRequest;
import com.omgservers.schema.module.user.DeletePlayerResponse;
import com.omgservers.schema.module.user.DeleteUserRequest;
import com.omgservers.schema.module.user.DeleteUserResponse;
import com.omgservers.schema.module.user.FindPlayerRequest;
import com.omgservers.schema.module.user.FindPlayerResponse;
import com.omgservers.schema.module.user.GetPlayerAttributesRequest;
import com.omgservers.schema.module.user.GetPlayerAttributesResponse;
import com.omgservers.schema.module.user.GetPlayerProfileRequest;
import com.omgservers.schema.module.user.GetPlayerProfileResponse;
import com.omgservers.schema.module.user.GetPlayerRequest;
import com.omgservers.schema.module.user.GetPlayerResponse;
import com.omgservers.schema.module.user.GetUserRequest;
import com.omgservers.schema.module.user.GetUserResponse;
import com.omgservers.schema.module.user.SyncPlayerRequest;
import com.omgservers.schema.module.user.SyncPlayerResponse;
import com.omgservers.schema.module.user.SyncUserRequest;
import com.omgservers.schema.module.user.SyncUserResponse;
import com.omgservers.schema.module.user.UpdatePlayerAttributesRequest;
import com.omgservers.schema.module.user.UpdatePlayerAttributesResponse;
import com.omgservers.schema.module.user.UpdatePlayerProfileRequest;
import com.omgservers.schema.module.user.UpdatePlayerProfileResponse;
import com.omgservers.schema.model.internalRole.InternalRoleEnum;
import com.omgservers.service.module.user.impl.service.webService.WebService;
import com.omgservers.service.operation.handleApiRequest.HandleApiRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
@RolesAllowed({InternalRoleEnum.Names.SERVICE})
class UserApiImpl implements UserApi {

    final HandleApiRequestOperation handleApiRequestOperation;
    final WebService webService;

    @Override
    public Uni<GetUserResponse> getUser(final GetUserRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getUser);
    }

    @Override
    public Uni<SyncUserResponse> syncUser(final SyncUserRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncUser);
    }

    @Override
    public Uni<DeleteUserResponse> deleteUser(final DeleteUserRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteUser);
    }

    @Override
    public Uni<CreateTokenResponse> createToken(final CreateTokenRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::createToken);
    }

    @Override
    public Uni<GetPlayerResponse> getPlayer(GetPlayerRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getPlayer);
    }

    @Override
    public Uni<FindPlayerResponse> findPlayer(FindPlayerRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::findPlayer);
    }

    @Override
    public Uni<SyncPlayerResponse> syncPlayer(SyncPlayerRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncPlayer);
    }

    @Override
    public Uni<UpdatePlayerAttributesResponse> updatePlayerAttributes(UpdatePlayerAttributesRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::updatePlayerAttributes);
    }

    @Override
    public Uni<UpdatePlayerProfileResponse> updatePlayerProfile(UpdatePlayerProfileRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::updatePlayerProfile);
    }

    @Override
    public Uni<DeletePlayerResponse> deletePlayer(DeletePlayerRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deletePlayer);
    }

    @Override
    public Uni<GetPlayerAttributesResponse> getPlayerAttributes(GetPlayerAttributesRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getPlayerAttributes);
    }

    @Override
    public Uni<GetPlayerProfileResponse> getPlayerProfile(GetPlayerProfileRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getPlayerProfile);
    }
}
