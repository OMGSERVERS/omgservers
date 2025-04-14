package com.omgservers.service.shard.user.service.testInterface;

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
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class UserServiceTestInterface {
    private static final long TIMEOUT = 1L;

    final UserService userService;

    public GetUserResponse getUser(final GetUserRequest request) {
        return userService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncUserResponse syncUser(final SyncUserRequest request) {
        return userService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteUserResponse deleteUser(final DeleteUserRequest request) {
        return userService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public CreateTokenResponse createToken(final CreateTokenRequest request) {
        return userService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public GetPlayerResponse getPlayer(final GetPlayerRequest request) {
        return userService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public GetPlayerProfileResponse getPlayerProfile(final GetPlayerProfileRequest request) {
        return userService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public FindPlayerResponse findPlayer(final FindPlayerRequest request) {
        return userService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncPlayerResponse syncPlayer(final SyncPlayerRequest request) {
        return userService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public UpdatePlayerProfileResponse updatePlayerProfile(final UpdatePlayerProfileRequest request) {
        return userService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeletePlayerResponse deletePlayer(final DeletePlayerRequest request) {
        return userService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
