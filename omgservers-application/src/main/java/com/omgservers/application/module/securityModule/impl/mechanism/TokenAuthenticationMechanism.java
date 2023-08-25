package com.omgservers.application.module.securityModule.impl.mechanism;

import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.quarkus.security.credential.TokenCredential;
import io.quarkus.security.identity.IdentityProviderManager;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.identity.request.AuthenticationRequest;
import io.quarkus.security.identity.request.TokenAuthenticationRequest;
import io.quarkus.vertx.http.runtime.security.ChallengeData;
import io.quarkus.vertx.http.runtime.security.HttpAuthenticationMechanism;
import io.quarkus.vertx.http.runtime.security.HttpCredentialTransport;
import io.quarkus.vertx.http.runtime.security.HttpSecurityUtils;
import io.smallrye.mutiny.Uni;
import io.vertx.ext.web.RoutingContext;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Priority(1)
@ApplicationScoped
public class TokenAuthenticationMechanism implements HttpAuthenticationMechanism {

    private static final String BEARER = "bearer";
    private static final String BEARER_PREFIX = BEARER + " ";
    private static final String LOWERCASE_BEARER_PREFIX = BEARER_PREFIX.toLowerCase(Locale.ENGLISH);
    private static final int PREFIX_LENGTH = BEARER_PREFIX.length();

    @Override
    public Uni<SecurityIdentity> authenticate(RoutingContext context, IdentityProviderManager identityProviderManager) {
        List<String> authHeaders = context.request().headers().getAll(HttpHeaderNames.AUTHORIZATION);
        if (authHeaders != null) {
            for (String current : authHeaders) {
                if (current.toLowerCase(Locale.ENGLISH).startsWith(LOWERCASE_BEARER_PREFIX)) {

                    var base64Challenge = current.substring(PREFIX_LENGTH);
                    var token = new TokenCredential(base64Challenge, BEARER);
                    var request = new TokenAuthenticationRequest(token);

                    HttpSecurityUtils.setRoutingContextAttribute(request, context);
                    context.put(HttpAuthenticationMechanism.class.getName(), this);

                    return identityProviderManager.authenticate(request);
                }
            }
        }

        return Uni.createFrom().optional(Optional.empty());
    }

    @Override
    public Uni<ChallengeData> getChallenge(RoutingContext context) {
        var result = new ChallengeData(HttpResponseStatus.UNAUTHORIZED.code(),
                HttpHeaderNames.WWW_AUTHENTICATE, BEARER);
        return Uni.createFrom().item(result);
    }

    @Override
    public Set<Class<? extends AuthenticationRequest>> getCredentialTypes() {
        return Collections.singleton(TokenAuthenticationRequest.class);
    }

    @Override
    public Uni<HttpCredentialTransport> getCredentialTransport(RoutingContext context) {
        return Uni.createFrom().item(new HttpCredentialTransport(HttpCredentialTransport.Type.AUTHORIZATION, BEARER));
    }
}
