package com.omgservers.application.module.internalModule.model.job;

import com.omgservers.application.exception.ServerSideBadRequestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobModel {

    static public void validate(JobModel job) {
        if (job == null) {
            throw new ServerSideBadRequestException("job is null");
        }
    }

    Long id;
    Instant created;
    Long shardKey;
    Long entity;
    JobType type;
}
