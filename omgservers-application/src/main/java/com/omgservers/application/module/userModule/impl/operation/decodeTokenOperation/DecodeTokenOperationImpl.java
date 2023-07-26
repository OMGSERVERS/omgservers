package com.omgservers.application.module.userModule.impl.operation.decodeTokenOperation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.application.module.userModule.model.user.UserTokenModel;
import com.omgservers.application.exception.ServerSideBadRequestException;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;
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
            log.info("Token was decoded, token={}", userTokenModel);
            return userTokenModel;
        } catch (Exception e) {
            throw new ServerSideBadRequestException("decoding failed, " + e.getMessage(), e);
        }
    }
}
