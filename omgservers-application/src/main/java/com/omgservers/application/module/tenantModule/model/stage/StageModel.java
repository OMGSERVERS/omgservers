package com.omgservers.application.module.tenantModule.model.stage;

import com.omgservers.application.exception.ServerSideBadRequestException;
import lombok.*;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StageModel {

    static public void validate(StageModel stage) {
        if (stage == null) {
            throw new ServerSideBadRequestException("stage is null");
        }
    }

    Long id;
    Long projectId;
    @ToString.Exclude
    Instant created;
    @ToString.Exclude
    Instant modified;
    Long versionId;
    @ToString.Exclude
    String secret;
    Long matchmakerId;
    @ToString.Exclude
    StageConfigModel config;
}
