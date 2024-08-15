package com.omgservers.service.server.security;

import com.omgservers.schema.model.user.UserRoleEnum;
import io.quarkus.security.identity.AuthenticationRequestContext;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.identity.SecurityIdentityAugmentor;
import io.quarkus.security.runtime.QuarkusSecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.jwt.JsonWebToken;

@ApplicationScoped
public class ServiceIdentityAugmentor implements SecurityIdentityAugmentor {

    @Override
    public Uni<SecurityIdentity> augment(final SecurityIdentity securityIdentity,
                                         final AuthenticationRequestContext context) {
        if (securityIdentity.getPrincipal() instanceof JsonWebToken jsonWebToken) {
            final var securityIdentityBuilder = QuarkusSecurityIdentity.builder(securityIdentity);

            securityIdentityBuilder.addAttribute(ServiceSecurityAttributes.TOKEN_ID.getAttributeName(),
                    jsonWebToken.getTokenID());
            securityIdentityBuilder.addAttribute(ServiceSecurityAttributes.RAW_TOKEN.getAttributeName(),
                    jsonWebToken.getRawToken());

            jsonWebToken.<String>claim(ServiceSecurityAttributes.RUNTIME_ID.getAttributeName())
                    .ifPresent(claim -> securityIdentityBuilder
                            .addAttribute(ServiceSecurityAttributes.RUNTIME_ID.getAttributeName(),
                                    Long.valueOf(claim)));
            jsonWebToken.<String>claim(ServiceSecurityAttributes.USER_ID.getAttributeName())
                    .ifPresent(claim -> securityIdentityBuilder
                            .addAttribute(ServiceSecurityAttributes.USER_ID.getAttributeName(),
                                    Long.valueOf(claim)));
            jsonWebToken.<String>claim(ServiceSecurityAttributes.CLIENT_ID.getAttributeName())
                    .ifPresent(claim -> securityIdentityBuilder
                            .addAttribute(ServiceSecurityAttributes.CLIENT_ID.getAttributeName(),
                                    Long.valueOf(claim)));
            jsonWebToken.<String>claim(ServiceSecurityAttributes.USER_ROLE.getAttributeName())
                    .ifPresent(claim -> securityIdentityBuilder
                            .addAttribute(ServiceSecurityAttributes.USER_ROLE.getAttributeName(),
                                    UserRoleEnum.fromString(claim)));

            final var augmentedSecurityIdentity = securityIdentityBuilder.build();
            return Uni.createFrom().item(augmentedSecurityIdentity);
        } else {
            return Uni.createFrom().item(securityIdentity);
        }
    }
}
