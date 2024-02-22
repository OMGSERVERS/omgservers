package com.omgservers.service.security;

import com.omgservers.model.dto.system.ValidateCredentialsRequest;
import com.omgservers.model.dto.system.ValidateCredentialsResponse;
import com.omgservers.model.internalRole.InternalRoleEnum;
import com.omgservers.service.module.system.SystemModule;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import io.quarkus.security.AuthenticationFailedException;
import io.quarkus.security.identity.AuthenticationRequestContext;
import io.quarkus.security.identity.IdentityProvider;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.identity.request.UsernamePasswordAuthenticationRequest;
import io.quarkus.security.runtime.QuarkusPrincipal;
import io.quarkus.security.runtime.QuarkusSecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.control.ActivateRequestContext;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class PasswordIdentityProvider implements IdentityProvider<UsernamePasswordAuthenticationRequest> {

    final SystemModule systemModule;
    final GetConfigOperation getConfigOperation;

    @Override
    public Class<UsernamePasswordAuthenticationRequest> getRequestType() {
        return UsernamePasswordAuthenticationRequest.class;
    }

    @Override
    @ActivateRequestContext
    public Uni<SecurityIdentity> authenticate(UsernamePasswordAuthenticationRequest request,
                                              AuthenticationRequestContext context) {
        final var username = request.getUsername();
        final var password = new String(request.getPassword().getPassword());

        if (username.equals(getConfigOperation.getServiceConfig().adminUsername())) {
            if (password.equals(getConfigOperation.getServiceConfig().adminPassword())) {
                final var principal = "admin/" + username;
                return Uni.createFrom().item(QuarkusSecurityIdentity.builder()
                        .setPrincipal(new QuarkusPrincipal(principal))
                        .addRole(InternalRoleEnum.Names.ADMIN)
                        .setAnonymous(false)
                        .build());
            } else {
                log.warn("Authentication failed, username={}", username);
                throw new AuthenticationFailedException();
            }
        } else {
            return validateCredentials(username, password)
                    .map(valid -> {
                        if (valid) {
                            final var principal = "sa/" + username;
                            return (SecurityIdentity) QuarkusSecurityIdentity.builder()
                                    .setPrincipal(new QuarkusPrincipal(principal))
                                    .addRole(InternalRoleEnum.Names.SERVICE)
                                    .setAnonymous(false)
                                    .build();
                        } else {
                            log.warn("Authentication failed, username={}", username);
                            throw new AuthenticationFailedException();
                        }
                    })
                    .onFailure().transform(t -> {
                        log.warn("Authentication failed, {}", t.getMessage());
                        return new AuthenticationFailedException();
                    });
        }
    }

    Uni<Boolean> validateCredentials(final String username, final String password) {
        final var request = new ValidateCredentialsRequest(username, password);
        return systemModule.getServiceAccountService().validateCredentials(request)
                .map(ValidateCredentialsResponse::getValid);
    }
}
