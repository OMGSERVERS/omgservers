package com.omgservers.application.module.runtimeModule.model.actor;

import com.omgservers.application.exception.ServerSideBadRequestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActorConfigModel {

    static public ActorConfigModel create() {
        final var actorConfig = new ActorConfigModel();
        actorConfig.setAttributes(new HashMap<>());
        return actorConfig;
    }

    static public void validate(ActorConfigModel config) {
        if (config == null) {
            throw new ServerSideBadRequestException("config is null");
        }
    }

    Map<String, String> attributes;
}
