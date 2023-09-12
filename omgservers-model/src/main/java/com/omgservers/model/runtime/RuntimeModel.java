package com.omgservers.model.runtime;

import com.omgservers.exception.ServerSideBadRequestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RuntimeModel {

    public static void validate(RuntimeModel runtime) {
        if (runtime == null) {
            throw new ServerSideBadRequestException("runtime is null");
        }
    }

    Long id;
    Instant created;
    Instant modified;
    Long tenantId;
    Long stageId;
    Long versionId;
    Long matchmakerId;
    Long matchId;
    RuntimeTypeEnum type;
    Long step;
    Long scriptId;
    RuntimeConfigModel config;
}
