package com.omgservers.service.shard.user.impl.service.webService.impl;

import com.omgservers.schema.shard.user.CreateTokenRequest;
import com.omgservers.schema.shard.user.CreateTokenResponse;
import com.omgservers.schema.shard.user.DeletePlayerRequest;
import com.omgservers.schema.shard.user.DeletePlayerResponse;
import com.omgservers.schema.shard.user.DeleteUserRequest;
import com.omgservers.schema.shard.user.DeleteUserResponse;
import com.omgservers.schema.shard.user.FindPlayerRequest;
import com.omgservers.schema.shard.user.FindPlayerResponse;
import com.omgservers.schema.shard.user.GetPlayerProfileRequest;
import com.omgservers.schema.shard.user.GetPlayerProfileResponse;
import com.omgservers.schema.shard.user.GetPlayerRequest;
import com.omgservers.schema.shard.user.GetPlayerResponse;
import com.omgservers.schema.shard.user.GetUserRequest;
import com.omgservers.schema.shard.user.GetUserResponse;
import com.omgservers.schema.shard.user.SyncPlayerRequest;
import com.omgservers.schema.shard.user.SyncPlayerResponse;
import com.omgservers.schema.shard.user.SyncUserRequest;
import com.omgservers.schema.shard.user.SyncUserResponse;
import com.omgservers.schema.shard.user.UpdatePlayerProfileRequest;
import com.omgservers.schema.shard.user.UpdatePlayerProfileResponse;
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