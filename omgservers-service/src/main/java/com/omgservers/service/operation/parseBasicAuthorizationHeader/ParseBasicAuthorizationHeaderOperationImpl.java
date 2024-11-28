package com.omgservers.service.operation.parseBasicAuthorizationHeader;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideUnauthorizedException;
import com.omgservers.service.operation.parseBasicAuthorizationHeader.dto.BasicCredentialsDto;
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
            throw new ServerSideUnauthorizedException(ExceptionQualifierEnum.WRONG_ARGUMENT,
                    "wrong authorization header structure");
        }
        if (!headerParts[0].equalsIgnoreCase("Basic")) {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.WRONG_ARGUMENT,
                    "authorization schema is not a basic");
        }
        try {
            final var credentials = new String(Base64.getDecoder().decode(headerParts[1]));

            final int firstColonIndex = credentials.indexOf(':');

            if (firstColonIndex == -1) {
                throw new ServerSideUnauthorizedException(ExceptionQualifierEnum.WRONG_ARGUMENT,
                        "wrong credentials structure");
            }

            final var userId = Long.valueOf(credentials.substring(0, firstColonIndex));

            final var password = credentials.substring(firstColonIndex + 1);
            if (password.isEmpty()) {
                throw new ServerSideUnauthorizedException(ExceptionQualifierEnum.WRONG_ARGUMENT,
                        "password is empty");
            }

            return new BasicCredentialsDto(userId, password);
        } catch (IllegalArgumentException e) {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.WRONG_ARGUMENT, e.getMessage(), e);
        }
    }
}
