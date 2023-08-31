package com.omgservers.model.stage;

import com.omgservers.exception.ServerSideBadRequestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    @ToString.Exclude
    String secret;
    @ToString.Exclude
    StageConfigModel config;
    Long matchmakerId;
    Long versionId;
}
