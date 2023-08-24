package com.omgservers.application.module.runtimeModule.model.actor;

import com.omgservers.application.exception.ServerSideBadRequestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActorModel {

    static public void validate(ActorModel actor) {
        if (actor == null) {
            throw new ServerSideBadRequestException("actor is null");
        }
    }

    Long id;
    Long runtimeId;
    Instant created;
    Instant modified;
    Long userId;
    Long clientId;
    ActorConfigModel config;
    ActorStatusEnum status;
}
