package com.omgservers.module.system.impl.service.serviceAccountService.impl;

import com.omgservers.module.system.impl.service.serviceAccountService.impl.method.deleteServiceAccount.DeleteServiceAccountMethod;
import com.omgservers.module.system.impl.service.serviceAccountService.impl.method.getServiceAccount.GetServiceAccountMethod;
import com.omgservers.module.system.impl.service.serviceAccountService.impl.method.syncServiceAccount.SyncServiceAccountMethod;
import com.omgservers.module.system.impl.service.serviceAccountService.impl.method.validateCredentials.ValidateCredentialsMethod;
import com.omgservers.module.system.impl.service.serviceAccountService.ServiceAccountService;
import com.omgservers.model.dto.system.DeleteServiceAccountRequest;
import com.omgservers.model.dto.system.GetServiceAccountRequest;
import com.omgservers.model.dto.system.GetServiceAccountResponse;
import com.omgservers.model.dto.system.SyncServiceAccountRequest;
import com.omgservers.model.dto.system.ValidateCredentialsRequest;
import com.omgservers.model.dto.system.ValidateCredentialsResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class ServiceAccountServiceImpl implements ServiceAccountService {

    final ValidateCredentialsMethod validateCredentialsInternalMethod;
    final DeleteServiceAccountMethod deleteServiceAccountMethod;
    final SyncServiceAccountMethod syncServiceAccountMethod;
    final GetServiceAccountMethod getServiceAccountMethod;

    @Override
    public Uni<GetServiceAccountResponse> getServiceAccount(GetServiceAccountRequest request) {
        return getServiceAccountMethod.getServiceAccount(request);
    }

    @Override
    public Uni<Void> syncServiceAccount(SyncServiceAccountRequest request) {
        return syncServiceAccountMethod.syncServiceAccount(request);
    }

    @Override
    public Uni<Void> deleteServiceAccount(DeleteServiceAccountRequest request) {
        return deleteServiceAccountMethod.deleteServiceAccount(request);
    }

    @Override
    public Uni<ValidateCredentialsResponse> validateCredentials(ValidateCredentialsRequest request) {
        return validateCredentialsInternalMethod.validateCredentials(request);
    }
}
