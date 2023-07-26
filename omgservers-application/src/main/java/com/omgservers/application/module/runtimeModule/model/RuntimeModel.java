package com.omgservers.application.module.runtimeModule.model;

import com.omgservers.application.exception.ServerSideBadRequestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RuntimeModel {

    static public void validate(RuntimeModel runtime) {
        if (runtime == null) {
            throw new ServerSideBadRequestException("runtime is null");
        }
    }

    Long id;
    @ToString.Exclude
    Instant created;
    Long matchmakerId;
    Long matchId;
    RuntimeConfigModel config;
}
