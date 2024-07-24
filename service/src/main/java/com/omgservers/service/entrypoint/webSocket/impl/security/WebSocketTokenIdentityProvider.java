package com.omgservers.service.entrypoint.webSocket.impl.security;

import com.omgservers.model.user.UserRoleEnum;
import com.omgservers.model.wsToken.WsToken;
import io.quarkus.security.AuthenticationFailedException;
import io.quarkus.security.identity.AuthenticationRequestContext;
import io.quarkus.security.identity.IdentityProvider;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.identity.request.TokenAuthenticationRequest;
import io.quarkus.security.runtime.QuarkusPrincipal;
import io.quarkus.security.runtime.QuarkusSecurityIdentity;
import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.auth.principal.ParseException;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.control.ActivateRequestContext;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class WebSocketTokenIdentityProvider implements IdentityProvider<TokenAuthenticationRequest> {

    private final JWTParser jwtParser;

    @Override
    public Class<TokenAuthenticationRequest> getRequestType() {
        return TokenAuthenticationRequest.class;
    }

    @Override
    @ActivateRequestContext
    public Uni<SecurityIdentity> authenticate(TokenAuthenticationRequest request,
                                              AuthenticationRequestContext context) {
        final var tokenType = request.getToken().getType();
        if (!tokenType.equals(WsToken.WS_TOKEN)) {
            return Uni.createFrom().nullItem();
        }

        final String wsToken = request.getToken().getToken();
        if (wsToken == null || wsToken.isBlank()) {
            return Uni.createFrom().nullItem();
        }

        try {
            final var jsonWebToken = jwtParser.parse(wsToken);
            final var securityIdentity = createSecurityIdentity(jsonWebToken);

            return Uni.createFrom().item(securityIdentity);
        } catch (ParseException | NumberFormatException e) {
            throw new AuthenticationFailedException(e);
        }
    }

    SecurityIdentity createSecurityIdentity(final JsonWebToken jsonWebToken) {
        final var subject = jsonWebToken.getSubject();

        final var builder = QuarkusSecurityIdentity.builder();
        builder.setPrincipal(new QuarkusPrincipal(subject));

        jsonWebToken.getGroups().forEach(builder::addRole);

        builder.addAttribute("subject", Long.valueOf(subject));
        builder.addAttribute("tokenId", jsonWebToken.getTokenID());
        jsonWebToken.<String>claim(WsToken.RUNTIME_ID_CLAIM)
                .ifPresent(claim -> builder.addAttribute("runtimeId", Long.valueOf(claim)));
        jsonWebToken.<String>claim(WsToken.USER_ROLE_CLAIM)
                .ifPresent(claim -> builder.addAttribute("userRole", UserRoleEnum.fromString(claim)));

        builder.setAnonymous(false);
        final var securityIdentity = builder.build();
        return securityIdentity;
    }
}
