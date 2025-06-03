package com.omgservers.connector.security;

import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.quarkus.security.identity.IdentityProviderManager;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.identity.request.AuthenticationRequest;
import io.quarkus.security.identity.request.TokenAuthenticationRequest;
import io.quarkus.smallrye.jwt.runtime.auth.JsonWebTokenCredential;
import io.quarkus.vertx.http.runtime.security.ChallengeData;
import io.quarkus.vertx.http.runtime.security.HttpAuthenticationMechanism;
import io.quarkus.vertx.http.runtime.security.HttpCredentialTransport;
import io.quarkus.vertx.http.runtime.security.HttpSecurityUtils;
import io.smallrye.mutiny.Uni;
import io.vertx.ext.web.RoutingContext;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Slf4j
@ApplicationScoped
public class WsTokenAuthenticationMechanism implements HttpAuthenticationMechanism {

    private final String WS_TOKEN = "ws_token";

    @Override
    public Uni<SecurityIdentity> authenticate(final RoutingContext context,
                                              final IdentityProviderManager identityProviderManager) {
        final var wsToken = context.request().getParam(WS_TOKEN);
        if (Objects.nonNull(wsToken)) {
            final var jsonWebTokenCredential = new JsonWebTokenCredential(wsToken);
            final var request = new TokenAuthenticationRequest(jsonWebTokenCredential);
            HttpSecurityUtils.setRoutingContextAttribute(request, context);
            context.put(HttpAuthenticationMechanism.class.getName(), this);
            return identityProviderManager.authenticate(request);
        }

        return Uni.createFrom().optional(Optional.empty());
    }

    @Override
    public Uni<ChallengeData> getChallenge(final RoutingContext context) {
        final var result = new ChallengeData(HttpResponseStatus.UNAUTHORIZED.code(),
                HttpHeaderNames.WWW_AUTHENTICATE, WS_TOKEN);
        return Uni.createFrom().item(result);
    }

    @Override
    public Set<Class<? extends AuthenticationRequest>> getCredentialTypes() {
        return Collections.singleton(TokenAuthenticationRequest.class);
    }

    @Override
    public Uni<HttpCredentialTransport> getCredentialTransport(final RoutingContext context) {
        return Uni.createFrom()
                .item(new HttpCredentialTransport(HttpCredentialTransport.Type.AUTHORIZATION, WS_TOKEN));
    }
}
