package com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.request;

import com.omgservers.application.InternalRequest;
import com.omgservers.application.module.runtimeModule.model.actor.ActorModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncActorInternalRequest implements InternalRequest {

    static public void validate(SyncActorInternalRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    ActorModel actor;

    @Override
    public String getRequestShardKey() {
        return actor.getRuntimeId().toString();
    }
}
