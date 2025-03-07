package com.omgservers.service.service.registry.operation.issueDockerRegistryClientAccessToken;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideInternalException;
import com.omgservers.service.operation.server.GetServiceConfigOperation;
import com.omgservers.service.service.registry.dto.DockerRegistryAccessDto;
import com.omgservers.service.service.registry.dto.DockerRegistryActionEnum;
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
class IssueDockerRegistryClientAccessTokenOperationImpl implements IssueDockerRegistryClientAccessTokenOperation {

    private final String REGISTRY_AUDIENCE = "registry";

    final GetServiceConfigOperation getServiceConfigOperation;
    final JWTParser jwtParser;

    @Override
    public JsonWebToken issueDockerRegistryClientAccessToken(final Long userId,
                                                             final List<DockerRegistryAccessDto> access) {

        try {
            final var issuer = getServiceConfigOperation.getServiceConfig().server().jwtIssuer();
            final var x5c = getServiceConfigOperation.getServiceConfig().server().x5c();
            final var jwtToken = Jwt.issuer(issuer)
                    .subject(userId.toString())
                    .audience(REGISTRY_AUDIENCE)
                    .expiresIn(Duration.ofSeconds(3600))
                    .claim("access", buildAccessList(access))
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

    JsonArray buildAccessList(final List<DockerRegistryAccessDto> access) {
        final var accessBuilder = Json.createArrayBuilder();
        for (final var dto : access) {
            final var actionsBuilder = Json.createArrayBuilder();
            dto.getActions().stream().map(DockerRegistryActionEnum::getAction)
                    .forEach(actionsBuilder::add);

            final var accessItem = Json.createObjectBuilder()
                    .add("type", dto.getType().getType())
                    .add("name", dto.getName())
                    .add("actions", actionsBuilder.build())
                    .build();

            accessBuilder.add(accessItem);
        }

        return accessBuilder.build();
    }
}
