package com.omgservers.service.module.user.impl.service.userService.impl;

import com.omgservers.model.dto.user.DeleteUserRequest;
import com.omgservers.model.dto.user.DeleteUserResponse;
import com.omgservers.model.dto.user.RespondClientRequest;
import com.omgservers.model.dto.user.SyncUserRequest;
import com.omgservers.model.dto.user.SyncUserResponse;
import com.omgservers.model.dto.user.ValidateCredentialsRequest;
import com.omgservers.model.dto.user.ValidateCredentialsResponse;
import com.omgservers.service.module.user.impl.operation.getUserModuleClient.GetUserModuleClientOperation;
import com.omgservers.service.module.user.impl.operation.getUserModuleClient.UserModuleClient;
import com.omgservers.service.module.user.impl.service.userService.UserService;
import com.omgservers.service.module.user.impl.service.userService.impl.method.deleteUser.DeleteUserMethod;
import com.omgservers.service.module.user.impl.service.userService.impl.method.respondClient.RespondClientMethod;
import com.omgservers.service.module.user.impl.service.userService.impl.method.syncUser.SyncUserMethod;
import com.omgservers.service.module.user.impl.service.userService.impl.method.validateCredentials.ValidateCredentialsMethod;
import com.omgservers.service.operation.calculateShard.CalculateShardOperation;
import com.omgservers.service.operation.handleInternalRequest.HandleInternalRequestOperation;
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

    final GetUserModuleClientOperation getUserModuleClientOperation;
    final HandleInternalRequestOperation handleInternalRequestOperation;
    final CalculateShardOperation calculateShardOperation;

    final ValidateCredentialsMethod validateCredentialsMethod;
    final RespondClientMethod respondClientMethod;
    final DeleteUserMethod deleteUserMethod;
    final SyncUserMethod syncUserMethod;

    @Override
    public Uni<SyncUserResponse> syncUser(@Valid final SyncUserRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getUserModuleClientOperation::getClient,
                UserModuleClient::syncUser,
                syncUserMethod::syncUser);
    }

    @Override
    public Uni<DeleteUserResponse> deleteUser(@Valid final DeleteUserRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getUserModuleClientOperation::getClient,
                UserModuleClient::deleteUser,
                deleteUserMethod::deleteUser);
    }

    @Override
    public Uni<ValidateCredentialsResponse> validateCredentials(@Valid final ValidateCredentialsRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getUserModuleClientOperation::getClient,
                UserModuleClient::validateCredentials,
                validateCredentialsMethod::validateCredentials);
    }

    @Override
    public Uni<Void> respondClient(@Valid final RespondClientRequest request) {
        return respondClientMethod.respondClient(request);
    }
}
