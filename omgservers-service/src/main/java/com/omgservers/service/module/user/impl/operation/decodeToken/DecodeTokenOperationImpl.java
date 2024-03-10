package com.omgservers.service.module.user.impl.operation.decodeToken;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.user.UserTokenModel;
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Base64;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DecodeTokenOperationImpl implements DecodeTokenOperation {

    final ObjectMapper objectMapper;

    @Override
    public UserTokenModel decodeToken(final String rawToken) {
        try {
            String tokenString = new String(Base64.getUrlDecoder().decode(rawToken));
            UserTokenModel userTokenModel = objectMapper.readValue(tokenString, UserTokenModel.class);
            return userTokenModel;
        } catch (Exception e) {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.RAW_TOKEN_WRONG, e.getMessage(), e);
        }
    }
}
