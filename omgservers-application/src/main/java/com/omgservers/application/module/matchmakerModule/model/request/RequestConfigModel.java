package com.omgservers.application.module.matchmakerModule.model.request;

import com.omgservers.application.exception.ServerSideBadRequestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestConfigModel {

    static public RequestConfigModel create(final UUID user,
                                            final UUID client,
                                            final UUID tenant,
                                            final UUID stage,
                                            final String mode,
                                            final String pool) {
        if (user == null) {
            throw new ServerSideBadRequestException("user is null");
        }
        if (client == null) {
            throw new ServerSideBadRequestException("client is null");
        }
        if (tenant == null) {
            throw new ServerSideBadRequestException("tenant is null");
        }
        if (stage == null) {
            throw new ServerSideBadRequestException("stage is null");
        }
        if (mode == null) {
            throw new ServerSideBadRequestException("mode is null");
        }
        if (pool == null) {
            throw new ServerSideBadRequestException("pool is null");
        }

        final var config = new RequestConfigModel();
        config.setUser(user);
        config.setClient(client);
        config.setTenant(tenant);
        config.setStage(stage);
        config.setMode(mode);
        config.setPool(pool);
        config.setAttributes(new HashMap<>());
        return config;
    }

    static public void validateMatchmakerRequestConfigModel(RequestConfigModel config) {
        if (config == null) {
            throw new ServerSideBadRequestException("config is null");
        }
    }

    UUID user;
    UUID client;
    UUID tenant;
    UUID stage;
    String mode;
    String pool;
    Map<String, String> attributes;
}
