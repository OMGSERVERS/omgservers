package com.omgservers.model.stage;

import com.omgservers.exception.ServerSideBadRequestException;
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
    Instant created;
    Instant modified;
    Long versionId;
    @ToString.Exclude
    String secret;
    Long matchmakerId;
    @ToString.Exclude
    StageConfigModel config;
}
