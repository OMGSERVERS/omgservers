package com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.impl;

import com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.ServiceAccountHelpService;
import com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.impl.method.deleteServiceAccountMethod.DeleteServiceAccountMethod;
import com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.impl.method.getServiceAccountMethod.GetServiceAccountMethod;
import com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.impl.method.syncServiceAccountMethod.SyncServiceAccountMethod;
import com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.impl.method.validateCredentialsMethod.ValidateCredentialsMethod;
import com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.request.DeleteServiceAccountHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.request.GetServiceAccountHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.request.SyncServiceAccountHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.request.ValidateCredentialsHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.response.GetServiceAccountHelpResponse;
import com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.response.ValidateCredentialsHelpResponse;
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
