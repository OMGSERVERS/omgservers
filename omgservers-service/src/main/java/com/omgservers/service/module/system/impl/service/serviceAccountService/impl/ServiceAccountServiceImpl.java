package com.omgservers.service.module.system.impl.service.serviceAccountService.impl;

import com.omgservers.model.dto.system.DeleteServiceAccountRequest;
import com.omgservers.model.dto.system.DeleteServiceAccountResponse;
import com.omgservers.model.dto.system.FindServiceAccountRequest;
import com.omgservers.model.dto.system.FindServiceAccountResponse;
import com.omgservers.model.dto.system.GetServiceAccountRequest;
import com.omgservers.model.dto.system.GetServiceAccountResponse;
import com.omgservers.model.dto.system.SyncServiceAccountRequest;
import com.omgservers.model.dto.system.SyncServiceAccountResponse;
import com.omgservers.model.dto.system.ValidateCredentialsRequest;
import com.omgservers.model.dto.system.ValidateCredentialsResponse;
import com.omgservers.service.module.system.impl.service.serviceAccountService.ServiceAccountService;
import com.omgservers.service.module.system.impl.service.serviceAccountService.impl.method.deleteServiceAccount.DeleteServiceAccountMethod;
import com.omgservers.service.module.system.impl.service.serviceAccountService.impl.method.findServiceAccount.FindServiceAccountMethod;
import com.omgservers.service.module.system.impl.service.serviceAccountService.impl.method.getServiceAccount.GetServiceAccountMethod;
import com.omgservers.service.module.system.impl.service.serviceAccountService.impl.method.syncServiceAccount.SyncServiceAccountMethod;
import com.omgservers.service.module.system.impl.service.serviceAccountService.impl.method.validateCredentials.ValidateCredentialsMethod;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
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
    final FindServiceAccountMethod findServiceAccountMethod;
    final GetServiceAccountMethod getServiceAccountMethod;

    @Override
    public Uni<GetServiceAccountResponse> getServiceAccount(@Valid final GetServiceAccountRequest request) {
        return getServiceAccountMethod.getServiceAccount(request);
    }

    @Override
    public Uni<FindServiceAccountResponse> findServiceAccount(@Valid final FindServiceAccountRequest request) {
        return findServiceAccountMethod.findServiceAccount(request);
    }

    @Override
    public Uni<SyncServiceAccountResponse> syncServiceAccount(@Valid final SyncServiceAccountRequest request) {
        return syncServiceAccountMethod.syncServiceAccount(request);
    }

    @Override
    public Uni<DeleteServiceAccountResponse> deleteServiceAccount(@Valid final DeleteServiceAccountRequest request) {
        return deleteServiceAccountMethod.deleteServiceAccount(request);
    }

    @Override
    public Uni<ValidateCredentialsResponse> validateCredentials(@Valid final ValidateCredentialsRequest request) {
        return validateCredentialsInternalMethod.validateCredentials(request);
    }
}
