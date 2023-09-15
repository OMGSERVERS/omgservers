package com.omgservers.model.runtime;

import com.omgservers.exception.ServerSideBadRequestException;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    Long id;

    @NotNull
    Instant created;

    @NotNull
    Instant modified;

    @NotNull
    Long tenantId;

    @NotNull
    Long stageId;

    @NotNull
    Long versionId;

    @NotNull
    Long matchmakerId;

    @NotNull
    Long matchId;

    @NotNull
    RuntimeTypeEnum type;

    @NotNull
    Long step;

    @NotNull
    Long scriptId;

    @NotNull
    RuntimeConfigModel config;
}
