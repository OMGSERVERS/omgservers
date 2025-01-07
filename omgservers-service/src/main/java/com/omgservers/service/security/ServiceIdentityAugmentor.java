package com.omgservers.service.security;

import com.omgservers.schema.model.user.UserRoleEnum;
import io.quarkus.security.identity.AuthenticationRequestContext;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.identity.SecurityIdentityAugmentor;
import io.quarkus.security.runtime.QuarkusSecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;

@ApplicationScoped
public class ServiceIdentityAugmentor implements SecurityIdentityAugmentor {

    @Override
    public Uni<SecurityIdentity> augment(final SecurityIdentity securityIdentity,
                                         final AuthenticationRequestContext context) {
        if (securityIdentity.getPrincipal() instanceof JsonWebToken jsonWebToken) {
            final var securityIdentityBuilder = QuarkusSecurityIdentity.builder(securityIdentity);

            securityIdentityBuilder.addAttribute(SecurityAttributesEnum.TOKEN_ID.getAttributeName(),
                    jsonWebToken.getTokenID());
            securityIdentityBuilder.addAttribute(SecurityAttributesEnum.RAW_TOKEN.getAttributeName(),
                    jsonWebToken.getRawToken());

            jsonWebToken.<String>claim(Claims.sub)
                    .ifPresent(claim -> securityIdentityBuilder
                            .addAttribute(SecurityAttributesEnum.SUBJECT.getAttributeName(),
                                    claim));

            jsonWebToken.<String>claim(SecurityAttributesEnum.RUNTIME_ID.getAttributeName())
                    .ifPresent(claim -> securityIdentityBuilder
                            .addAttribute(SecurityAttributesEnum.RUNTIME_ID.getAttributeName(),
                                    Long.valueOf(claim)));
            jsonWebToken.<String>claim(SecurityAttributesEnum.USER_ID.getAttributeName())
                    .ifPresent(claim -> securityIdentityBuilder
                            .addAttribute(SecurityAttributesEnum.USER_ID.getAttributeName(),
                                    Long.valueOf(claim)));
            jsonWebToken.<String>claim(SecurityAttributesEnum.USER_ROLE.getAttributeName())
                    .ifPresent(claim -> securityIdentityBuilder
                            .addAttribute(SecurityAttributesEnum.USER_ROLE.getAttributeName(),
                                    UserRoleEnum.fromString(claim)));

            final var augmentedSecurityIdentity = securityIdentityBuilder.build();
            return Uni.createFrom().item(augmentedSecurityIdentity);
        } else {
            return Uni.createFrom().item(securityIdentity);
        }
    }
}
