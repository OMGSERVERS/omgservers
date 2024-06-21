package com.omgservers.service.module.system.impl.service.serviceAccountService.impl.method.validateCredentials;

import com.omgservers.model.dto.system.FindServiceAccountRequest;
import com.omgservers.model.dto.system.FindServiceAccountResponse;
import com.omgservers.model.dto.system.ValidateCredentialsRequest;
import com.omgservers.model.dto.system.ValidateCredentialsResponse;
import com.omgservers.model.serviceAccount.ServiceAccountModel;
import com.omgservers.service.module.system.SystemModule;
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

    final SystemModule systemModule;

    @Override
    public Uni<ValidateCredentialsResponse> validateCredentials(final ValidateCredentialsRequest request) {
        log.debug("Validate credentials, request={}", request);

        final var username = request.getUsername();
        return findServiceAccount(username)
                .map(serviceAccount -> {
                    final var password = request.getPassword();
                    final var passwordHash = serviceAccount.getPasswordHash();
                    return BcryptUtil.matches(password, passwordHash);
                })
                .map(ValidateCredentialsResponse::new);
    }

    Uni<ServiceAccountModel> findServiceAccount(final String username) {
        final var request = new FindServiceAccountRequest(username);
        return systemModule.getServiceAccountService().findServiceAccount(request)
                .map(FindServiceAccountResponse::getServiceAccount);
    }
}
