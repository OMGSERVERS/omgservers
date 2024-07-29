package com.omgservers.service.server.operation.parseBasicAuthorizationHeader;

import com.omgservers.schema.dto.basicCredentials.BasicCredentialsDto;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideUnauthorizedException;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Base64;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ParseBasicAuthorizationHeaderOperationImpl implements ParseBasicAuthorizationHeaderOperation {

    @Override
    public BasicCredentialsDto parseBasicAuthorizationHeader(final String authorizationHeader) {
        final var headerParts = authorizationHeader.split(" ");
        if (headerParts.length != 2) {
            throw new ServerSideUnauthorizedException(ExceptionQualifierEnum.ARGUMENT_WRONG,
                    "wrong authorization header structure");
        }
        if (!headerParts[0].equalsIgnoreCase("Basic")) {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.ARGUMENT_WRONG,
                    "authorization schema is not a basic");
        }
        try {
            final var credentials = new String(Base64.getDecoder().decode(headerParts[1]));
            final var usernamePassword = credentials.split(":");
            if (usernamePassword.length != 2) {
                throw new ServerSideUnauthorizedException(ExceptionQualifierEnum.ARGUMENT_WRONG,
                        "wrong credentials structure");
            }

            final var userId = Long.valueOf(usernamePassword[0]);
            final var password = usernamePassword[1];
            if (password.isEmpty()) {
                throw new ServerSideUnauthorizedException(ExceptionQualifierEnum.ARGUMENT_WRONG,
                        "password is empty");
            }

            return new BasicCredentialsDto(userId, password);
        } catch (IllegalArgumentException e) {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.ARGUMENT_WRONG, e.getMessage(), e);
        }
    }
}
