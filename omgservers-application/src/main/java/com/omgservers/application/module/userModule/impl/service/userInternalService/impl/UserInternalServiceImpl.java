package com.omgservers.application.module.userModule.impl.service.userInternalService.impl;

import com.omgservers.application.module.userModule.impl.operation.getUserServiceApiClientOperation.GetUserServiceApiClientOperation;
import com.omgservers.application.module.userModule.impl.operation.getUserServiceApiClientOperation.UserServiceApiClient;
import com.omgservers.application.module.userModule.impl.service.userInternalService.UserInternalService;
import com.omgservers.application.module.userModule.impl.service.userInternalService.impl.method.syncUserMethod.SyncUserMethod;
import com.omgservers.application.module.userModule.impl.service.userInternalService.impl.method.validateCredentials.ValidateCredentialsMethod;
import com.omgservers.operation.calculateShard.CalculateShardOperation;
import com.omgservers.operation.handleInternalRequest.HandleInternalRequestOperation;
import com.omgservers.dto.userModule.SyncUserShardRequest;
import com.omgservers.dto.userModule.SyncUserInternalResponse;
import com.omgservers.dto.userModule.ValidateCredentialsShardRequest;
import com.omgservers.dto.userModule.ValidateCredentialsInternalResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class UserInternalServiceImpl implements UserInternalService {

    final GetUserServiceApiClientOperation getUserServiceApiClientOperation;
    final HandleInternalRequestOperation handleInternalRequestOperation;
    final CalculateShardOperation calculateShardOperation;

    final ValidateCredentialsMethod validateCredentialsMethod;
    final SyncUserMethod syncUserMethod;

    @Override
    public Uni<SyncUserInternalResponse> syncUser(final SyncUserShardRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncUserShardRequest::validate,
                getUserServiceApiClientOperation::getClient,
                UserServiceApiClient::syncUser,
                syncUserMethod::syncUser);
    }

    @Override
    public Uni<ValidateCredentialsInternalResponse> validateCredentials(ValidateCredentialsShardRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                ValidateCredentialsShardRequest::validate,
                getUserServiceApiClientOperation::getClient,
                UserServiceApiClient::validateCredentials,
                validateCredentialsMethod::validateCredentials);
    }
}
