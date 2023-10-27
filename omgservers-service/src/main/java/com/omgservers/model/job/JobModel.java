package com.omgservers.model.job;

import com.omgservers.exception.ServerSideBadRequestException;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    Long id;

    @NotNull
    Instant created;

    @NotNull
    Long shardKey;

    @NotNull
    Long entityId;

    @NotNull
    JobQualifierEnum qualifier;
}
