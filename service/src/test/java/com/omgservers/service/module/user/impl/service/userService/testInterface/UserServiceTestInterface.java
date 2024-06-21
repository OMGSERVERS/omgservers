package com.omgservers.service.module.user.impl.service.userService.testInterface;

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

    public GetPlayerAttributesResponse getPlayerAttributes(final GetPlayerAttributesRequest request) {
        return userService.getPlayerAttributes(request)
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

    public UpdatePlayerAttributesResponse updatePlayerAttributes(final UpdatePlayerAttributesRequest request) {
        return userService.updatePlayerAttributes(request)
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
