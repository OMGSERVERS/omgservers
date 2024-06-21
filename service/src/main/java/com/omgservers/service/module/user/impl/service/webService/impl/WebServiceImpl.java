package com.omgservers.service.module.user.impl.service.webService.impl;

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
import com.omgservers.service.module.user.impl.service.userService.UserService;
import com.omgservers.service.module.user.impl.service.webService.WebService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class WebServiceImpl implements WebService {

    final UserService userService;

    @Override
    public Uni<GetUserResponse> getUser(GetUserRequest request) {
        return userService.getUser(request);
    }

    @Override
    public Uni<SyncUserResponse> syncUser(final SyncUserRequest request) {
        return userService.syncUser(request);
    }

    @Override
    public Uni<DeleteUserResponse> deleteUser(final DeleteUserRequest request) {
        return userService.deleteUser(request);
    }

    @Override
    public Uni<CreateTokenResponse> createToken(final CreateTokenRequest request) {
        return userService.createToken(request);
    }

    @Override
    public Uni<GetPlayerResponse> getPlayer(final GetPlayerRequest request) {
        return userService.getPlayer(request);
    }

    @Override
    public Uni<GetPlayerAttributesResponse> getPlayerAttributes(final GetPlayerAttributesRequest request) {
        return userService.getPlayerAttributes(request);
    }

    @Override
    public Uni<GetPlayerProfileResponse> getPlayerProfile(final GetPlayerProfileRequest request) {
        return userService.getPlayerProfile(request);
    }

    @Override
    public Uni<FindPlayerResponse> findPlayer(final FindPlayerRequest request) {
        return userService.findPlayer(request);
    }

    @Override
    public Uni<SyncPlayerResponse> syncPlayer(final SyncPlayerRequest request) {
        return userService.syncPlayer(request);
    }

    @Override
    public Uni<UpdatePlayerAttributesResponse> updatePlayerAttributes(final UpdatePlayerAttributesRequest request) {
        return userService.updatePlayerAttributes(request);
    }

    @Override
    public Uni<UpdatePlayerProfileResponse> updatePlayerProfile(final UpdatePlayerProfileRequest request) {
        return userService.updatePlayerProfile(request);
    }

    @Override
    public Uni<DeletePlayerResponse> deletePlayer(final DeletePlayerRequest request) {
        return userService.deletePlayer(request);
    }
}