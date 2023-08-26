package com.omgservers.module.internal.impl.service.serviceAccountService.impl.method.validateCredentials;

import com.omgservers.module.internal.impl.service.serviceAccountService.ServiceAccountService;
import com.omgservers.dto.internalModule.ValidateCredentialsRequest;
import com.omgservers.dto.internalModule.ValidateCredentialsResponse;
import com.omgservers.dto.internalModule.GetServiceAccountHelpRequest;
import com.omgservers.dto.internalModule.GetServiceAccountHelpResponse;
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

    final ServiceAccountService serviceAccountService;

    @Override
    public Uni<ValidateCredentialsResponse> validateCredentials(ValidateCredentialsRequest request) {
        ValidateCredentialsRequest.validate(request);

        final var username = request.getUsername();
        final var getServiceAccountHelpRequest = new GetServiceAccountHelpRequest(username);
        return serviceAccountService.getServiceAccount(getServiceAccountHelpRequest)
                .map(GetServiceAccountHelpResponse::getServiceAccount)
                .map(serviceAccount -> {
                    final var password = request.getPassword();
                    final var passwordHash = serviceAccount.getPasswordHash();
                    return BcryptUtil.matches(password, passwordHash);
                })
                .map(ValidateCredentialsResponse::new);
    }
}
