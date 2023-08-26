package com.omgservers.application.module.userModule.impl.service.userInternalService.impl;

import com.omgservers.application.module.userModule.impl.operation.getUserServiceApiClientOperation.GetUserServiceApiClientOperation;
import com.omgservers.application.module.userModule.impl.operation.getUserServiceApiClientOperation.UserServiceApiClient;
import com.omgservers.application.module.userModule.impl.service.userInternalService.UserInternalService;
import com.omgservers.application.module.userModule.impl.service.userInternalService.impl.method.syncUserMethod.SyncUserMethod;
import com.omgservers.application.module.userModule.impl.service.userInternalService.impl.method.validateCredentials.ValidateCredentialsMethod;
import com.omgservers.base.operation.calculateShard.CalculateShardOperation;
import com.omgservers.base.operation.handleInternalRequest.HandleInternalRequestOperation;
import com.omgservers.dto.userModule.SyncUserRoutedRequest;
import com.omgservers.dto.userModule.SyncUserInternalResponse;
import com.omgservers.dto.userModule.ValidateCredentialsRoutedRequest;
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
    public Uni<SyncUserInternalResponse> syncUser(final SyncUserRoutedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncUserRoutedRequest::validate,
                getUserServiceApiClientOperation::getClient,
                UserServiceApiClient::syncUser,
                syncUserMethod::syncUser);
    }

    @Override
    public Uni<ValidateCredentialsInternalResponse> validateCredentials(ValidateCredentialsRoutedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                ValidateCredentialsRoutedRequest::validate,
                getUserServiceApiClientOperation::getClient,
                UserServiceApiClient::validateCredentials,
                validateCredentialsMethod::validateCredentials);
    }
}
