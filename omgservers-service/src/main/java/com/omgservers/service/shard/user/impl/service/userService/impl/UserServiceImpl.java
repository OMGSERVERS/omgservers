package com.omgservers.service.shard.user.impl.service.userService.impl;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
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
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.shard.user.impl.operation.getUserModuleClient.GetUserModuleClientOperation;
import com.omgservers.service.shard.user.impl.operation.getUserModuleClient.UserModuleClient;
import com.omgservers.service.shard.user.impl.service.userService.UserService;
import com.omgservers.service.shard.user.impl.service.userService.impl.method.player.deletePlayer.DeletePlayerMethod;
import com.omgservers.service.shard.user.impl.service.userService.impl.method.player.findPlayer.FindPlayerMethod;
import com.omgservers.service.shard.user.impl.service.userService.impl.method.player.getPlayer.GetPlayerMethod;
import com.omgservers.service.shard.user.impl.service.userService.impl.method.player.getPlayerProfile.GetPlayerProfileMethod;
import com.omgservers.service.shard.user.impl.service.userService.impl.method.player.syncPlayer.SyncPlayerMethod;
import com.omgservers.service.shard.user.impl.service.userService.impl.method.player.updatePlayerProfile.UpdatePlayerProfileMethod;
import com.omgservers.service.shard.user.impl.service.userService.impl.method.user.createToken.CreateTokenMethod;
import com.omgservers.service.shard.user.impl.service.userService.impl.method.user.deleteUser.DeleteUserMethod;
import com.omgservers.service.shard.user.impl.service.userService.impl.method.user.getUser.GetUserMethod;
import com.omgservers.service.shard.user.impl.service.userService.impl.method.user.syncUser.SyncUserMethod;
import com.omgservers.service.operation.server.CalculateShardOperation;
import com.omgservers.service.operation.server.HandleShardedRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class UserServiceImpl implements UserService {

    final UpdatePlayerProfileMethod updatePlayerProfileMethod;
    final GetPlayerProfileMethod getPlayerProfileMethod;
    final DeletePlayerMethod deletePlayerMethod;
    final CreateTokenMethod createTokenMethod;
    final SyncPlayerMethod syncPlayerMethod;
    final FindPlayerMethod findPlayerMethod;
    final DeleteUserMethod deleteUserMethod;
    final GetPlayerMethod getPlayerMethod;
    final SyncUserMethod syncUserMethod;
    final GetUserMethod getUserMethod;

    final HandleShardedRequestOperation handleShardedRequestOperation;
    final GetUserModuleClientOperation getUserModuleClientOperation;
    final CalculateShardOperation calculateShardOperation;

    @Override
    public Uni<GetUserResponse> getUser(@Valid final GetUserRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getUserModuleClientOperation::getClient,
                UserModuleClient::getUser,
                getUserMethod::getUser);
    }

    @Override
    public Uni<SyncUserResponse> syncUser(@Valid final SyncUserRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getUserModuleClientOperation::getClient,
                UserModuleClient::syncUser,
                syncUserMethod::syncUser);
    }

    @Override
    public Uni<SyncUserResponse> syncUserWithIdempotency(@Valid final SyncUserRequest request) {
        return syncUser(request)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.debug("Idempotency was violated, object={}, {}", request.getUser(), t.getMessage());
                            return Uni.createFrom().item(new SyncUserResponse(Boolean.FALSE));
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    @Override
    public Uni<DeleteUserResponse> deleteUser(@Valid final DeleteUserRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getUserModuleClientOperation::getClient,
                UserModuleClient::deleteUser,
                deleteUserMethod::deleteUser);
    }

    @Override
    public Uni<CreateTokenResponse> createToken(@Valid final CreateTokenRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getUserModuleClientOperation::getClient,
                UserModuleClient::createToken,
                createTokenMethod::createToken);
    }

    @Override
    public Uni<GetPlayerResponse> getPlayer(@Valid final GetPlayerRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getUserModuleClientOperation::getClient,
                UserModuleClient::getPlayer,
                getPlayerMethod::getPlayer);
    }

    @Override
    public Uni<GetPlayerProfileResponse> getPlayerProfile(@Valid final GetPlayerProfileRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getUserModuleClientOperation::getClient,
                UserModuleClient::getPlayerProfile,
                getPlayerProfileMethod::getPlayerProfile);
    }

    @Override
    public Uni<FindPlayerResponse> findPlayer(@Valid final FindPlayerRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getUserModuleClientOperation::getClient,
                UserModuleClient::findPlayer,
                findPlayerMethod::findPlayer);
    }

    @Override
    public Uni<SyncPlayerResponse> syncPlayer(@Valid final SyncPlayerRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getUserModuleClientOperation::getClient,
                UserModuleClient::syncPlayer,
                syncPlayerMethod::syncPlayer);
    }

    @Override
    public Uni<UpdatePlayerProfileResponse> updatePlayerProfile(@Valid final UpdatePlayerProfileRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getUserModuleClientOperation::getClient,
                UserModuleClient::updatePlayerProfile,
                updatePlayerProfileMethod::updatePlayerProfile);
    }

    @Override
    public Uni<DeletePlayerResponse> deletePlayer(@Valid final DeletePlayerRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getUserModuleClientOperation::getClient,
                UserModuleClient::deletePlayer,
                deletePlayerMethod::deletePlayer);
    }
}
