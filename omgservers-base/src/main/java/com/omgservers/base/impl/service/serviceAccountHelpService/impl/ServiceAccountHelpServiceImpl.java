package com.omgservers.base.impl.service.serviceAccountHelpService.impl;

import com.omgservers.base.impl.service.serviceAccountHelpService.impl.method.deleteServiceAccountMethod.DeleteServiceAccountMethod;
import com.omgservers.base.impl.service.serviceAccountHelpService.impl.method.syncServiceAccountMethod.SyncServiceAccountMethod;
import com.omgservers.base.impl.service.serviceAccountHelpService.impl.method.validateCredentialsMethod.ValidateCredentialsMethod;
import com.omgservers.base.impl.service.serviceAccountHelpService.ServiceAccountHelpService;
import com.omgservers.base.impl.service.serviceAccountHelpService.impl.method.getServiceAccountMethod.GetServiceAccountMethod;
import com.omgservers.dto.internalModule.DeleteServiceAccountHelpRequest;
import com.omgservers.dto.internalModule.GetServiceAccountHelpRequest;
import com.omgservers.dto.internalModule.SyncServiceAccountHelpRequest;
import com.omgservers.base.impl.service.serviceAccountHelpService.request.ValidateCredentialsHelpRequest;
import com.omgservers.dto.internalModule.GetServiceAccountHelpResponse;
import com.omgservers.base.impl.service.serviceAccountHelpService.response.ValidateCredentialsHelpResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class ServiceAccountHelpServiceImpl implements ServiceAccountHelpService {

    final GetServiceAccountMethod getServiceAccountMethod;
    final SyncServiceAccountMethod syncServiceAccountMethod;
    final DeleteServiceAccountMethod deleteServiceAccountMethod;
    final ValidateCredentialsMethod validateCredentialsInternalMethod;

    @Override
    public Uni<GetServiceAccountHelpResponse> getServiceAccount(GetServiceAccountHelpRequest request) {
        return getServiceAccountMethod.getServiceAccount(request);
    }

    @Override
    public Uni<Void> syncServiceAccount(SyncServiceAccountHelpRequest request) {
        return syncServiceAccountMethod.syncServiceAccount(request);
    }

    @Override
    public Uni<Void> deleteServiceAccount(DeleteServiceAccountHelpRequest request) {
        return deleteServiceAccountMethod.deleteServiceAccount(request);
    }

    @Override
    public Uni<ValidateCredentialsHelpResponse> validateCredentials(ValidateCredentialsHelpRequest request) {
        return validateCredentialsInternalMethod.validateCredentials(request);
    }
}
