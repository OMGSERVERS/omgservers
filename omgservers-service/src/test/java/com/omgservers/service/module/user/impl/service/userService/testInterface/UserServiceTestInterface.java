package com.omgservers.service.module.user.impl.service.userService.testInterface;

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
import com.omgservers.service.module.user.impl.service.userService.UserService;
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
        return userService.getUser(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncUserResponse syncUser(final SyncUserRequest request) {
        return userService.syncUser(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteUserResponse deleteUser(final DeleteUserRequest request) {
        return userService.deleteUser(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public CreateTokenResponse createToken(final CreateTokenRequest request) {
        return userService.createToken(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public GetPlayerResponse getPlayer(final GetPlayerRequest request) {
        return userService.getPlayer(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public GetPlayerProfileResponse getPlayerProfile(final GetPlayerProfileRequest request) {
        return userService.getPlayerProfile(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public FindPlayerResponse findPlayer(final FindPlayerRequest request) {
        return userService.findPlayer(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncPlayerResponse syncPlayer(final SyncPlayerRequest request) {
        return userService.syncPlayer(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public UpdatePlayerProfileResponse updatePlayerProfile(final UpdatePlayerProfileRequest request) {
        return userService.updatePlayerProfile(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeletePlayerResponse deletePlayer(final DeletePlayerRequest request) {
        return userService.deletePlayer(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
