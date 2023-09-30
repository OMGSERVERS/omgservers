package com.omgservers.module.user.impl;

import com.omgservers.dto.user.IntrospectTokenRequest;
import com.omgservers.module.user.UserModule;
import io.quarkus.security.AuthenticationFailedException;
import io.quarkus.security.identity.AuthenticationRequestContext;
import io.quarkus.security.identity.IdentityProvider;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.identity.request.TokenAuthenticationRequest;
import io.quarkus.security.runtime.QuarkusPrincipal;
import io.quarkus.security.runtime.QuarkusSecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.control.ActivateRequestContext;
import jakarta.enterprise.inject.Instance;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.security.Permission;
import java.util.function.Function;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class TokenIdentityProvider implements IdentityProvider<TokenAuthenticationRequest> {

    final UserModule userModule;
    final Instance<Function<Permission, Uni<Boolean>>> permissionCheckers;

    @Override
    public Class<TokenAuthenticationRequest> getRequestType() {
        return TokenAuthenticationRequest.class;
    }

    @Override
    @ActivateRequestContext
    public Uni<SecurityIdentity> authenticate(TokenAuthenticationRequest request,
                                              AuthenticationRequestContext context) {
        String tokenType = request.getToken().getType();
        if (!tokenType.equals("bearer")) {
            return Uni.createFrom().nullItem();
        }

        final String rawToken = request.getToken().getToken();
        if (rawToken == null || rawToken.isBlank()) {
            return Uni.createFrom().nullItem();
        }

        IntrospectTokenRequest introspectTokenRequest = new IntrospectTokenRequest(rawToken);
        return userModule.getTokenService().introspectToken(introspectTokenRequest)
                .map(response -> {
                    final var tokenObject = response.getTokenObject();
                    final var userId = tokenObject.getUserId();
                    final var role = tokenObject.getRole().getName();
                    final var principal = role + "/" + userId.toString();
                    log.debug("User was authenticated, principal={}, role={}", principal, role);

                    return (SecurityIdentity) QuarkusSecurityIdentity.builder()
                            .setPrincipal(new QuarkusPrincipal(principal))
                            .addAttribute("userId", userId)
                            .addRole(role)
                            .addPermissionCheckers(permissionCheckers.stream().toList())
                            .setAnonymous(false)
                            .build();
                })
                .onFailure()
                .transform(t -> {
                    log.warn("Authentication failed, {}", t.getMessage());
                    return new AuthenticationFailedException(t);
                });
    }
}
