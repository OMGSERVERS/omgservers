package com.omgservers.module.user.impl.operation.encodeToken;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.model.user.UserTokenModel;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
@ApplicationScoped
class EncodeTokenOperationImpl implements EncodeTokenOperation {

    final ObjectMapper objectMapper;

    EncodeTokenOperationImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public String encodeToken(UserTokenModel tokenObject) {
        if (tokenObject == null) {
            throw new ServerSideBadRequestException("tokenObject is null");
        }

        try {
            String tokenString = objectMapper.writeValueAsString(tokenObject);
            String encodedToken = Base64.getUrlEncoder()
                    .encodeToString(tokenString.getBytes(StandardCharsets.UTF_8));
            log.info("Token was encoded, token={}", tokenObject);
            return encodedToken;
        } catch (Exception e) {
            throw new ServerSideBadRequestException("encoding failed, " + e.getMessage());
        }
    }
}
