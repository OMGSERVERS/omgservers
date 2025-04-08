package com.omgservers.service.shard.user.impl.service.webService.impl;

import com.omgservers.schema.module.user.CreateTokenRequest;
import com.omgservers.schema.module.user.CreateTokenResponse;
import com.omgservers.schema.module.user.DeletePlayerRequest;
import com.omgservers.schema.module.user.DeletePlayerResponse;
import com.omgservers.schema.module.user.DeleteUserRequest;
import com.omgservers.schema.module.user.DeleteUserResponse;
import com.omgservers.schema.module.user.FindPlayerRequest;
import com.omgservers.schema.module.user.FindPlayerResponse;
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
import com.omgservers.schema.module.user.UpdatePlayerProfileRequest;
import com.omgservers.schema.module.user.UpdatePlayerProfileResponse;
import com.omgservers.service.shard.user.impl.service.userService.UserService;
import com.omgservers.service.shard.user.impl.service.webService.WebService;
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
    public Uni<GetUserResponse> execute(GetUserRequest request) {
        return userService.execute(request);
    }

    @Override
    public Uni<SyncUserResponse> execute(final SyncUserRequest request) {
        return userService.execute(request);
    }

    @Override
    public Uni<DeleteUserResponse> execute(final DeleteUserRequest request) {
        return userService.execute(request);
    }

    @Override
    public Uni<CreateTokenResponse> execute(final CreateTokenRequest request) {
        return userService.execute(request);
    }

    @Override
    public Uni<GetPlayerResponse> execute(final GetPlayerRequest request) {
        return userService.execute(request);
    }

    @Override
    public Uni<GetPlayerProfileResponse> execute(final GetPlayerProfileRequest request) {
        return userService.execute(request);
    }

    @Override
    public Uni<FindPlayerResponse> execute(final FindPlayerRequest request) {
        return userService.execute(request);
    }

    @Override
    public Uni<SyncPlayerResponse> execute(final SyncPlayerRequest request) {
        return userService.execute(request);
    }

    @Override
    public Uni<UpdatePlayerProfileResponse> execute(final UpdatePlayerProfileRequest request) {
        return userService.execute(request);
    }

    @Override
    public Uni<DeletePlayerResponse> execute(final DeletePlayerRequest request) {
        return userService.execute(request);
    }
}