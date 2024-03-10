package com.omgservers.service.module.user.impl.operation.encodeToken;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.user.UserTokenModel;
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class EncodeTokenOperationImpl implements EncodeTokenOperation {

    final ObjectMapper objectMapper;

    @Override
    public String encodeToken(final UserTokenModel userToken) {
        try {
            String tokenString = objectMapper.writeValueAsString(userToken);
            String encodedToken = Base64.getUrlEncoder()
                    .encodeToString(tokenString.getBytes(StandardCharsets.UTF_8));
            return encodedToken;
        } catch (Exception e) {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.USER_TOKEN_WRONG, e.getMessage(), e);
        }
    }
}
