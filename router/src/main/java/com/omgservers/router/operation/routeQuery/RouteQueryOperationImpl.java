package com.omgservers.router.operation.routeQuery;

import com.omgservers.schema.dto.wsToken.WsTokenDto;
import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.auth.principal.ParseException;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;


@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class RouteQueryOperationImpl implements RouteQueryOperation {

    final JWTParser jwtParser;

    @Override
    public Optional<URI> routeQuery(final String query) {
        final var parameters = parseQuery(query);
        final var wsToken = parameters.get(WsTokenDto.WS_TOKEN);
        if (Objects.nonNull(wsToken)) {
            try {
                final var jsonWebToken = jwtParser.parseOnly(wsToken);
                final var audienceOptional = jsonWebToken.getAudience().stream().findFirst();
                if (audienceOptional.isPresent()) {
                    final var audience = audienceOptional.get();
                    final var runtimeId = Long.valueOf(audience);

                    return Optional.of(URI.create(""));
                } else {
                    return Optional.empty();
                }
            } catch (ParseException | NumberFormatException e) {
                log.info("Parsing jwt failed, {}:{}", e.getClass().getSimpleName(), e.getMessage());
                return Optional.empty();
            }
        } else {
            log.info("Token ws_token is missing");
            return Optional.empty();
        }
    }

    Map<String, String> parseQuery(final String query) {
        final var params = new HashMap<String, String>();
        final var parts = query.split("&");
        for (String part : parts) {
            final var param = part.split("=");
            if (param.length > 1) {
                final var key = param[0];
                final var value = param[1];
                params.put(key, value);
            }
        }

        return params;
    }
}
