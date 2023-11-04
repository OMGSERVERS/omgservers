package com.omgservers.service.module.system.impl.service.serviceAccountService.impl.method.validateCredentials;

import com.omgservers.model.dto.system.GetServiceAccountRequest;
import com.omgservers.model.dto.system.GetServiceAccountResponse;
import com.omgservers.model.dto.system.ValidateCredentialsRequest;
import com.omgservers.model.dto.system.ValidateCredentialsResponse;
import com.omgservers.service.module.system.impl.service.serviceAccountService.ServiceAccountService;
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
        final var username = request.getUsername();
        final var getServiceAccountHelpRequest = new GetServiceAccountRequest(username);
        return serviceAccountService.getServiceAccount(getServiceAccountHelpRequest)
                .map(GetServiceAccountResponse::getServiceAccount)
                .map(serviceAccount -> {
                    final var password = request.getPassword();
                    final var passwordHash = serviceAccount.getPasswordHash();
                    return BcryptUtil.matches(password, passwordHash);
                })
                .map(ValidateCredentialsResponse::new);
    }
}
