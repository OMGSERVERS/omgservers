package com.omgservers.application.module.runtimeModule.model.runtime;

import com.omgservers.application.exception.ServerSideBadRequestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

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
    @ToString.Exclude
    Instant modified;
    Long matchmakerId;
    Long matchId;
    RuntimeConfigModel config;
}
