package com.omgservers.service.server.registry.operation;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideInternalException;
import com.omgservers.service.operation.server.ExecuteStateOperation;
import com.omgservers.service.operation.server.GetServiceConfigOperation;
import com.omgservers.service.server.registry.dto.RegistryActionEnum;
import com.omgservers.service.server.registry.dto.RegistryResourceAccess;
import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.auth.principal.ParseException;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.time.Duration;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class IssueRegistryAccessTokenOperationImpl implements IssueRegistryAccessTokenOperation {

    static private final String REGISTRY_AUDIENCE = "registry";

    final GetServiceConfigOperation getServiceConfigOperation;
    final ExecuteStateOperation executeStateOperation;

    final JWTParser jwtParser;

    @Override
    public JsonWebToken execute(final Long userId,
                                final List<RegistryResourceAccess> intersection) {

        try {
            final var issuer = getServiceConfigOperation.getServiceConfig().jwt().issuer();
            final var x5c = executeStateOperation.getX5C();
            final var jwtToken = Jwt.issuer(issuer)
                    .subject(userId.toString())
                    .audience(REGISTRY_AUDIENCE)
                    .expiresIn(Duration.ofSeconds(600))
                    .claim("access", buildAccessList(intersection))
                    .jws()
                    .header("x5c", List.of(x5c))
                    .sign();

            final var jsonWebToken = jwtParser.parseOnly(jwtToken);
            return jsonWebToken;
        } catch (ParseException e) {
            throw new ServerSideInternalException(ExceptionQualifierEnum.INTERNAL_EXCEPTION_OCCURRED,
                    e.getMessage(),
                    e);
        }
    }

    JsonArray buildAccessList(final List<RegistryResourceAccess> intersection) {
        final var accessBuilder = Json.createArrayBuilder();
        for (final var access : intersection) {
            final var actionsBuilder = Json.createArrayBuilder();
            access.actions().stream().map(RegistryActionEnum::getAction)
                    .forEach(actionsBuilder::add);

            final var accessItem = Json.createObjectBuilder()
                    .add("type", access.resourceType().getType())
                    .add("name", access.resourceName())
                    .add("actions", actionsBuilder.build())
                    .build();

            accessBuilder.add(accessItem);
        }

        return accessBuilder.build();
    }
}
