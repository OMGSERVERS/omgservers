package com.omgservers.service.module.user.impl.service.webService.impl;

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