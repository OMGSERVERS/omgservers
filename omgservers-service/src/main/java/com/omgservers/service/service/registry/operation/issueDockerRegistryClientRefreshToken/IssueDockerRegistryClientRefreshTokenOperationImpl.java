package com.omgservers.service.service.registry.operation.issueDockerRegistryClientRefreshToken;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideInternalException;
import com.omgservers.service.operation.getServiceConfig.GetServiceConfigOperation;
import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.auth.principal.ParseException;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class IssueDockerRegistryClientRefreshTokenOperationImpl implements IssueDockerRegistryClientRefreshTokenOperation {

    final GetServiceConfigOperation getServiceConfigOperation;
    final JWTParser jwtParser;

    @Override
    public JsonWebToken issueDockerRegistryClientRefreshToken(final Long userId) {

        final var issuer = getServiceConfigOperation.getServiceConfig().server().jwtIssuer();
        final var jwtToken = Jwt.issuer(issuer)
                .subject(userId.toString())
                // Service uses refresh_token to issue new access_token
                .audience(issuer)
                .jws()
                .sign();

        try {
            final var jsonWebToken = jwtParser.parseOnly(jwtToken);
            return jsonWebToken;
        } catch (ParseException e) {
            throw new ServerSideInternalException(ExceptionQualifierEnum.INTERNAL_EXCEPTION_OCCURRED,
                    e.getMessage(),
                    e);
        }
    }
}
