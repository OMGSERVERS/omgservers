package com.omgservers.application.module.matchmakerModule.model.request;

import com.omgservers.application.exception.ServerSideBadRequestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestConfigModel {

    static public RequestConfigModel create(final Long userId,
                                            final Long clientId,
                                            final Long tenantId,
                                            final Long stageId,
                                            final String mode) {
        if (userId == null) {
            throw new ServerSideBadRequestException("userId is null");
        }
        if (clientId == null) {
            throw new ServerSideBadRequestException("clientId is null");
        }
        if (tenantId == null) {
            throw new ServerSideBadRequestException("tenantId is null");
        }
        if (stageId == null) {
            throw new ServerSideBadRequestException("stageId is null");
        }
        if (mode == null) {
            throw new ServerSideBadRequestException("mode is null");
        }

        final var config = new RequestConfigModel();
        config.setUserId(userId);
        config.setClientId(clientId);
        config.setTenantId(tenantId);
        config.setStageId(stageId);
        config.setMode(mode);
        config.setAttributes(new HashMap<>());
        return config;
    }

    static public void validate(RequestConfigModel config) {
        if (config == null) {
            throw new ServerSideBadRequestException("config is null");
        }
    }

    Long userId;
    Long clientId;
    Long tenantId;
    Long stageId;
    String mode;
    Map<String, String> attributes;
}
