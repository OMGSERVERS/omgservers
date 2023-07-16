package com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.impl.method.validateCredentialsMethod;

import com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.ServiceAccountHelpService;
import com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.request.GetServiceAccountHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.response.GetServiceAccountHelpResponse;
import com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.request.ValidateCredentialsHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.response.ValidateCredentialsHelpResponse;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class ValidateCredentialsMethodImpl implements ValidateCredentialsMethod {

    final ServiceAccountHelpService serviceAccountHelpService;

    @Override
    public Uni<ValidateCredentialsHelpResponse> validateCredentials(ValidateCredentialsHelpRequest request) {
        ValidateCredentialsHelpRequest.validate(request);

        final var username = request.getUsername();
        final var getServiceAccountHelpRequest = new GetServiceAccountHelpRequest(username);
        return serviceAccountHelpService.getServiceAccount(getServiceAccountHelpRequest)
                .map(GetServiceAccountHelpResponse::getServiceAccount)
                .map(serviceAccount -> {
                    final var password = request.getPassword();
                    final var passwordHash = serviceAccount.getPasswordHash();
                    return BcryptUtil.matches(password, passwordHash);
                })
                .map(ValidateCredentialsHelpResponse::new);
    }
}
