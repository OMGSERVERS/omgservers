package com.omgservers.model.job;

import com.omgservers.exception.ServerSideBadRequestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobModel {

    public static void validate(JobModel job) {
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
