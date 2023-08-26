package com.omgservers.module.internal.impl.service.serviceAccountService.impl;

import com.omgservers.module.internal.impl.service.serviceAccountService.ServiceAccountService;
import com.omgservers.module.internal.impl.service.serviceAccountService.impl.method.deleteServiceAccount.DeleteServiceAccountMethod;
import com.omgservers.module.internal.impl.service.serviceAccountService.impl.method.getServiceAccount.GetServiceAccountMethod;
import com.omgservers.module.internal.impl.service.serviceAccountService.impl.method.syncServiceAccount.SyncServiceAccountMethod;
import com.omgservers.module.internal.impl.service.serviceAccountService.impl.method.validateCredentials.ValidateCredentialsMethod;
import com.omgservers.dto.internalModule.DeleteServiceAccountHelpRequest;
import com.omgservers.dto.internalModule.GetServiceAccountHelpRequest;
import com.omgservers.dto.internalModule.GetServiceAccountHelpResponse;
import com.omgservers.dto.internalModule.SyncServiceAccountRequest;
import com.omgservers.dto.internalModule.ValidateCredentialsRequest;
import com.omgservers.dto.internalModule.ValidateCredentialsResponse;
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
    public Uni<GetServiceAccountHelpResponse> getServiceAccount(GetServiceAccountHelpRequest request) {
        return getServiceAccountMethod.getServiceAccount(request);
    }

    @Override
    public Uni<Void> syncServiceAccount(SyncServiceAccountRequest request) {
        return syncServiceAccountMethod.syncServiceAccount(request);
    }

    @Override
    public Uni<Void> deleteServiceAccount(DeleteServiceAccountHelpRequest request) {
        return deleteServiceAccountMethod.deleteServiceAccount(request);
    }

    @Override
    public Uni<ValidateCredentialsResponse> validateCredentials(ValidateCredentialsRequest request) {
        return validateCredentialsInternalMethod.validateCredentials(request);
    }
}
