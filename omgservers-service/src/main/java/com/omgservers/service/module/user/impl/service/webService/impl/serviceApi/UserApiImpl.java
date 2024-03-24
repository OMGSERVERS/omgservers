package com.omgservers.service.module.user.impl.service.webService.impl.serviceApi;

import com.omgservers.model.dto.user.CreateTokenRequest;
import com.omgservers.model.dto.user.CreateTokenResponse;
import com.omgservers.model.dto.user.DeletePlayerRequest;
import com.omgservers.model.dto.user.DeletePlayerResponse;
import com.omgservers.model.dto.user.DeleteUserRequest;
import com.omgservers.model.dto.user.DeleteUserResponse;
import com.omgservers.model.dto.user.FindPlayerRequest;
import com.omgservers.model.dto.user.FindPlayerResponse;
import com.omgservers.model.dto.user.GetPlayerAttributesRequest;
import com.omgservers.model.dto.user.GetPlayerAttributesResponse;
import com.omgservers.model.dto.user.GetPlayerProfileRequest;
import com.omgservers.model.dto.user.GetPlayerProfileResponse;
import com.omgservers.model.dto.user.GetPlayerRequest;
import com.omgservers.model.dto.user.GetPlayerResponse;
import com.omgservers.model.dto.user.GetUserRequest;
import com.omgservers.model.dto.user.GetUserResponse;
import com.omgservers.model.dto.user.SyncPlayerRequest;
import com.omgservers.model.dto.user.SyncPlayerResponse;
import com.omgservers.model.dto.user.SyncUserRequest;
import com.omgservers.model.dto.user.SyncUserResponse;
import com.omgservers.model.dto.user.UpdatePlayerAttributesRequest;
import com.omgservers.model.dto.user.UpdatePlayerAttributesResponse;
import com.omgservers.model.dto.user.UpdatePlayerProfileRequest;
import com.omgservers.model.dto.user.UpdatePlayerProfileResponse;
import com.omgservers.service.module.user.impl.service.webService.WebService;
import com.omgservers.service.operation.handleApiRequest.HandleApiRequestOperation;
import io.smallrye.mutiny.Uni;
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
