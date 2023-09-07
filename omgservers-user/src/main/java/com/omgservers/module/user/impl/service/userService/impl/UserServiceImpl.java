package com.omgservers.module.user.impl.service.userService.impl;

import com.omgservers.dto.user.RespondClientRequest;
import com.omgservers.dto.user.SyncUserRequest;
import com.omgservers.dto.user.SyncUserResponse;
import com.omgservers.dto.user.ValidateCredentialsRequest;
import com.omgservers.dto.user.ValidateCredentialsResponse;
import com.omgservers.module.user.impl.operation.getUserModuleClient.GetUserModuleClientOperation;
import com.omgservers.module.user.impl.operation.getUserModuleClient.UserModuleClient;
import com.omgservers.module.user.impl.service.userService.UserService;
import com.omgservers.module.user.impl.service.userService.impl.method.respondClient.RespondClientMethod;
import com.omgservers.module.user.impl.service.userService.impl.method.syncUser.SyncUserMethod;
import com.omgservers.module.user.impl.service.userService.impl.method.validateCredentials.ValidateCredentialsMethod;
import com.omgservers.operation.calculateShard.CalculateShardOperation;
import com.omgservers.operation.handleInternalRequest.HandleInternalRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
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
    final SyncUserMethod syncUserMethod;

    @Override
    public Uni<SyncUserResponse> syncUser(final SyncUserRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncUserRequest::validate,
                getUserModuleClientOperation::getClient,
                UserModuleClient::syncUser,
                syncUserMethod::syncUser);
    }

    @Override
    public Uni<ValidateCredentialsResponse> validateCredentials(ValidateCredentialsRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                ValidateCredentialsRequest::validate,
                getUserModuleClientOperation::getClient,
                UserModuleClient::validateCredentials,
                validateCredentialsMethod::validateCredentials);
    }

    @Override
    public Uni<Void> respondClient(RespondClientRequest request) {
        return respondClientMethod.respondClient(request);
    }
}
