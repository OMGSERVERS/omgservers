package com.omgservers.module.user.impl.operation.decodeToken;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.model.user.UserTokenModel;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.util.Base64;

@Slf4j
@ApplicationScoped
class DecodeTokenOperationImpl implements DecodeTokenOperation {

    final ObjectMapper objectMapper;

    public DecodeTokenOperationImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public UserTokenModel decodeToken(String rawToken) {
        if (rawToken == null) {
            throw new ServerSideBadRequestException("rawToken is null");
        }

        try {
            String tokenString = new String(Base64.getUrlDecoder().decode(rawToken));
            UserTokenModel userTokenModel = objectMapper.readValue(tokenString, UserTokenModel.class);
            UserTokenModel.validate(userTokenModel);
            log.debug("Token was decoded, token={}", userTokenModel);
            return userTokenModel;
        } catch (Exception e) {
            throw new ServerSideBadRequestException("raw token is wrong, " + e.getMessage(), e);
        }
    }
}
